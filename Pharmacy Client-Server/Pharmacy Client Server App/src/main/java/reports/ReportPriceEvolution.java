package reports;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DrugFavorite;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReportPriceEvolution {

    public void createReportPrice(ArrayList<DrugFavorite> drugsFav){
        try {
            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            chooser.getExtensionFilters().add(extFilter);
            File file = chooser.showSaveDialog(new Stage());

            PrintWriter out = new PrintWriter(file);
            out.print("\t\t\t\t\tReport With Favourite Drugs Price Evolution Last Month");
            out.print("\n\n");
            out.print("Price evolution \n\n");

            Collections.sort(drugsFav, new Comparator<DrugFavorite>(){
                public int compare(DrugFavorite d1, DrugFavorite d2) {
                    return d1.getName().compareTo(d2.getName());
                }
            });

            for(int i=0; i<drugsFav.size(); i++){
                out.println("Product: " + drugsFav.get(i).getName() + " changed price to: " +  drugsFav.get(i).getPrice() + " at date: " +  drugsFav.get(i).getDateUpdated());
            }
            out.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
