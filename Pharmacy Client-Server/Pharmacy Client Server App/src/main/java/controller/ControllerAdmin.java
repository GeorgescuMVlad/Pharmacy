package controller;

import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import layer_data_access.repo.GenericRepo;
import main.AlertBox;
import model.Drug;
import model.User;
import reports.ReportPdf;
import reports.ReportTxt;
import reports.Reports;
import validators.Validators;

import java.io.*;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Boolean.FALSE;

public class ControllerAdmin {
    @FXML
    TableView usersTable;
    @FXML
    TableColumn idNameCol;
    @FXML
    TableColumn idPassCol;
    @FXML
    TableColumn idMailCol;
    @FXML
    TableColumn idMoneyCol;
    @FXML
    TextField userNameAdmin;
    @FXML
    TextField userMailAdmin;
    @FXML
    TextField userPassAdmin;
    @FXML
    TextField userMoneyAdmin;
    @FXML
    TableView drugsAdminTable;
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
    TableColumn idDrugRating;
    @FXML
    TableColumn idDrugDiscount;
    @FXML
    TableColumn idDrugYBuys;
    @FXML
    TextField drugNameId;
    @FXML
    TextField drugTypeAdmin;
    @FXML
    TextField drugPriceAdmin;
    @FXML
    TextField drugDescAdmin;
    @FXML
    TextField drugStockAdmin;
    @FXML
    TextField drugRatingAdmin;
    @FXML
    TextField reportAdmin;
    @FXML
    DatePicker dateAdded;
    @FXML
    TextField drugDiscountAdmin;
    @FXML
    TextField drugYBuysAdmin;
    @FXML
    TextField pharmacyMoney;


    List<User> users = new ArrayList<User>();
    public void showUsers() throws IOException, ClassNotFoundException {

        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        dos.writeUTF("getAllUsers");
        users = (List<User>) ois.readObject();

        usersTable.getItems().clear();
        ObservableList<User> usersObs = FXCollections.observableArrayList(users);

        for(int i=0; i<usersObs.size(); i++)
        {
            idNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            idPassCol.setCellValueFactory(new PropertyValueFactory<>("password"));
            idMailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
            idMoneyCol.setCellValueFactory(new PropertyValueFactory<>("money"));
            usersTable.getItems().add(usersObs.get(i));
        }
    }

    public void saveUser() throws IOException, ClassNotFoundException {
        String name = userNameAdmin.getText();
        String mail = userMailAdmin.getText();
        String pass = userPassAdmin.getText();
        String money = userMoneyAdmin.getText();

        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        ObjectOutputStream oos = Client.getOos();

        if(Validators.validateName(name)==0){
            AlertBox.display("Wrong name", "Please insert a valid name. Must have at least 3 characters.");
        }
        else if(Validators.validatePass(pass)==0){
            AlertBox.display("Wrong pass", "Pass must be at least 3 characters and at most 15");
        }
        else if(Validators.validateEmail(mail)==0){
            AlertBox.display("Wrong email", "Email must contain exactly 1 @ which is not first or last and must have at least 3 characters");
        }
        else if(Validators.validateDoubleBox(money)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money");
        }
        else{
            dos.writeUTF("saveUser");
            dos.writeUTF(mail);
            dos.writeUTF(pass);
            dos.writeUTF(name);
            dos.writeUTF(money);
            String done = dis.readUTF();
        }
        showUsers();
        userNameAdmin.clear();
        userMailAdmin.clear();
        userPassAdmin.clear();
        userMoneyAdmin.clear();
    }

