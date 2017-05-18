import java.sql.*;
import java.util.Scanner;

/**
 * Created by Auger on 18/05/2017.
 */
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int option;

        do {
            printMenu();

            option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Write the table name you want to get");
                    String tableName = sc.next();
                    showTable(tableName);
                    break;
                case 2:
                    System.out.println("Write the Player's Id");
                    String id = sc.next();
                    System.out.println("Write the Player's Name");
                    String name = sc.next();
                    System.out.println("Write the Player's Surname");
                    String surname1 = sc.next();
                    System.out.println("Write the Player's Second Surname");
                    String surname2 = sc.next();
                    System.out.println("Write the Player's Password");
                    String password = sc.next();
                    System.out.println("Write the Player's Email");
                    String email = sc.next();
                    createPlayer(id, name, surname1, surname2, password, email);
                    System.out.println("Player Created!!");
                    break;
                case 3:
                    System.out.println("Write the player's id you want to get");
                    String playerId = sc.next();
                    getPlayerById(playerId);
                    break;
                case 4:
                    System.out.println("Write the player's id you want to delete");
                    String playerId2 = sc.next();
                    deletePlayerById(playerId2);
                    System.out.println("Player Deleted!");
                    break;
                case 5:
                    System.out.println("Bye bye!!");
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }

        } while (option != 5);
    }

    public static void showTable(String table) {

        try {
            // 1. Create the connection
            Connection con = Singleton.getConnection();

            // 2. Create a Statement
            PreparedStatement stmt;

            //ADDED BY XAVI
            String query = String.format("SELECT * FROM %s", table);
            //END ADDED BY XAVI

            stmt = con.prepareStatement(query);

            // !! We can't use a table name variable for a Java preparedStatement.
            // !! PreparedStatement is only for column values, not table names. Error below:
            // stmt = con.prepareStatement("SELECT * FROM ?");
            // stmt.setString(1, table);

            // 3. Execute SQL Statement
            ResultSet rSet = stmt.executeQuery();

            ResultSetMetaData setMetaData = rSet.getMetaData();
            int colsNum = setMetaData.getColumnCount();

            // 4. Read the Resultset
            String colValue;

            while (rSet.next()) {

                for (int i=1; i<colsNum + 1; i++) {
                    colValue = rSet.getString(i);
                    System.out.print(colValue + "  ");
                }
                System.out.println("");

            }

        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void createPlayer(String id, String nombre, String apellido1, String apellido2, String password, String email) {

        try {
            // 1. Create the connection
            Connection con = Singleton.getConnection();

            // 2. Create a Statement
            PreparedStatement stmt;
            stmt = con.prepareStatement("INSERT INTO players (id, nombre, apellido1, apellido2, password, email)" +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido1);
            stmt.setString(4, apellido2);
            stmt.setString(5, password);
            stmt.setString(6, email);

            // 3. Execute SQL Statement
            stmt.executeUpdate();

        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void getPlayerById(String id) {

        try {
            // 1. Create the connection
            Connection con = Singleton.getConnection();

            // 2. Create a Statement
            PreparedStatement stmt;
            stmt = con.prepareStatement("SELECT * FROM players WHERE id = ?");
            stmt.setString(1, id);

            // 3. Execute SQL Statement
            ResultSet rs = stmt.executeQuery();

            // 4.Read the ResultSet
            //!! In order to access the first row of the resultSet, first we must check if there is a row
            //!! by calling rs.next()
            if(rs.next()) {
                String str = rs.getString("id") + " "
                + rs.getString("nombre") + " "
                + rs.getString("apellido1") + " "
                + rs.getString("apellido2") + " "
                + rs.getString("password") + " "
                + rs.getString("email") + " ";

                System.out.println(str);
            }

        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void deletePlayerById(String id) {

        try {
            // 1. Create the connection
            Connection con = Singleton.getConnection();

            // 2. Create a Statement
            PreparedStatement stmt;
            stmt = con.prepareStatement("DELETE FROM players WHERE id = ?");
            stmt.setString(1, id);

            // 3. Execute SQL Statement
            stmt.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void printMenu() {

        System.out.println("DATABASE OPERATIONS");
        System.out.println("-------------------");
        System.out.println(" 1. Show Table");
        System.out.println(" 2. Create Player");
        System.out.println(" 3. Show Player");
        System.out.println(" 4. Delete Player");
        System.out.println(" 5. Exit");

        System.out.print("\n");
        System.out.print("Please enter the choice what you want to perform in the database: ");
    }


}