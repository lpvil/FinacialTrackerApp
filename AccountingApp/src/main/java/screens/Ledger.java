package screens;

import jdbc.Jdbc;
import tools.HelperMethods;

public class Ledger {
    public static  void ledger(){
        String choice = HelperMethods.getUserAnswer("\nDisplay (D)eposits" +
                "\nDisplay (P)ayments" +
                "\nDisplay (A)ll transaction");

        switch (choice){
            case "D": case "d":
                Jdbc.displayDesposits();
                break;
            case "P": case "p":
                Jdbc.displayPayments();
                break;
            case "A": case "a":
                Jdbc.displayAllTransactions();
                break;
        }
    }
}
