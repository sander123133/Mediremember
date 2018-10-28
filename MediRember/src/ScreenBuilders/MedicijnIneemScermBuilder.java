package ScreenBuilders;

import Utility.Medicijn;
import Utility.Patiënt;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import Utility.DbConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MedicijnIneemScermBuilder {
    Patiënt patiënt;
    ArrayList<Medicijn> medicijnen;
    public MedicijnIneemScermBuilder(BorderPane root,Patiënt patiënt, DbConnector dbConnector)
    {
        BorderPane patiëntInfoScherm = new BorderPane();
        FlowPane northPane = new FlowPane();
        GridPane centerPane = new GridPane();
        medicijnen = new ArrayList<>();


        Label patiëntNaam =  new Label();
        Label naamDokter = new Label();
        this.patiënt = patiënt;
        try
        {
            String strSQL = "SELECT Medicijn FROM MEDICIJNPATIËNT WHERE Inlognaampatiënt = '" + patiënt.getInlognaam() + "'";
            ResultSet medicijnNaam = dbConnector.getData(strSQL);
            String medicijnStrSQL;
            ResultSet medicijnInfo;
            while (medicijnNaam.next()) {
                medicijnStrSQL = "SELECT * FROM MEDICIJN WHERE Medicijnnaam = '" + medicijnNaam.getString(1) + "'";
                medicijnInfo = dbConnector.getData(medicijnStrSQL);
                while (medicijnInfo.next()) {
                    medicijnen.add(new Medicijn(medicijnInfo.getString(1), medicijnInfo.getString(2)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        for(int location = 0; location < medicijnen.size(); location++) {
            VBox medicijnIneemPane = new VBox();
            Label medicijnLabel = new Label();
            Label medicijnBeschrijving = new Label();
            Medicijn medicijn = medicijnen.get(location);
            medicijnLabel.setText(medicijn.getNaam());
            medicijnBeschrijving.setText(medicijn.getBeschrijving());
            Label aantalLbl = new Label("hoevel ingenomen: ");
            TextField aantalTxt = new TextField();
            Button neemIn = new Button("neem in");
            neemIn.setOnAction(event -> {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dbConnector.voegIneemMomentToe(patiënt.getInlognaam(),medicijn.getNaam(),Integer.parseInt(aantalTxt.getText()),now.format(format));
            });
            medicijnIneemPane.getChildren().addAll(medicijnLabel,medicijnBeschrijving,aantalLbl,aantalTxt,neemIn);
            centerPane.add(medicijnIneemPane,location,0);
        }
        patiëntNaam.setText("naam patiënt: " + patiënt.getNaam());
        naamDokter.setText("naam doktor: " + patiënt.getNaamDokter());
        northPane.getChildren().addAll(patiëntNaam,naamDokter);



        patiëntInfoScherm.getChildren().addAll(patiëntNaam,naamDokter);
        root.setTop(northPane);
        root.setCenter(centerPane);







    }
}
