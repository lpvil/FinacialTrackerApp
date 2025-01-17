package tools;

import java.util.Scanner;

public class HelperMethods {
    public static String getUserAnswer(String q){
        Scanner scanner = new Scanner(System.in);
        System.out.print(q);
        String answer = scanner.nextLine();
        return answer;
    }
}
