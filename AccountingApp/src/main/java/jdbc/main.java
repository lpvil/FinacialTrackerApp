package jdbc;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class main {
    static Scanner sc = new Scanner(System.in);
    private static Connection connection;

    public static void main(String[] args) {
        init();
        String choice = showHomescreen();
        handleChoiceHomescreen(choice);
        String pickone = showLedger();
        ledgerChoice(pickone);
    }

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

    public static String showHomescreen() {
        System.out.println("Welcome to Finacial Tracker");
        System.out.println("What would you like to do?: ");
        System.out.println("Deposit(D), Make a Payment(P), Display ledger(L), Exit(E) ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toUpperCase();
        return choice;
    }

    public static void handleChoiceHomescreen(String choice) {
        switch (choice) {
            case "D":
                makeDeposit();
                break;
            case "P":
                makePayment();
                break;
            case "L":
                showLedger();
                break;
            case "E":
                exit();
                break;
        }
    }

    public static void makeDeposit() {
        // get input using scanner
        System.out.println("how much would you like to deposit?: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        System.out.println("who is the vendor?: ");
        String vendor = sc.nextLine();
        System.out.println("give brief desciption of deposit: ");
        String dscrpt = sc.nextLine();

        try ( PreparedStatement preparedStatement = connection.prepareStatement("insert into transactions (date, time, description, vendor, amount) values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setTime(2, Time.valueOf(time));
            preparedStatement.setString(3, dscrpt);
            preparedStatement.setString(4, vendor);
            preparedStatement.setDouble(5, amount);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void makePayment() {
        // same as make deposti, but preceed amount by a -
        System.out.println("how much of a payment would you like to make?: ");
        double amount = sc.nextDouble() * -1;
        sc.nextLine();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        System.out.println("who is the vendor?: ");
        String vendor = sc.nextLine();
        System.out.println("give brief desciption of payment: ");
        String dscrpt = sc.nextLine();
        // create a transaction string with the input
        String transaction = date.toString() + "|" + time.toString() + "|" + dscrpt + "|" + vendor + "|" + amount;
        // call the method to add the transaction
        addTransaction(transaction);
    }

    public static String showLedger() {
        System.out.println("Welcome to ledger screen!");
        System.out.println("here are some options:");
        System.out.println("Display All (A), Display Deposits(B), Display Payments(C), Home(H) ");
        String pickone = sc.nextLine().toUpperCase();
        return pickone;
    }

    public static void ledgerChoice(String pickone) {
        switch (pickone) {
            case "A":
                displayAllTransactions();
                break;
            case "B":
                displayDesposits();
                break;
            case "C":
                displayPayments();
                break;
            case "H":
                goHome();
                break;
        }
    }

    public static void exit() {
        System.out.println("closing application...");
    }

    public static void displayPayments() {
        try {
            FileReader freader = new FileReader("src\\transactions.csv");
            BufferedReader reader = new BufferedReader(freader);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void displayDesposits() {

    }

    public static void displayAllTransactions() {
        try {
            FileReader fr = new FileReader("src\\transactions.csv");
            BufferedReader br = new BufferedReader(fr);
            String file1;
            while ((file1 = br.readLine()) != null) {
                String[] fileInfo = file1.split(Pattern.quote("|"));
                System.out.println(file1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void goHome() {
        showHomescreen();
    }

    //helper method by maaike
    public static List<String[]> getTransactionsFromFile() {
        List<String[]> transactions = new ArrayList<>();
        // we'll read transactions from file and add them tot our list
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src\\transactions.csv"));
            bf.readLine();
            String line;
            while ((line = bf.readLine()) != null) {
                String[] transaction = line.split("\\|");
                transactions.add(transaction);
            }
            bf.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return transactions;
    }

    // helper method by maaike
    public static void addTransaction(String transaction) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src\\transactions.csv", true));
            bw.write("\n" + transaction);
            bw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
