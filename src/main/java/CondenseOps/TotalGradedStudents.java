package CondenseOps;

import Entities.Grades;
import FileOps.FileDataReader;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class TotalGradedStudents {
    public void totalGradedStudentsReport() throws IOException, ParseException {
        Scanner scan = new Scanner(System.in);
        System.out.println ("Enter file name");
        String filename = scan.nextLine ();

        int totalGradedStudents = calculateTotalGradedStudents(filename);

        System.out.println("Total graded students: " + totalGradedStudents);
    }

    public int calculateTotalGradedStudents(String filepath) throws IOException, ParseException {
        int totalGradedStudentsResult = 0;
        List<Grades> listWithFileData = getFileWithData(filepath);
        int listSize = listWithFileData.size();
        String[] studentIds = new String[listSize];

        for(int i = 0; i < listSize; i++) {
            Grades log = listWithFileData.get(i);
            studentIds[i] = log.getId();
        }
        totalGradedStudentsResult = studentIds.length;

        return totalGradedStudentsResult;
    }

    public List<Grades> getFileWithData(String filepath) throws IOException, ParseException {
        FileDataReader reader = new FileDataReader();
        List<Grades> data = reader.readAllDataFromFile(filepath);
        return data;
    }
}
