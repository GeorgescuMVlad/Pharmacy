package reports;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Drug;
import model.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ReportTxt implements Reports {
    @Override
    public void createReport(List<User> users) {
        try {
            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            chooser.getExtensionFilters().add(extFilter);
            File file = chooser.showSaveDialog(new Stage());

            PrintWriter out = new PrintWriter(file);
            out.print("\t\t\t\t\tReport With Drugs Bought");
            out.print("\n\n");
            out.print("Drugs Bought \n\n");


            for(int i=0; i<users.size(); i++){
                List<Drug> drugs = new ArrayList<Drug>();
                drugs.addAll(users.get(i).getDrugs());
                if(drugs.size()>0) {
                    for (int j = 0; j < drugs.size(); j++) {
                        String drugName = null;
                        drugName = drugs.get(j).getName();
                        out.println(drugName);
                    }
                }
            }
            out.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
