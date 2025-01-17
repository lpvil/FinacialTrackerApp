package screens;
import tools.*;
public class HomeScreen {

    public static void ShowHomeScreen() {
        System.out.println("Welcome to Finacial Tracker");
        System.out.println("What would you like to do?: ");
        System.out.println("Deposit(D), Make a Payment(P), Display ledger(L), Exit(E) ");
        String answer = HelperMethods.getUserAnswer("Your choice: ");
        handleSwitch(answer);

    }

    public static void handleSwitch(String answer){

        switch (answer){
            case "D": case "d":
                Deposit.Makedeposit();
                break;
            case "P": case "p":
                Payment.MakePayment();
                break;
            case "L": case "l":
                Ledger.ledger();
                break;
            case "E": case "e": case "o": case "O": case "0":
                Exit.kill();
                break;
            default:
                System.out.println("Not a choice!");

        }
    }
}
