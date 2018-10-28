package Utility;

import ScreenBuilders.InlogschermBuilder;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.sql.*;

public class DbConnector {
    private ResultSet result = null;
    private Connection conn;
    private Stage succesStage = new Stage();
    public DbConnector() {
        VBox pane = new VBox();
        Label succeslbl = new Label("Het versturen van het inneemmoment is gelukt");
        succeslbl.setFont(InlogschermBuilder.groteLetters);
        Button succesBtn = new Button("OK");
        succesBtn.setOnAction(event -> succesStage.close());
        pane.getChildren().addAll(succeslbl,succesBtn);
        succesStage.setTitle("succes");
        succesStage.setScene(new Scene(pane,500,120));
        createConnection();
    }

    private Connection createConnection() {
         conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String strConnString = "jdbc:oracle:thin:@localhost:1521:xe";
            conn = DriverManager.getConnection(strConnString, "DOKTER+", "123456789");
        } catch (Exception var3) {
            System.out.println("OOF");
            var3.printStackTrace();
        }
           return conn;
    }

    public void voegIneemMomentToe(String inlognaamPatiënt, String medicijnNaam, int aantal, String tijd )
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO PATIËNTMEDICIJN(TIJD,INLOGNAAMPATIËNT, MEDICIJNNAAM, AANTAL)" + "VALUES(?,?,?,?)");
            stmt.setString(2, inlognaamPatiënt);
            stmt.setString(3,medicijnNaam);
            stmt.setInt(4,aantal);
            stmt.setString(1,tijd);
            stmt.executeUpdate();
            succesStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ResultSet getData(String strSQL) {
        try {
            Statement stmt = conn.createStatement();
            this.result = stmt.executeQuery(strSQL);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return this.result;
    }

    public boolean checkinlognaam(String strSQL)
    {
        int resultString = 0;
        boolean inlogcorrect = false;
        try {
            Statement stmt = conn.createStatement();
            this.result = stmt.executeQuery(strSQL);
            while(result.next())
             resultString = Integer.parseInt(result.getString(1));
            if(resultString == 0)
            {
                inlogcorrect = false;
            }
            else
            {
                inlogcorrect = true;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return inlogcorrect;
    }

}
