import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class DBConnect implements Runnable {
    private Connection connection;
    private final String DBURL ="";
    private final String UNAME = "";
    private final String UPASS = "";
    private Statement sqlStatement;




    @Override
    public void run() {
        dbUpload();
    }

    private void dbUpload() {
        try {
            connection = DriverManager.getConnection(DBURL,UNAME,UPASS);
            sqlStatement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
