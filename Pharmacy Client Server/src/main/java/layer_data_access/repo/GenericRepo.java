package layer_data_access.repo;

import config.HibernateConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Drug;
import model.DrugFavorite;
import model.Pharmacy;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.Iterator;

public class GenericRepo {

    private final static Session session = HibernateConfig.Instance();


    public static <T> T findById(Class<T> clasz, int id) {
        Transaction transaction = session.beginTransaction();
        T toReturn = null;
        toReturn = session.find(clasz, Integer.valueOf(id));
        transaction.commit();
        return toReturn;
    }

    public static <T> T findByName(Class<T> clasz, String name) {
        Transaction transaction = session.beginTransaction();
        T toReturn = null;
        toReturn = session.find(clasz, name);
        transaction.commit();
        return toReturn;
    }

    /**
     * Save returns the object identifier not the saved object.
     */
    public static <T> int save(T toSave) {
        Transaction transaction = session.beginTransaction();
     //   session.clear();
        int savedId = (int) session.save(toSave);
        transaction.commit();
        return savedId;
    }

    public static <T> void update(T toUpdate){
        Transaction transaction = session.beginTransaction();
        session.clear();
        session.update(toUpdate);
        transaction.commit();
    }

    public static <T> void delete(T toDelete){
        Transaction transaction = session.beginTransaction();
        session.clear();
        session.delete(toDelete);
        transaction.commit();
    }

    public static ObservableList<User> users;
    public static <T> ObservableList<User> findUsers() {

        users  =  FXCollections.observableArrayList();

        Query qee = session.createQuery("from model.User");
        Iterator ite  =qee.iterate();
        while(ite.hasNext())
        {
            User obj = (User)ite.next();
            users.add(obj);
        }
        return users;
    }

    public static ArrayList<User> userss;
    public static <T> ArrayList<User> findAllUsers() {

        userss  =  new ArrayList<User>();
        session.clear();
        Query qee = session.createQuery("from model.User");
        Iterator ite  =qee.iterate();
        while(ite.hasNext())
        {
            User obj = (User)ite.next();
            userss.add(obj);
        }
        return userss;
    }

    public static ArrayList<Drug> drugss;
    public static <T> ArrayList<Drug> findAllDrugs() {

        drugss  =  new ArrayList<Drug>();
        session.clear();
        Query qee = session.createQuery("from model.Drug");
        Iterator ite  =qee.iterate();
        while(ite.hasNext())
        {
            Drug obj = (Drug)ite.next();
            drugss.add(obj);
        }
        return drugss;
    }

    public static ArrayList<DrugFavorite> drugssFav;
    public static <T> ArrayList<DrugFavorite> findAllDrugsFav() {

        drugssFav  =  new ArrayList<DrugFavorite>();
        session.clear();
        Query qee = session.createQuery("from model.DrugFavorite");
        Iterator ite  =qee.iterate();
        while(ite.hasNext())
        {
            DrugFavorite obj = (DrugFavorite)ite.next();
            drugssFav.add(obj);
        }

        return drugssFav;
    }

    public static ObservableList<Drug> drugs;
    public static <T> ObservableList<Drug> findDrugs() {

        drugs  =  FXCollections.observableArrayList();
        Query qee = session.createQuery("from model.Drug");
        Iterator ite  =qee.iterate();
        while(ite.hasNext())
        {
            Drug obj = (Drug)ite.next();
            drugs.add(obj);
        }

        return drugs;
    }

    public static ArrayList<Pharmacy> pharma;
    public static <T> ArrayList<Pharmacy> findAllPharmacy() {

        pharma  =  new ArrayList<Pharmacy>();
        session.clear();
        Query qee = session.createQuery("from model.Pharmacy");
        Iterator ite  =qee.iterate();
        while(ite.hasNext())
        {
            Pharmacy obj = (Pharmacy)ite.next();
            pharma.add(obj);
        }
        return pharma;
    }

    public static <T> ObservableList<T> findEverything(String querry) {
        ObservableList<T> list;
        list  =  FXCollections.observableArrayList();
        Query qee = session.createQuery(querry);
        Iterator ite  =qee.iterate();
        while(ite.hasNext())
        {
            list.add((T)ite.next());
        }
        return list;
    }

}
