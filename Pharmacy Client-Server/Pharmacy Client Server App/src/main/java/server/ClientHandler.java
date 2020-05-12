package server;

import layer_data_access.repo.GenericRepo;
import model.Drug;
import model.DrugFavorite;
import model.Pharmacy;
import model.User;
import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.*;
import static java.lang.Boolean.FALSE;

public class ClientHandler implements Runnable {

    final DataInputStream dis;
    final DataOutputStream dos;
    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    final Socket s;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos,  ObjectInputStream ois, ObjectOutputStream oos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.ois = ois;
        this.oos = oos;
    }

    @Override
    public void run()
    {
        String received;
        while (true)
        {
            try {
                // receive the answer from client
                received = dis.readUTF();

                if(received.equals("Exit"))
                {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // write on output stream based on the
                // answer from the client
                switch (received) {
                    case "getAllDrugs" :
                        getAllDrugs();
                        break;
                    case "getAllUsers" :
                        getAllUsers();
                        break;
                    case "saveUser" :
                        saveUser();
                        break;
                    case "editUser" :
                        editUser();
                        break;
                    case "deleteUser" :
                        deleteUser();
                        break;
                    case "saveDrug" :
                        saveDrug();
                        break;
                    case "editDrug" :
                        editDrug();
                        break;
                    case "deleteDrug" :
                        deleteDrug();
                        break;
                    case "searchDrugs" :
                        searchDrugs();
                        break;
                    case "filterPriceDrugs" :
                        filterPriceDrugs();
                        break;
                    case "buyItem" :
                        buyItem();
                        break;
                    case "getConnectedUser" :
                        getConnectedUser();
                        break;
                    case "rateDrug" :
                        rateDrug();
                        break;
                    case "filterRatingDrugs" :
                        filterRatingDrugs();
                        break;
                    case "getAllDrugsFav" :
                        getAllDrugsFav();
                        break;
                    case "setPharmacyMoney" :
                        setPharmacyMoney();
                        break;
                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    DecimalFormat df = new DecimalFormat("#.##");

    public void getAllDrugs() throws IOException,ClassNotFoundException {
        ArrayList<Drug> drugs = new ArrayList<>();
        drugs = GenericRepo.findAllDrugs();
        oos.writeObject(drugs);
    }

    public void getAllUsers() throws IOException {
        ArrayList<User> users = new ArrayList<User>();
        users = GenericRepo.findAllUsers();
        oos.writeObject(users);
    }

    public void saveUser() throws IOException{
        User toSave = new User();
        String mail = dis.readUTF();
        String pass = dis.readUTF();
        String name = dis.readUTF();
        String money = dis.readUTF();
        toSave = User.builder().mail(mail).password(pass).name(name).money(money).role(FALSE).build();
        GenericRepo.save(toSave);
        dos.writeUTF("done");
    }

    public void editUser() throws IOException{
        String mail = dis.readUTF();
        String pass = dis.readUTF();
        String name = dis.readUTF();
        String money = dis.readUTF();

        ArrayList<User> users;
        users = GenericRepo.findAllUsers();

        for(int i=0; i<users.size(); i++)
        {
            if(users.get(i).getName().equals(name))
            {
                users.get(i).setMail(mail);
                users.get(i).setMoney(money);
                users.get(i).setPassword(pass);
                GenericRepo.update(users.get(i));
                dos.writeUTF("done");
            }
        }
        oos.writeObject(users);
    }

    public void deleteUser() throws IOException{
        String name = dis.readUTF();
        ArrayList<User> users;
        users = GenericRepo.findAllUsers();

        List<Drug> drugs = new ArrayList<Drug>();

        for(int i=0; i<users.size(); i++) {
            if (users.get(i).getName().equals(name)) {
                GenericRepo.delete(users.get(i));
            }
        }
    }

    public void saveDrug() throws IOException{
        Drug toSave = new Drug();
        String type = dis.readUTF();
        String price = dis.readUTF();
        String name = dis.readUTF();
        String desc = dis.readUTF();
        String stock = dis.readUTF();
        String date = dis.readUTF();
        String discount = dis.readUTF();
        String buyYtimes = dis.readUTF();
        toSave = Drug.builder().name(name).price(price).type(type).description(desc).stock(stock).rating("0.0/0 din 0 rates").discount(discount).ybuys(buyYtimes).build();

        DrugFavorite d = new DrugFavorite();
        d = DrugFavorite.builder().name(name).price(price).dateUpdated(date).build();
        GenericRepo.save(toSave);
        GenericRepo.save(d);
        dos.writeUTF("done");
    }

    public void editDrug() throws IOException{
        String type = dis.readUTF();
        String price = dis.readUTF();
        String name = dis.readUTF();
        String desc = dis.readUTF();
        String stock = dis.readUTF();
        String rating = dis.readUTF();
        String date = dis.readUTF();
        String discount = dis.readUTF();
        String buyYtimes = dis.readUTF();

        ArrayList<Drug> drugs;
        drugs = GenericRepo.findAllDrugs();

        for(int i=0; i<drugs.size(); i++)
        {
            if(drugs.get(i).getName().equals(name))
            {
                drugs.get(i).setType(type);
                drugs.get(i).setPrice(price);
                drugs.get(i).setDescription(desc);
                drugs.get(i).setStock(stock);
                drugs.get(i).setRating(rating);
                drugs.get(i).setDiscount(discount);
                drugs.get(i).setYbuys(buyYtimes);
                DrugFavorite d = new DrugFavorite();
                d = DrugFavorite.builder().name(name).price(price).dateUpdated(date).build();
                GenericRepo.update(drugs.get(i));
                GenericRepo.save(d);
                dos.writeUTF("done");
            }
        }
        oos.writeObject(drugs);
    }

    public void deleteDrug() throws IOException{
        String name = dis.readUTF();
        ArrayList<Drug> drugs;
        drugs = GenericRepo.findAllDrugs();

        List<User> users = new ArrayList<User>();

        for(int i=0; i<drugs.size(); i++) {
            if (drugs.get(i).getName().equals(name)) {
                users.addAll(drugs.get(i).getUsers());
                for (int j = 0; j < users.size(); j++) {
                    List<Drug> drugss = new ArrayList<>();
                    drugss = users.get(j).getDrugs();
                    drugss.remove(drugs.get(i));
                    users.get(j).setDrugs(drugss);
                    GenericRepo.update(users.get(j));
                }
                GenericRepo.delete(drugs.get(i));
            }
        }
    }

    public void searchDrugs() throws IOException {
        ArrayList<Drug> drugs;
        drugs = GenericRepo.findAllDrugs();
        oos.writeObject(drugs);
    }

    public void filterPriceDrugs() throws IOException {
        ArrayList<Drug> drugs;
        ArrayList<Drug> searchedDrugs = new ArrayList<>();
        String priceMini = dis.readUTF();
        String priceMaxi = dis.readUTF();
        double mini = Double.parseDouble(priceMini);
        double maxi = Double.parseDouble(priceMaxi);

        drugs = GenericRepo.findAllDrugs();
        for(int i=0; i<drugs.size(); i++)
        {
            double drugPrice = Double.parseDouble(drugs.get(i).getPrice());

            if(drugPrice >= mini && drugPrice<=maxi )
            {
                searchedDrugs.add(drugs.get(i));
            }
        }
        oos.writeObject(searchedDrugs);
    }

    public void getConnectedUser()throws IOException {
        List<User> users = new ArrayList<User>();
        users = GenericRepo.findAllUsers();
        String userConnectedMail;
        userConnectedMail = dis.readUTF();
        User user = new User();
        String name = dis.readUTF();

        for(int i=0; i<users.size(); i++)
        {
            if(users.get(i).getMail().equals(userConnectedMail))
                user = users.get(i);
        }
        oos.writeObject(user);
    }

    public void buyItem() throws IOException {
        List<User> users = new ArrayList<User>();
        List<User> userss = new ArrayList<>();
        List<Drug> drugss = new ArrayList<>();
        users = GenericRepo.findAllUsers();
        String userConnectedMail;
        userConnectedMail = dis.readUTF();
        User user = new User();

        String name = dis.readUTF();
        ArrayList<Drug> dr;
        dr = GenericRepo.findAllDrugs();
        Drug drug = new Drug();

        for(int i=0; i<dr.size(); i++) {
            if (dr.get(i).getName().equals(name)) {
                drug = dr.get(i);
            }
        }

        for(int i=0; i<users.size(); i++)
        {
            if(users.get(i).getMail().equals(userConnectedMail))
                user = users.get(i);
        }

        Pharmacy phm = new Pharmacy();
        ArrayList<Pharmacy> pharma;
        pharma = GenericRepo.findAllPharmacy();

        phm = pharma.get(0);

        double pharmacyAmount = Double.parseDouble(phm.getMoney());  //banii farmaciei de discount pe luna curenta
        double userMoney = Double.parseDouble(user.getMoney());
        double drugPrice = Double.parseDouble(drug.getPrice());
        int stock = Integer.parseInt(drug.getStock());

        if(stock>0) {
            if (userMoney - drugPrice >= 0) {
                drugss = user.getDrugs();  //cu acestea imi scot userii si drugs din tabela de many to many
                userss = drug.getUsers();

                int nrBuys = 0;
                for(int k=0; k<drugss.size(); k++)
                {
                    if(drugss.get(k).getName().equals(drug.getName()))
                    {
                        nrBuys++;
                    }
                }

                if(Integer.parseInt(drug.getDiscount())>0)
                {
                    if (nrBuys >= Integer.parseInt(drug.getYbuys()))
                    {
                        double discount = drugPrice * Double.parseDouble(drug.getDiscount()) / 100;
                        if (pharmacyAmount > 0 && pharmacyAmount >= discount)
                        {
                            pharmacyAmount -= discount;
                            pharmacyAmount = Double.parseDouble(df.format(pharmacyAmount));
                            phm.setMoney(Double.toString(pharmacyAmount));
                            GenericRepo.update(phm);
                            drugPrice -= discount;
                            userMoney -= drugPrice;
                            userMoney = Double.parseDouble(df.format(userMoney));
                            stock--;

                            drug.setStock(Integer.toString(stock));
                            user.setMoney(Double.toString(userMoney));
                            userss.add(user);
                            drugss.add(drug);
                            user.setDrugs(drugss);
                            drug.setUsers(userss);
                            GenericRepo.update(user);
                            GenericRepo.update(drug);
                            dos.writeUTF("4");   //bought at discount price
                        } else {
                            userMoney -= drugPrice;
                            userMoney = Double.parseDouble(df.format(userMoney));
                            stock--;
                            drug.setStock(Integer.toString(stock));
                            user.setMoney(Double.toString(userMoney));
                            userss.add(user);
                            drugss.add(drug);
                            user.setDrugs(drugss);
                            drug.setUsers(userss);
                            GenericRepo.update(user);
                            GenericRepo.update(drug);
                            dos.writeUTF("3");   //pharmacy oot of discount money, bought at standard price
                        }
                    }
                    else
                    {
                        userMoney -= drugPrice;
                        userMoney = Double.parseDouble(df.format(userMoney));
                        stock--;
                        drug.setStock(Integer.toString(stock));
                        user.setMoney(Double.toString(userMoney));
                        userss.add(user);
                        drugss.add(drug);
                        user.setDrugs(drugss);
                        drug.setUsers(userss);
                        GenericRepo.update(user);
                        GenericRepo.update(drug);
                        dos.writeUTF("1");   //buy successful at standard money
                    }
                }
                else{
                    userMoney -= drugPrice;
                    userMoney = Double.parseDouble(df.format(userMoney));
                    stock--;
                    drug.setStock(Integer.toString(stock));
                    user.setMoney(Double.toString(userMoney));
                    userss.add(user);
                    drugss.add(drug);
                    user.setDrugs(drugss);
                    drug.setUsers(userss);
                    GenericRepo.update(user);
                    GenericRepo.update(drug);
                    dos.writeUTF("1");   //buy successful at standard money
                }
            }
            else{dos.writeUTF("0");}   //user has inssuficient money
        }
        else{dos.writeUTF("2");}   //product out of stock
        oos.writeObject(user);
    }

    public void rateDrug() throws IOException {
        String name = dis.readUTF();
        ArrayList<Drug> dr;
        dr = GenericRepo.findAllDrugs();
        Drug drug = new Drug();
        String rate = dis.readUTF();  //rate-ul dat de user

        for(int i=0; i<dr.size(); i++) {
            if (dr.get(i).getName().equals(name)) {
                drug = dr.get(i);
            }
        }
        //suma = media*cate sunt
        String medie = drug.getRating().substring(0, 3);
        double media = Double.parseDouble(medie);
        String nr = drug.getRating().substring(10, 12);
        int nrRates = nr.charAt(0) - 48;
        if(nr.charAt(1)!=' ')
        {
            nrRates = nrRates*10 + (nr.charAt(1)-48);
        }

        int suma = (int)(media*nrRates);
        nrRates++;
        suma += (int)Double.parseDouble(rate);
        DecimalFormat df = new DecimalFormat("#.#");
        media = (double)suma/nrRates;
        media = Double.parseDouble(df.format(media));
        String m = Double.toString(media);
        m = m+"/5 din " + nrRates +" rates";

        drug.setRating(m);
        GenericRepo.update(drug);

        dos.writeUTF("done");
    }

    public void filterRatingDrugs() throws IOException {

        ArrayList<Drug> drugs;
        ArrayList<Drug> searchedDrugs = new ArrayList<>();
        String priceMini = dis.readUTF();
        String priceMaxi = dis.readUTF();
        double mini = Double.parseDouble(priceMini);
        double maxi = Double.parseDouble(priceMaxi);

        drugs = GenericRepo.findAllDrugs();
        for(int i=0; i<drugs.size(); i++)
        {
            String rating = drugs.get(i).getRating();
            double rate = Double.parseDouble(rating.substring(0, 3));

            if(rate >= mini && rate<=maxi )
            {
                searchedDrugs.add(drugs.get(i));
            }
        }
        oos.writeObject(searchedDrugs);
    }


    public void getAllDrugsFav() throws IOException {
        ArrayList<DrugFavorite> drugsF;
        drugsF = GenericRepo.findAllDrugsFav();
        oos.writeObject(drugsF);
    }

    public void setPharmacyMoney()  throws IOException {
        String pharmacyMoney = dis.readUTF();
        Pharmacy phm = new Pharmacy();
        ArrayList<Pharmacy> pharma;
        pharma = GenericRepo.findAllPharmacy();

        phm = pharma.get(0);
        phm.setMoney(pharmacyMoney);
        GenericRepo.update(phm);
        dos.writeUTF("done");
    }

}
