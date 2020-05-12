package controller;

import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.AlertBox;
import model.Drug;
import model.DrugFavorite;
import model.User;
import org.controlsfx.control.Rating;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import reports.ReportPriceEvolution;
import validators.Validators;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ControllerUser {
    @FXML
    TableView drugsUserTable;
    @FXML
    TableColumn idDrugName;
    @FXML
    TableColumn idDrugPrice;
    @FXML
    TableColumn idDrugType;
    @FXML
    TableColumn idDrugDesc;
    @FXML
    TableColumn idDrugStock;
    @FXML
    TableColumn idDrugDiscount;
    @FXML
    TableColumn idDrugOnYBuys;
    @FXML
    TextField searchDrug;
    @FXML
    TextField priceMin;
    @FXML
    TextField priceMax;
    @FXML
    TableView tableCart;
    @FXML
    TableColumn drugsCart;
    @FXML
    TableColumn idDrugRating;
    @FXML
    Rating ratingStars;
    @FXML
    TextField idMinRating;
    @FXML
    TextField idMaxRating;


    List<Drug> drugs = new ArrayList<Drug>();

    public void showDrugs() throws IOException, ClassNotFoundException {
        //write to server to receive all drugs
        //server returns all drugs

        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        dos.writeUTF("getAllDrugs");
        drugs = (List<Drug>) ois.readObject();

        drugsUserTable.getItems().clear();
        ObservableList<Drug> drugsObs = FXCollections.observableArrayList(drugs);

        for(int i=0; i<drugsObs.size(); i++)
        {
            idDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
            idDrugType.setCellValueFactory(new PropertyValueFactory<>("type"));
            idDrugPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            idDrugDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            idDrugStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            idDrugRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            idDrugDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            idDrugOnYBuys.setCellValueFactory(new PropertyValueFactory<>("ybuys"));
            drugsUserTable.getItems().add(drugsObs.get(i));
        }
    }

    public static ArrayList<Drug> drugsFromCart = new ArrayList<>();
    public void addToCart() {
        Drug drug = (Drug) drugsUserTable.getSelectionModel().getSelectedItem();
        drugsCart.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCart.getItems().add(drug);
        drugsFromCart.add(drug);
    }

    public void buyItems() throws IOException, ClassNotFoundException {
        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        dos.writeUTF("buyItem");

        String userConnectedMail;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userConnectedMail = ControllerLogin.authReq.getPrincipal().toString();
        dos.writeUTF(userConnectedMail);

        Drug drug = (Drug) tableCart.getSelectionModel().getSelectedItem();
        dos.writeUTF(drug.getName());

        String resultFromServer = dis.readUTF();
        User user = (User) ois.readObject();
        if (resultFromServer.equals("1")) {
            AlertBox.display("Command registered!", "User " + user.getName() + " successfully bought " + drug.getName());
            tableCart.getItems().remove(drug);
            drugsFromCart.remove(drug);
        }
        else if (resultFromServer.equals("3")) {
            AlertBox.display("Command registered!", "User " + user.getName() + " successfully bought " + drug.getName() + " but no discount as Pharmacy out of money");
            tableCart.getItems().remove(drug);
            drugsFromCart.remove(drug);
        }
        else if (resultFromServer.equals("4")) {
            AlertBox.display("Command registered!", "User " + user.getName() + " successfully bought " + drug.getName() + " with discount from pharmacy");
            tableCart.getItems().remove(drug);
            drugsFromCart.remove(drug);
        }
        else if(resultFromServer.equals("0")) {AlertBox.display("Cannot perform the command", "User " + user.getName() + " does not have enough money");}
        else {AlertBox.display("Cannot perform the command", "Drug out of stock");}  //2
        showDrugs();
    }

    public static ArrayList<Drug> drugsRated = new ArrayList<>();
    public void rateDrug() throws IOException, ClassNotFoundException{

        Drug drug = (Drug) drugsUserTable.getSelectionModel().getSelectedItem();
        DecimalFormat df = new DecimalFormat("#.#");
        double rate = ratingStars.getRating();
        rate = Double.parseDouble(df.format(rate));

        int ok = 1;
        for(int i=0; i< drugsRated.size(); i++)
        {
            if(drug.getName().equals(drugsRated.get(i).getName()))
                ok=0;
        }

        if(ok==1)
        {
            Socket s = Client.getClientSocket();
            DataInputStream dis = Client.getDis();
            DataOutputStream dos = Client.getDos();
            ObjectInputStream ois = Client.getOis();

            dos.writeUTF("rateDrug");
            dos.writeUTF(drug.getName());
            dos.writeUTF(Double.toString(rate));
            String done = dis.readUTF();

            drugsRated.add(drug);

            showDrugs();
        }
        else {AlertBox.display("Drug already rated", "Drug has been already rated!");}
    }

    public List<Drug> searchDrugs() throws IOException, ClassNotFoundException {
        List<Drug> searchedDrugs = new ArrayList<Drug>();
        String searchText = searchDrug.getText();
        int ok = 0;

        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        dos.writeUTF("searchDrugs");
        drugs = (List<Drug>) ois.readObject();

        for(int i=0; i<drugs.size(); i++)
        {
            if(drugs.get(i).getName().equals(searchText) || drugs.get(i).getType().equals(searchText) || drugs.get(i).getPrice().equals(searchText)  || drugs.get(i).getDescription().equals(searchText)   )
            {
                searchedDrugs.add(drugs.get(i));
                ok = 1;
            }
        }
        if(ok == 0)
            AlertBox.display("Product not found", "Inexistent product/type/price/description! " );

        searchDrug.clear();
        return searchedDrugs;
    }

    public void showDrugsSearched() throws IOException, ClassNotFoundException {
        drugsUserTable.getItems().clear();
        List<Drug> searchedDrugs = new ArrayList<Drug>();
        searchedDrugs = searchDrugs();
        ObservableList<Drug> drugsObs = FXCollections.observableArrayList(searchedDrugs);

        for(int i=0; i<drugsObs.size(); i++)
        {
            idDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
            idDrugType.setCellValueFactory(new PropertyValueFactory<>("type"));
            idDrugPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            idDrugDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            idDrugStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            idDrugRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            idDrugDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            idDrugOnYBuys.setCellValueFactory(new PropertyValueFactory<>("ybuys"));
            drugsUserTable.getItems().add(drugsObs.get(i));
        }
    }

    public List<Drug> filteredPriceDrugs() throws IOException, ClassNotFoundException {
        List<Drug> searchedDrugs = new ArrayList<Drug>();
        String priceMini = priceMin.getText();
        String priceMaxi = priceMax.getText();

        if(Validators.validateDoubleBox(priceMini)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money for the minimum sum");
        }
        else if(Validators.validateDoubleBox(priceMaxi)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money for the maximum sum");
        }
        else{
            double mini = Double.parseDouble(priceMini);
            double maxi = Double.parseDouble(priceMaxi);
            if(mini>maxi)
            {
                AlertBox.display("Wrong money", "Minimum sum can not be greater than maximum sum");
            }
            else{
                Socket s = Client.getClientSocket();
                DataInputStream dis = Client.getDis();
                DataOutputStream dos = Client.getDos();
                ObjectInputStream ois = Client.getOis();
                dos.writeUTF("filterPriceDrugs");
                dos.writeUTF(priceMini);
                dos.writeUTF(priceMaxi);
                searchedDrugs = (List<Drug>) ois.readObject();
            }
        }
        priceMin.clear();
        priceMax.clear();
        return searchedDrugs;
    }

    public void showFilteredDrugs() throws IOException, ClassNotFoundException {
        drugsUserTable.getItems().clear();
        List<Drug> searchedDrugs = new ArrayList<Drug>();
        searchedDrugs = filteredPriceDrugs();
        ObservableList<Drug> drugsObs = FXCollections.observableArrayList(searchedDrugs);

        for(int i=0; i<drugsObs.size(); i++)
        {
            idDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
            idDrugType.setCellValueFactory(new PropertyValueFactory<>("type"));
            idDrugPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            idDrugDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            idDrugStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            idDrugRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            idDrugDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            idDrugOnYBuys.setCellValueFactory(new PropertyValueFactory<>("ybuys"));
            drugsUserTable.getItems().add(drugsObs.get(i));
        }
    }


    public List<Drug> filteredRatingsDrugs() throws IOException, ClassNotFoundException {
        List<Drug> searchedDrugs = new ArrayList<Drug>();
        String rateMini = idMinRating.getText();
        String rateMaxi = idMaxRating.getText();

        if(Validators.validateDoubleBox(rateMini)==0){
            AlertBox.display("Wrong rating", "Please insert a valid rating for the minimum rating");
        }
        else if(Validators.validateDoubleBox(rateMaxi)==0){
            AlertBox.display("Wrong rating", "Please insert a valid rating for the maximum rating");
        }
        else{
            double mini = Double.parseDouble(rateMini);
            double maxi = Double.parseDouble(rateMaxi);
            if(mini>maxi)
            {
                AlertBox.display("Wrong rating", "Minimum rating can not be greater than maximum rating");
            }
            else{
                Socket s = Client.getClientSocket();
                DataInputStream dis = Client.getDis();
                DataOutputStream dos = Client.getDos();
                ObjectInputStream ois = Client.getOis();
                dos.writeUTF("filterRatingDrugs");
                dos.writeUTF(rateMini);
                dos.writeUTF(rateMaxi);
                searchedDrugs = (List<Drug>) ois.readObject();
            }
        }
        idMinRating.clear();
        idMaxRating.clear();
        return searchedDrugs;
    }

    public void showFilteredDrugsRating() throws IOException, ClassNotFoundException {
        drugsUserTable.getItems().clear();
        List<Drug> searchedDrugs = new ArrayList<Drug>();
        searchedDrugs = filteredRatingsDrugs();
        ObservableList<Drug> drugsObs = FXCollections.observableArrayList(searchedDrugs);

        for(int i=0; i<drugsObs.size(); i++)
        {
            idDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
            idDrugType.setCellValueFactory(new PropertyValueFactory<>("type"));
            idDrugPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            idDrugDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            idDrugStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
            idDrugRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            idDrugDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            idDrugOnYBuys.setCellValueFactory(new PropertyValueFactory<>("ybuys"));
            drugsUserTable.getItems().add(drugsObs.get(i));
        }
    }


    public static ObservableList<Drug> drugsFromWishList;

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public void seePriceEvolution() throws IOException, ClassNotFoundException, ParseException {
        drugsFromWishList = tableCart.getItems();
        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        List<DrugFavorite> drugsFav = new ArrayList<>();
        dos.writeUTF("getAllDrugsFav");
        drugsFav = (List<DrugFavorite>) ois.readObject();

        ArrayList<DrugFavorite>drugsToReport = new ArrayList<>();
        for(int i=0; i<drugsFromWishList.size(); i++)
        {
            for(int j=0; j<drugsFav.size(); j++)
            {
                if(drugsFromWishList.get(i).getName().equals(drugsFav.get(j).getName()))
                {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = new Date();
                    dateFormat.format(date);   //data curenta

                    Date dataUpdatePret= dateFormat.parse(drugsFav.get(j).getDateUpdated());  //data updatePret

                    LocalDate d = convertToLocalDateViaInstant(date);
                    LocalDate earlier = d.minusMonths(1);

                    date = convertToDateViaSqlDate(earlier);  //acum am data de acuma o luna in variabila date

                    if (dataUpdatePret.compareTo(date) > 0)
                    {
                        drugsToReport.add(drugsFav.get(j));
                    }
                }
            }
        }
        ReportPriceEvolution report = new ReportPriceEvolution();  //creaza un raport txt cu evolutia preturilor produselor din wishlist
        report.createReportPrice(drugsToReport);
    }

}
