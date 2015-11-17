package ginRummy;

import java.io.*;

public class IOtests {

   public static void main (String[] args) {

      //  prompt the user to enter their name
      System.out.print("Enter your name: ");

      //  open up standard input
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      String userName = null;

      //  read the username from the command-line; need to use try/catch with the
      //  readLine() method
      try {
         userName = br.readLine();
      } catch (IOException ioe) {
         System.out.println("IO error trying to read your name!");
         System.exit(1);
      }

      System.out.println("Thanks for the name, " + userName);

   }
}
