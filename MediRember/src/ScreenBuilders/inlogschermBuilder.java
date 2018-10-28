package ScreenBuilders;

import Utility.Dokter;
import Utility.Patiënt;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Utility.DbConnector;

import java.sql.ResultSet;


public class inlogschermBuilder {
    private DbConnector dbConnector;
    private TextField loginnaamTxt;
    private TextField wachtwoordTxt;
    /**
     * deze klasse bouwt het inlogscherm
     */
    public inlogschermBuilder(Scene scene, GridPane inlogscherm, DbConnector dbConnector)
    {
        this.dbConnector = dbConnector;
        Label foutmeldinglbl = new Label("");

        Label loginnaamLbl = new Label("inlognaam: ");
        loginnaamTxt = new TextField();

        Label wachtwoordLbl = new Label("wachtwoord: ");
         wachtwoordTxt = new TextField();

        CheckBox isPatiëntChB = new CheckBox();
        Label isPatiëntLbl = new Label("patiënt:");

        CheckBox isDokterChb = new CheckBox();
        Label isDokterLbl = new Label("dokter:");

        Button inloggenBtn = new Button("log in");

        inloggenBtn.setOnAction(event -> {
        if(!isPatiëntChB.isSelected() && !isDokterChb.isSelected())
        {
            foutmeldinglbl.setText("selcteer de boxes van patiënt of dokter");
        }
        else if(isPatiëntChB.isSelected())
        {
            String querry;
            if(checkInloggegevensPatiënt())
            {
                //met deze querry worden alle gegevens opgevraagd en daarna opgeslagen in een entity patiënt, deze wordt
                //uiteindelijk meegegeven aan
                querry = "SELECT * FROM PATIËNT WHERE Inlognaampatiënt = '" + loginnaamTxt.getText() + "'"  +
                        " AND Wachtwoord = '" + wachtwoordTxt.getText() +"'";
                ResultSet patiëntdata = dbConnector.getData(querry);
                ResultSet medicijndata = null;
                Patiënt patiënt = null;
                try {
                    while(patiëntdata.next()) {
                        System.out.println(patiëntdata.getString(5));
                         patiënt = new Patiënt(patiëntdata.getString(1),patiëntdata.getString(2),
                                patiëntdata.getString(3), patiëntdata.getString(4),patiëntdata.getString(5));
                        querry = "SELECT * FROM MEDICIJN WHERE Medicijnnaam = '" + patiëntdata.getString(5) + "'";
                        medicijndata = dbConnector.getData(querry);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                FlowPane medicijnIneemScherm = new FlowPane();
                new MedicijnIneemScermBuilder(medicijnIneemScherm,patiënt,medicijndata, dbConnector);
                Stage medicijnStage = new Stage();
                medicijnStage.setTitle("patiënt scherm");
                medicijnStage.setScene(new Scene(medicijnIneemScherm,1000,1000));
                medicijnStage.show();

            }
            else
            {
                foutmeldinglbl.setText("de inloggegevens komen niet overeen");
            }

        }
        else if(isDokterChb.isSelected())
        {
            if(checkInloggegevensDokter())
            {
                String querry  = "SELECT * FROM DOKTER WHERE InlognaamDokter = '"
                        + loginnaamTxt.getText() + "'"  + " AND Wachtwoord = '" + wachtwoordTxt.getText() +"'";
                ResultSet dokterData = dbConnector.getData(querry);
                Dokter dokter = null;
                try {
                    if (dokterData.next()) {
                         dokter = new Dokter(dokterData.getString(1), dokterData.getString(2)
                                ,dokterData.getString(3));
                    }
                }
                catch (Exception e)
                {

                }
                BorderPane checkPatiëntenPane = new BorderPane();
                    new CheckPatiëntenBuilder(checkPatiëntenPane, dokter, dbConnector );

                    Stage checkPatiëntenStage = new Stage();
                    checkPatiëntenStage.setScene(new Scene(checkPatiëntenPane,1000,1000));
                    checkPatiëntenStage.setTitle("hoi");
                    checkPatiëntenStage.show();
            }

        }

        });

        inlogscherm.setVgap(6);
        inlogscherm.setHgap(4);

        inlogscherm.add(foutmeldinglbl,1,0);
        inlogscherm.add(loginnaamLbl,0,1  );
        inlogscherm.add(loginnaamTxt,1,1  );
        inlogscherm.add(wachtwoordLbl,0,2 );
        inlogscherm.add(wachtwoordTxt,1,2 );
        inlogscherm.add(isPatiëntLbl,0,3  );
        inlogscherm.add(isPatiëntChB,1,3  );
        inlogscherm.add(isDokterLbl,2,3   );
        inlogscherm.add(isDokterChb,3,3  );
        inlogscherm.add(inloggenBtn,1,4   );
    }

    public boolean checkInloggegevensPatiënt()
    {
        String querry  = "SELECT COUNT(inlognaamPatiënt) FROM PATIËNT WHERE Inlognaampatiënt = '"
                + loginnaamTxt.getText() + "'"  + " AND Wachtwoord = '" + wachtwoordTxt.getText() +"'";
        return dbConnector.checkinlognaam(querry);
    }

    public boolean checkInloggegevensDokter()
    {
        String querry  = "SELECT COUNT(inlognaamDokter) FROM DOKTER WHERE InlognaamDokter = '"
                + loginnaamTxt.getText() + "'"  + " AND Wachtwoord = '" + wachtwoordTxt.getText() +"'";
        return dbConnector.checkinlognaam(querry);
    }
}
