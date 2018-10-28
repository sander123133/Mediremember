package ScreenBuilders;

import Utility.Medicijn;
import Utility.Patiënt;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import Utility.DbConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MedicijnIneemScermBuilder {
    Patiënt patiënt;
    Medicijn medicijn;
    public MedicijnIneemScermBuilder(FlowPane root,Patiënt patiënt, ResultSet medicijnInfo, DbConnector dbConnector)
    {
        FlowPane patiëntInfoScherm = new FlowPane();

        Label medicijnLabel = new Label();
        Label medicijnBeschrijving = new Label();

        Label patiëntNaam =  new Label();
        Label naamDokter = new Label();
        this.patiënt = patiënt;
        try
        {
            while (medicijnInfo.next())
            medicijn = new Medicijn(medicijnInfo.getString(1), medicijnInfo.getString(2));
        } catch (SQLException e) {

        }
        medicijnLabel.setText(medicijn.getNaam());
        medicijnBeschrijving.setText(medicijn.getBeschrijving());
        patiëntNaam.setText("naam patiënt: " + patiënt.getNaam());
        naamDokter.setText("naam doktor: " + patiënt.getNaamDokter());

        Label aantalLbl = new Label("hoevel ingenomen: ");
        TextField aantalTxt = new TextField();
        Button neemIn = new Button("neem in");

        neemIn.setOnAction(event -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                dbConnector.insertRow(patiënt.getInlognaam(),medicijn.getNaam(),Integer.parseInt(aantalTxt.getText()),now.format(format));
        });
        patiëntInfoScherm.getChildren().addAll(patiëntNaam,naamDokter);
        root.getChildren().addAll(patiëntInfoScherm,medicijnLabel,medicijnBeschrijving,aantalLbl, aantalTxt, neemIn);







    }
}
