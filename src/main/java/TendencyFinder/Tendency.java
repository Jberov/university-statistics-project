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
        int studentId;
        boolean IDFound = false;
        int NumberOfActvRec;

        for (Grades grade : data)
        {
            studentNumbers[recordNumber] = Integer.parseInt(grade.getId ());
            studentMarks[recordNumber] = grade.getGrade ();
            recordNumber++;
        }
        NumberOfActvRec = fileDataReader.readAllDataFromFile (recordFilePath).size ();
        String[] activity = new String[NumberOfActvRec];
        String[] description = new String[NumberOfActvRec];
        int activityRcrdCnt = 0;
        int uploadedFilesViaStudent = 0;
        List<UserLogs> userData = fileDataReader.readAllDataFromFile (recordFilePath);

        for (UserLogs user : userData ){
            activity[activityRcrdCnt] = user.getEventName ();
            description[activityRcrdCnt] = user.getDescription ();
            activityRcrdCnt++;
        }
        System.out.println("Enter student ID for which to get data.");
        Scanner myObj = new Scanner(System.in);
        studentId = Integer.parseInt(myObj.nextLine());
        myObj.close();

        for (int index : studentNumbers) {
            if (index == studentId) {
                IDFound = true;
                break;
            }
        }
        if(!IDFound)
        {
            System.out.println("Selected ID not found, please select valid one.");
            return;
        }
        for (int index = 0; index < activityRcrdCnt; index++) {
            if((description[index].contains(Integer.toString(studentId))) && (activity[index].equals("A file has been uploaded.")))
            {
                uploadedFilesViaStudent++;
            }
        }
        System.out.println("Student with id " + studentId + " had uploaded " + uploadedFilesViaStudent + " files.\n");
    }
    private double getMode(double[] studentMarks, int recordsSize)
    {
        double resL = 0;
        int maxCount = 0, index, secondIndex;

        for (index = 0; index < recordsSize; ++index)
        {
            int count = 0;
            for (secondIndex = 0; secondIndex < recordsSize; ++secondIndex)
            {
                if(studentMarks[secondIndex] == studentMarks[secondIndex])
                {
                    ++count;
                }
            }

            if (count > maxCount) {
                maxCount = count;
                resL = studentMarks[index];
            }
        }

        return resL;
    }

    private double getMedian(double[] studentMarks, int recordsSize)
    {
        double resL;

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
        double resL;
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
        for (int index = 0; index < RecordsOnRow; index++) {
            sc.next();
        }
        if(5 == RecordsOnRow)
        {
            RecordsOnRow+=1;
        }
        while(sc.hasNext())
        {
            for (int index = 0; index < RecordsOnRow; index++) {
                sc.next();
            }
            resL+=1;
        }
        sc.close();

        return resL;
    }
}
