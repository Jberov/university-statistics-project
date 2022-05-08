package GUI;

import FrequencyAnalysis.FrequencyAnalyzer;
import TendencyFinder.Tendency;
import CondenseOps.AverageGrades;
import CondenseOps.TotalGradedStudents;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner (System.in);

    public Main() {

    }

    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        System.out.println ("Hello, and welcome to the Intelligent system for student participation and success data analysis.");
        int loginCounter = 0;
        while(true) {
            if (isLoginSuccessful ()) {
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
                                    AverageGrades averageGrades = new AverageGrades();
                                    averageGrades.averageGradesReport();
                                    break;
                                case 2:
                                    TotalGradedStudents totalGradedStudents = new TotalGradedStudents();
                                    totalGradedStudents.totalGradedStudentsReport();
                                    break;
                                default:
                                    System.out.println ("Wrong choice: ");
                                    break;
                            }
                            break;
                        case 2:
                            scanner.nextLine ();
                            FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer ();
                            System.out.println ("Enter file name");
                            String filepath = scanner.nextLine ();
                            frequencyAnalyzer.frequencyReport (filepath);
                            break;
                        case 3:
                            scanner.nextLine ();
                            System.out.println ("Enter grades file path:");
                            String gradesFilePath = scanner.nextLine ();
                            System.out.println ("Enter records file path:");
                            String recordsFilePath = scanner.nextLine ();
                            Tendency tendency = new Tendency ();
                            tendency.tendencyCalc(gradesFilePath);
                            tendency.getPearsonCoef(gradesFilePath, recordsFilePath);
                            break;
                        case 4:
                            //Dispersion analysis
                            break;
                        case 5:
                            //Correlational analysis
                            break;
                        default:
                            System.out.println ("Invalid choice. Please enter the digit in front of the desired command");
                            break;

                    }
                }
            } else {
                System.out.println ("Invalid password");
                loginCounter++;
                if (loginCounter == 3) {
                    System.exit (401);
                }
            }
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
        System.out.println ("\n3.Discover tendency of uploaded exercises from a student participation data file.");
        System.out.println ("\n4.Dispersion analysis of uploaded exercises from a student participation data file.");
        System.out.println ("\n5.Correlational analysis of uploaded files from a student participation data file.");
        return scanner.nextInt ();
    }

    private static int condenseMenu() {
        System.out.println ("Please, choose one of the following options, by entering the number preceding each command: ");
        System.out.println ("\n0.Exit.");
        System.out.println ("\n1.Average grade.");
        System.out.println ("\n2.Number of grades");
        return scanner.nextInt ();
    }
}