    public void editUser() throws IOException, ClassNotFoundException {
        String name = userNameAdmin.getText();
        String mail = userMailAdmin.getText();
        String pass = userPassAdmin.getText();
        String money = userMoneyAdmin.getText();

        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        ObjectOutputStream oos = Client.getOos();

        if(Validators.validateName(name)==0){
            AlertBox.display("Wrong name", "Please insert a valid name. Must have at least 3 characters, only letters.");
        }
        else if(Validators.validatePass(pass)==0){
            AlertBox.display("Wrong pass", "Pass must be at least 3 characters and at most 15");
        }
        else if(Validators.validateEmail(mail)==0){
            AlertBox.display("Wrong email", "Email must contain exactly 1 @ which is not first or last and must have at least 3 characters");
        }
        else if(Validators.validateDoubleBox(money)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money");
        }
        else {
            dos.writeUTF("editUser");
            dos.writeUTF(mail);
            dos.writeUTF(pass);
            dos.writeUTF(name);
            dos.writeUTF(money);
            String done = dis.readUTF();
        }

        userNameAdmin.clear();
        userMailAdmin.clear();
        userPassAdmin.clear();
        userMoneyAdmin.clear();
        showUsers();
    }

    public void deleteUser() throws IOException, ClassNotFoundException {
        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        ObjectOutputStream oos = Client.getOos();
        User user = (User) usersTable.getSelectionModel().getSelectedItem();
        dos.writeUTF("deleteUser");
        dos.writeUTF(user.getName());
        showUsers();
    }

    //--------------------------------------------------------------------------------------------------------------

    List<Drug> drugs = new ArrayList<Drug>();

    public void showDrugs() throws IOException, ClassNotFoundException {

        idDrugName.setCellValueFactory(new PropertyValueFactory<>("name"));
        idDrugType.setCellValueFactory(new PropertyValueFactory<>("type"));
        idDrugPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        idDrugDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        idDrugStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        idDrugRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        idDrugDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        idDrugYBuys.setCellValueFactory(new PropertyValueFactory<>("ybuys"));


        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        dos.writeUTF("getAllDrugs");
        drugs = new ArrayList<>();
        drugs = (List<Drug>) ois.readObject();

        drugsAdminTable.getItems().clear();
        drugsAdminTable.getItems().addAll(drugs);
    }

    public void saveDrug() throws IOException, ClassNotFoundException {
        String name = drugNameId.getText();
        String type = drugTypeAdmin.getText();
        String price = drugPriceAdmin.getText();
        String desc = drugDescAdmin.getText();
        String stock = drugStockAdmin.getText();
        String rating = drugRatingAdmin.getText();
        String date = dateAdded.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String discount = drugDiscountAdmin.getText();
        String buysYtimes = drugYBuysAdmin.getText();

        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        ObjectOutputStream oos = Client.getOos();

        if(Validators.validateName(name)==0){
            AlertBox.display("Wrong name", "Please insert a valid name. Must have at least 3 characters, only letters.");
        }
        else if(Validators.validateName(type)==0){
            AlertBox.display("Wrong type", "Please insert a valid type name. Must have at least 3 characters, only letters.");
        }
        else if(Validators.validateDoubleBox(price)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money for drug price");
        }
        else if(Validators.validateName(desc)==0){
            AlertBox.display("Wrong description", "Please insert a valid description. Must have at least 3 characters, only letters");
        }
        else if(Validators.validateNumberBox(stock)==0){
            AlertBox.display("Wrong stock", "Please insert a valid number for drug stock");
        }
        else if(Validators.validateNumberBox(discount)==0){
            AlertBox.display("Wrong discount", "Please insert a valid number for drug discount");
        }
        else if(Validators.validateNumberBox(buysYtimes)==0){
            AlertBox.display("Wrong number of buys", "Please insert a valid number for drug number of buys for discount");
        }
        else{
            dos.writeUTF("saveDrug");
            dos.writeUTF(type);
            dos.writeUTF(price);
            dos.writeUTF(name);
            dos.writeUTF(desc);
            dos.writeUTF(stock);
            dos.writeUTF(date);
            dos.writeUTF(discount);
            dos.writeUTF(buysYtimes);

            String done = dis.readUTF();
        }
        showDrugs();
        drugNameId.clear();
        drugTypeAdmin.clear();
        drugPriceAdmin.clear();
        drugDescAdmin.clear();
        drugStockAdmin.clear();
        drugRatingAdmin.clear();
        drugDiscountAdmin.clear();
        drugYBuysAdmin.clear();
        dateAdded.setValue(null);
    }

