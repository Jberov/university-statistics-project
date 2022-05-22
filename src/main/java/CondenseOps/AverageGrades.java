package CondenseOps;

import Entities.Grades;
import FileOps.FileDataReader;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class AverageGrades {

    public void averageGradesReport() throws IOException, ParseException {
        Scanner scan = new Scanner(System.in);
        System.out.println ("Enter file name");
        String filename = scan.nextLine ();

        double avgGrades = calculateAvgGrades(filename);

        System.out.println("Average grade of all students: " + avgGrades);
        System.out.println();
    }

    public double calculateAvgGrades(String filepath) throws IOException, ParseException {
        double avgGradesResult = 0;
        List<Grades> listWithFileData = getFileWithData(filepath);
        int listSize = listWithFileData.size();
        double[] studentGrades = new double[listSize];
        double sumGrades = 0.0;

        for(int index = 0; index < listSize; index++) {
            Grades log = listWithFileData.get(index);
            studentGrades[index] = log.getGrade();
        }
        int allGrades = studentGrades.length;

        for (double studentGrade : studentGrades) {
            sumGrades += studentGrade;
        }

        avgGradesResult = sumGrades / allGrades;

        return avgGradesResult;
    }

    public List<Grades> getFileWithData(String filepath) throws IOException, ParseException {
        FileDataReader reader = new FileDataReader();
        List<Grades> data = reader.readAllDataFromFile(filepath);
        return data;
    }
}