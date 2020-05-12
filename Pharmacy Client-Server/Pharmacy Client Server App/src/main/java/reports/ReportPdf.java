package reports;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Drug;
import model.User;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReportPdf implements Reports {

    @Override
    public void createReport(List<User> users) {
        try{
            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
            chooser.getExtensionFilters().add(extFilter);
            File file = chooser.showSaveDialog(new Stage());

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file ));

            document.open();
            document.addAuthor("Vlad Georgescu");
            document.addTitle("Drugs Bought");
            document.add(new Paragraph("                                                              Report With Drugs Bought                                            Author: Vlad Georgescu\n\n"));
            document.add(new Paragraph("Drugs Bought   \n\n" ));

            for(int i=0; i<users.size(); i++){
                List<Drug> drugs = new ArrayList<Drug>();
                drugs.addAll(users.get(i).getDrugs());
                for(int j=0; j<drugs.size(); j++)
                {
                    String drugName = null;
                    drugName = drugs.get(j).getName();
                    drugName = drugName + "\n";

                    Paragraph p = new Paragraph(drugName);
                    document.add(p);
                }
            }
            document.close();
        }catch(Exception ex) {
        }
    }
}
