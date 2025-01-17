package screens;

import jdbc.Jdbc;
import tools.HelperMethods;

public class Payment {
    public static void MakePayment(){
    String amount = HelperMethods.getUserAnswer("Payment amount: ");
    double payment = Double.parseDouble(amount);
    String vendor = HelperMethods.getUserAnswer("Vendor: ");
    String description = HelperMethods.getUserAnswer("Description: ");

        Jdbc.makePayment(payment, amount, description);

        System.out.println("\n  Payment: $" + payment  +
            "\n Vendor: " + vendor +
            "\n Description: " + description + "\n\n*This data has been saved!*");

    }
}
