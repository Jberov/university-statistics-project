package TendencyFinder;

import Entities.Grades;
import Entities.UserLogs;
import FileOps.FileDataReader;

import java.io.* ;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Tendency
{
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public double[] tendencyCalc(String csvFilepath) throws IOException, ParseException {
        FileDataReader fileDataReader = new FileDataReader ();
        List<Grades> data = fileDataReader.readAllDataFromFile (csvFilepath);
        int recordNumber = 0;
        int recordsSize = data.size ();
        int[] studentNumbers = new int[recordsSize];
        double[] studentMarks = new double[recordsSize];
        double[] results = new double[3];

        for (Grades grade : data) {
            studentNumbers[recordNumber] = Integer.parseInt (grade.getId ());
            studentMarks[recordNumber] = grade.getGrade ();
            recordNumber++;
        }
        results[0] = getAverage(studentMarks, recordsSize);
        results[1] = getMedian(studentMarks, recordsSize);
        results[2] = getMode(studentMarks, recordsSize);
        System.out.println(" Central tangency: " + df.format(results[0]) + "\n Average mark: " + df.format(results[0]) +
                "\n Median of marks records: " + df.format(results[1]) + "\n Mode of mark records: " + df.format(results[2]) + "\n");
        return results;
    }
    public void getPearsonCoef(String filepath, String recordFilePath) throws IOException, ParseException {
        FileDataReader fileDataReader = new FileDataReader ();
        List<Grades> data = fileDataReader.readAllDataFromFile (filepath);
        int recordNumber = 0;
        int recordsSize = data.size ();
        int[] studentNumbers = new int[recordsSize];
        double[] studentMarks = new double[recordsSize];
        int studentId = 0;
        boolean IDFound = false;
        int NumberOfActvRec = 0;

        for (Grades grade : data)
        {
            studentNumbers[recordNumber] = Integer.parseInt(grade.getId ());
            studentMarks[recordNumber] = grade.getGrade ();
            recordNumber++;
        }
        NumberOfActvRec = fileDataReader.readAllDataFromFile (recordFilePath).size ();
        String[] activity = new String[NumberOfActvRec];
        String[] descritpion = new String[NumberOfActvRec];
        int activityRcrdCnt = 0;
        int uploadedFilesViaStudent = 0;
        List<UserLogs> userData = fileDataReader.readAllDataFromFile (recordFilePath);
        for (UserLogs user : userData ){
            activity[activityRcrdCnt] = user.getEventName ();
            descritpion[activityRcrdCnt] = user.getDescription ();
            activityRcrdCnt++;
        }
        System.out.println("Enter student ID for which to get data.");
        Scanner myObj = new Scanner(System.in);
        studentId = Integer.parseInt(myObj.nextLine());
        myObj.close();

        for (int i : studentNumbers) {
            if( i == studentId)
            {
                IDFound = true;
            }
        }
        if(!IDFound)
        {
            System.out.println("Selected ID not found, please select valid one.");
            return;
        }
        for (int i = 0; i < activityRcrdCnt; i++) {
            if((descritpion[i].contains(Integer.toString(studentId))) && (activity[i].equals("A file has been uploaded.")))
            {
                uploadedFilesViaStudent++;
            }
        }
        System.out.println("Student with id " + studentId + " had uploaded " + uploadedFilesViaStudent + " files.\n");
    }
    private double getMode(double[] studentMarks, int recordsSize)
    {
        double resL = 0;
        int maxCount = 0, i, j;

        for (i = 0; i < recordsSize; ++i)
        {
            int count = 0;
            for (j = 0; j < recordsSize; ++j)
            {
                if(studentMarks[j] == studentMarks[i])
                {
                    ++count;
                }
            }

            if (count > maxCount) {
                maxCount = count;
                resL = studentMarks[i];
            }
        }

        return resL;
    }

    private double getMedian(double[] studentMarks, int recordsSize)
    {
        double resL = 0;
        Arrays.sort(studentMarks);
        if(recordsSize%2 == 0)
        {
            resL = ((studentMarks[(recordsSize/2)] + studentMarks[(recordsSize/2)+1])/2);
        }
        else
        {
            resL = studentMarks[recordsSize];
        }
        return resL;
    }

    private double getAverage(double[] studentMarks, int recordsSize)
    {
        double resL = 0;
        double sumOfMarks = 0;
        for (double d : studentMarks) {
            sumOfMarks += d;
        }
        resL = (sumOfMarks / recordsSize);
        return resL;
    }

    private int getRecordsNumberInCSV(String path,int RecordsOnRow)
    {
        int resL = 0;
        Scanner sc;
        try {
            sc = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            return 0;
        }
        sc.useDelimiter(",|\n");
        for (int i = 0; i < RecordsOnRow; i++) {
            sc.next();
        }
        if(5 == RecordsOnRow)
        {
            RecordsOnRow+=1;
        }
        while(sc.hasNext())
        {
            for (int i = 0; i < RecordsOnRow; i++) {
                sc.next();
            }
            resL+=1;
        }
        sc.close();

        return resL;
    }
}
