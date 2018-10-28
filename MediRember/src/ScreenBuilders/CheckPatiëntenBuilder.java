package ScreenBuilders;

import Utility.DbConnector;
import Utility.Dokter;
import Utility.MedicatieIneemMoment;
import Utility.Patiënt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CheckPatiëntenBuilder {
    private TableView table = new TableView();
    private ArrayList<MedicatieIneemMoment> patiëntenIneemmomentLijst;
    private Dokter dokter;
    private DbConnector dbConnector;
    public CheckPatiëntenBuilder(BorderPane checkpatiëntenPane, Dokter dokter, DbConnector dbConnector)
    {
        this.dokter = dokter;
        this.dbConnector = dbConnector;
        Label dokterName = new Label("welkom: " + dokter.getNaam());
        checkpatiëntenPane.setTop(dokterName);
        final Label label = new Label("Medicijn Info");
        table.setEditable(false);
        checkpatiëntenPane.setCenter(table);
        patiëntenIneemmomentLijst = new ArrayList<>();
        try {
            getPatiënts();
        }
        catch (Exception e)
        {

        }

        ObservableList<MedicatieIneemMoment> tableLijstPatiënten = FXCollections.observableArrayList(patiëntenIneemmomentLijst);
        TableColumn patiëntNaam = new TableColumn("patiënt naam");
        TableColumn medicijnNaam = new TableColumn("medicijnNaam");
        TableColumn aantal = new TableColumn("aantal");
        TableColumn wanneer = new TableColumn("wanneer");

        patiëntNaam.setCellValueFactory(new PropertyValueFactory<MedicatieIneemMoment, String>("patiënt"));
        medicijnNaam.setCellValueFactory(new PropertyValueFactory<MedicatieIneemMoment,String>("medicijn"));
        aantal.setCellValueFactory(new PropertyValueFactory<MedicatieIneemMoment, Integer>("aantal"));
        wanneer.setCellValueFactory(new PropertyValueFactory<MedicatieIneemMoment, String>("tijd"));

        table.setItems(tableLijstPatiënten);
        table.getColumns().addAll(patiëntNaam,medicijnNaam,aantal,wanneer);







    }

    public void getPatiënts() throws SQLException {
        String strSQL = "SELECT * FROM PATIËNT WHERE Inlognaamdokter = '" + dokter.getInlognaam() + "'";
        ResultSet patiënten = dbConnector.getData(strSQL);
        ArrayList<Patiënt> patiëntenLijst= new ArrayList<>();
        while(patiënten.next())
        {
            patiëntenLijst.add(new Patiënt(patiënten.getString(1),patiënten.getString(2),
                    patiënten.getString(3),patiënten.getString(4),patiënten.getString(5)));
        }

        for(Patiënt p : patiëntenLijst)
        {
            String strSQL2 = "SELECT * FROM PATIËNTMEDICIJN WHERE Inlognaampatiënt =  '" + p.getInlognaam() + "'";
            ResultSet patiëntenlijstIname = dbConnector.getData(strSQL2);
            while(patiëntenlijstIname.next())
            {
                patiëntenIneemmomentLijst.add(new MedicatieIneemMoment(patiëntenlijstIname.getString(1),
                        patiëntenlijstIname.getString(2),patiëntenlijstIname.getString(4),
                        patiëntenlijstIname.getInt(3)));
            }
        }
    }
}
