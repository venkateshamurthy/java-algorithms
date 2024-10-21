package basics;

import java.util.Scanner;

public class Factorial {
    public static void main(String[] args) {
        long fact = 1L;
        System.out.print("Please enter the number:");

        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        while (num-- > 1) {
            fact = fact * num;
        }
        System.out.println("Factorial= " +fact);
    }
}
