package screens;
import jdbc.Jdbc;
import tools.*;
public class Deposit {
    public static void Makedeposit(){
       String amount = HelperMethods.getUserAnswer("Deposit amount: ");
       double payment = Double.parseDouble(amount);
       String vendor = HelperMethods.getUserAnswer("Vendor: ");
       String description = HelperMethods.getUserAnswer("Description: ");

        Jdbc.makeDeposit(payment, vendor, description);

        System.out.println("\n  Deposit: $" + payment  +
                "\n Vendor: " + vendor +
                "\n Description: " + description + "\n\n*This data has been saved!*");

    }
}
