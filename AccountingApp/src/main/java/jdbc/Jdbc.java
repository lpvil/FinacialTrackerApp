package jdbc;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Jdbc {
    static Scanner sc = new Scanner(System.in);
    private static Connection connection;

    public static void init() {
        try {
            // Ensure the JDBC driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver registered.");

            // Initialize the connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ledger", // Replace with your database name
                    "root", // Replace with your MySQL username
                    "yearup" // Replace with your MySQL password
            );

            // Confirm connection success
            if (connection != null) {
                System.out.println("Connection to the database was successful!");
            } else {
                System.out.println("Failed to establish connection!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Include the library in your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error establishing connection to the database.");
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void makeDeposit(double amount, String vendor, String dscrpt) {
//        // get input using scanner
//        System.out.println("how much would you like to deposit?: ");
//        double amount = sc.nextDouble();
//        sc.nextLine();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
//        System.out.println("who is the vendor?: ");
//        String vendor = sc.nextLine();
//        System.out.println("give brief desciption of deposit: ");
//        String dscrpt = sc.nextLine();

        try ( PreparedStatement preparedStatement = connection.prepareStatement("insert into transactions (date, time, description, vendor, amount) values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setTime(2, Time.valueOf(time));
            preparedStatement.setString(3, dscrpt);
            preparedStatement.setString(4, vendor);
            preparedStatement.setDouble(5, amount);
            preparedStatement.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void makePayment(double amount, String vendor, String dscrpt) {
        // same as make deposti, but preceed amount by a -
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();


        try ( PreparedStatement preparedStatement = connection.prepareStatement("insert into transactions (date, time, description, vendor, amount) values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setTime(2, Time.valueOf(time));
            preparedStatement.setString(3, dscrpt);
            preparedStatement.setString(4, vendor);
            preparedStatement.setDouble(5, amount * -1);
            preparedStatement.executeUpdate();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void displayPayments() {
        try ( PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE amount < 0");
              ResultSet rs = preparedStatement.executeQuery()
        ) {
            while(rs.next()) {
                System.out.print(rs.getDate("date"));
                System.out.print(" ");
                System.out.print(rs.getTime("time"));
                System.out.print(" ");
                System.out.print(rs.getString("description"));
                System.out.print(" ");
                System.out.print(rs.getString("vendor"));
                System.out.print(" ");
                System.out.print(rs.getDouble("amount"));
                System.out.print(" ");
                System.out.println();
            }


        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void displayDesposits() {
        try ( PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE amount > 0");
              ResultSet rs = preparedStatement.executeQuery()
        ) {
            while(rs.next()) {
                System.out.print(rs.getDate("date"));
                System.out.print(" ");
                System.out.print(rs.getTime("time"));
                System.out.print(" ");
                System.out.print(rs.getString("description"));
                System.out.print(" ");
                System.out.print(rs.getString("vendor"));
                System.out.print(" ");
                System.out.print(rs.getDouble("amount"));
                System.out.print(" ");
                System.out.println();
            }


        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void displayAllTransactions() {
        try ( PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions");
              ResultSet rs = preparedStatement.executeQuery()
        ) {
            while(rs.next()) {
                System.out.print(rs.getDate("date"));
                System.out.print(" ");
                System.out.print(rs.getTime("time"));
                System.out.print(" ");
                System.out.print(rs.getString("description"));
                System.out.print(" ");
                System.out.print(rs.getString("vendor"));
                System.out.print(" ");
                System.out.print(rs.getDouble("amount"));
                System.out.print(" ");
                System.out.println();
            }


        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
