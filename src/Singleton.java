import java.sql.*;

/**
 * Created by Auger on 18/05/2017.
 */
public class Singleton {

    private static Connection con = null;

    private Singleton() {}

    public static Connection getConnection() {
        if (con == null) {
            String url = "jdbc:mysql://127.0.0.1:3306/battleship";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "1234";

            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, userName, password);
            }
            catch(ClassNotFoundException ex) {
                ex.printStackTrace();
//                System.out.println("Error: unable to load driver class!");
            }
            catch(SQLException ex) {
                ex.printStackTrace();
            }
        }

        return con;
    }
}
