import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Mark on 9/15/2016.
 * A class to test porter stemmer
 */
public class PorterMain {
   public static void main(String[] args){
      PorterStemmer porter = new PorterStemmer();
      System.out.println("Enter string: ");
      Scanner scan = new Scanner(System.in);
      while(true) {
         String s = scan.next();
         String replaced = porter.processToken(s);
         System.out.println(replaced);
//         System.out.println(porter.testRegex(s));
      }
   }
}
