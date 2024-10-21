package basics;
import java.util.Scanner;
 import static java.lang.Math.*;
public class QuadraticRoots {

    public static void main( String[] args) {
        System.out.print("Input a,b,c:");
        Scanner s = new Scanner(System.in)   ;
        int a = s.nextInt();
        int b = s.nextInt();
        int c = s.nextInt();
        double r1 = (-b + sqrt(b*b - 4*a*c)) / 2*a;
        double r2 = (-b - sqrt(b*b - 4*a*c)) / 2*a;
        System.out.println("r1= "+r1+" r2= "+r2);
    }


}