    public void editDrug() throws IOException, ClassNotFoundException {
        String name = drugNameId.getText();
        String type = drugTypeAdmin.getText();
        String price = drugPriceAdmin.getText();
        String desc = drugDescAdmin.getText();
        String stock = drugStockAdmin.getText();
        String rating = drugRatingAdmin.getText();
        String date = dateAdded.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String discount = drugDiscountAdmin.getText();
        String buysYtimes = drugYBuysAdmin.getText();

        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        ObjectOutputStream oos = Client.getOos();

        if(Validators.validateName(name)==0){
            AlertBox.display("Wrong name", "Please insert a valid name. Must have at least 3 characters.");
        }
        else if(Validators.validateName(type)==0){
            AlertBox.display("Wrong type", "Please insert a valid type name. Must have at least 3 characters.");
        }
        else if(Validators.validateDoubleBox(price)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money for drug price");
        }
        else if(Validators.validateName(desc)==0){
            AlertBox.display("Wrong description", "Please insert a valid description. Must have at least 3 characters");
        }
        else if(Validators.validateNumberBox(stock)==0){
            AlertBox.display("Wrong stock", "Please insert a valid number for drug stock");
        }
        else if(Validators.validateNumberBox(discount)==0){
            AlertBox.display("Wrong discount", "Please insert a valid number for drug discount");
        }
        else if(Validators.validateNumberBox(buysYtimes)==0){
            AlertBox.display("Wrong number of buys", "Please insert a valid number for drug number of buys for discount");
        }
        else{
            dos.writeUTF("editDrug");
            dos.writeUTF(type);
            dos.writeUTF(price);
            dos.writeUTF(name);
            dos.writeUTF(desc);
            dos.writeUTF(stock);
            dos.writeUTF(rating);
            dos.writeUTF(date);
            dos.writeUTF(discount);
            dos.writeUTF(buysYtimes);
            String done = dis.readUTF();
        }
        drugNameId.clear();
        drugTypeAdmin.clear();
        drugPriceAdmin.clear();
        drugDescAdmin.clear();
        drugStockAdmin.clear();
        drugRatingAdmin.clear();
        dateAdded.setValue(null);
        drugDiscountAdmin.clear();
        drugYBuysAdmin.clear();
        showDrugs();
    }

    public void deleteDrug() throws IOException, ClassNotFoundException {
        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        ObjectOutputStream oos = Client.getOos();
        Drug drug = (Drug) drugsAdminTable.getSelectionModel().getSelectedItem();
        dos.writeUTF("deleteDrug");
        dos.writeUTF(drug.getName());
        showDrugs();
    }

    public void generateReport() throws IOException, ClassNotFoundException {

        Socket s = Client.getClientSocket();
        DataInputStream dis = Client.getDis();
        DataOutputStream dos = Client.getDos();
        ObjectInputStream ois = Client.getOis();
        dos.writeUTF("getAllUsers");
        users = (List<User>) ois.readObject();

        Reports report = null;
        String rep = reportAdmin.getText();
        if(rep.equals("pdf")){
            report = new ReportPdf();
            report.createReport(users);
            AlertBox.display("Pdf", "Pdf generated successfully, check for it in the folder you saved it");
        }
        else if(rep.equals("txt")) {
            report = new ReportTxt();
            report.createReport(users);
            AlertBox.display("Txt", "Txt generated successfully, check for it in the folder you saved it");
        }
        else AlertBox.display("Reports", "Invalid format inserted. Try again!");
        reportAdmin.clear();
    }

    public void setPharmacyMoney() throws IOException, ClassNotFoundException{
        String pharmacyAmount = pharmacyMoney.getText();

        if(Validators.validateDoubleBox(pharmacyAmount)==0){
            AlertBox.display("Wrong money", "Please insert a valid amount of money for pharmacy");
        }
        else {
            Socket s = Client.getClientSocket();
            DataInputStream dis = Client.getDis();
            DataOutputStream dos = Client.getDos();
            ObjectInputStream ois = Client.getOis();
            ObjectOutputStream oos = Client.getOos();
            dos.writeUTF("setPharmacyMoney");
            dos.writeUTF(pharmacyAmount);
            String done = dis.readUTF();
        }
        pharmacyMoney.clear();
    }

}
