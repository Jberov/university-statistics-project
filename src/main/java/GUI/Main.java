package GUI;

import FileOps.FileDataReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner (System.in);

    public Main() {

    }

    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        System.out.println ("Hello, and welcome to the Intelligent system for student participation and success data analysis.");
        if (isLoginSuccessful ()) {
            loadFiles();
            while (true) {
                switch (startingMenu ()) {
                    case 0:
                        System.out.println ("\nLogging out.");
                        return;
                    case 1:
                        switch (condenseMenu ()) {
                            case 0:
                                break;
                            case 1:
                                //Find average grade
                                break;
                            case 2:
                                //Count all students
                                break;
                            default:
                                System.out.println ("Wrong choice: ");
                                break;
                        }
                        break;
                    case 2:
                        //Frequency analysis
                        break;
                    case 3:
                        //Condense data
                        break;
                    case 4:
                        //Tendency
                        break;
                    case 5:
                        //Dispersion analysis
                        break;
                    case 6:
                        //Correlational analysis
                        break;
                    default:
                        System.out.println ("Invalid choice. Please enter the digit in front of the desired command");
                        break;

                }
            }
        }
        else{
            System.out.println ("Invalid password");
        }
    }

    private static boolean isLoginSuccessful() {
        String pass;
        while (true) {
            System.out.println ("\nPlease, enter your provided password: ");
            pass = scanner.nextLine ();
            if (pass.equals ("D782000b")) {
                System.out.println ("\nLogin successful. You may proceed");
                return true;
            } else if (pass.equalsIgnoreCase ("exit")) {
                System.out.println ("\nGoodbye");
                return false;
            }
            System.out.println ("\nWrong password. If you wish to continue, please enter correct pass. Otherwise, enter \"exit\" to exit the system");
        }
    }

    private static int startingMenu() {
        System.out.println ("Please, choose one of the following options, by entering the number preceding each command: ");
        System.out.println ("\n0.Exit.");
        System.out.println ("\n1.Condense all grade data.");
        System.out.println ("\n2.Frequency analysis of student participation data");
        System.out.println ("\n3.Condense all students grade data.");
        System.out.println ("\n4.Discover tendency of uploaded exercises from a student participation data file.");
        System.out.println ("\n5.Dispersion analysis of uploaded exercises from a student participation data file.");
        System.out.println ("\n6.Correlational analysis of uploaded files from a student participation data file.");
        return scanner.nextInt ();
    }

    private static int condenseMenu() {
        System.out.println ("Please, choose one of the following options, by entering the number preceding each command: ");
        System.out.println ("\n0.Exit.");
        System.out.println ("\n1.Average grade.");
        System.out.println ("\n2.Number of grades");
        return scanner.nextInt ();
    }

    private static void loadFiles() throws IOException {

    }
    private static void visualizeFrequencyAnalysisTable(List<Object> data){
        System.out.println ("Count of student participation data " + "\t" + "|" + "\t" + "Absolute frequency" + "\t" + "|" + "\t" + "Relative frequency");
        for(Object student : data){
            System.out.println (student + "\t" + "|" + "\t" + student + "\t" + "|" + "\t" + student);
        }
        System.out.println ("Total" + "\t" + "|" + "\t" + data.size () + "\t" + "|" + "\t" + "100%");

    }
}
