package Utility;

import java.sql.*;

public class DbConnector {
    ResultSet result = null;
    Connection conn;
    public DbConnector() {
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
