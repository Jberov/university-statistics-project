package TendencyFinder;

import java.io.* ;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class Tendency
{
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public double[] tendencyCalc(String csvFilepath)
    {
        Scanner sc;
        int recordNumber = 0;
        int recordsSize = getRecordsNumberInCSV(csvFilepath, 2);
        int[] studentNumbers = new int[recordsSize];
        double[] studentMarks = new double[recordsSize];
        double[] results = new double[3];
        try {
            sc = new Scanner(new File("..\\cfg\\Course A_StudentsResults_Year_1.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Data file Course A_StudentsResults_Year_1.csv not found.");
            return results;
        }
        sc.useDelimiter(",|\n");
        sc.next();
        sc.next();
        while (sc.hasNext())
        {
            studentNumbers[recordNumber] = Integer.parseInt(sc.next());
            studentMarks[recordNumber] = Double.parseDouble(sc.next());
            recordNumber++;
        }
        sc.close();
        results[0] = getAverage(studentMarks, recordsSize);
        results[1] = getMedian(studentMarks, recordsSize);
        results[2] = getMode(studentMarks, recordsSize);
        System.out.println(" Central tangency: " + df.format(results[0]) + "\n Average mark: " + df.format(results[0]) +
                "\n Median of marks records: " + df.format(results[1]) + "\n Mode of mark records: " + df.format(results[2]) + "\n");
        return results;
    }
    public void getPearsonCoef()
    {
        Scanner sc;
        int recordNumber = 0;
        int recordsSize = getRecordsNumberInCSV("..\\cfg\\Course A_StudentsResults_Year_1.csv", 2);
        int[] studentNumbers = new int[recordsSize];
        double[] studentMarks = new double[recordsSize];
        int studentId = 0;
        boolean IDFound = false;
        int NumberOfActvRec = 0;
        try {
            sc = new Scanner(new File("..\\cfg\\Course A_StudentsResults_Year_1.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Data file Course A_StudentsResults_Year_1.csv not found.");
            return;
        }
        sc.useDelimiter(",|\n");
        sc.next();
        sc.next();
        while (sc.hasNext())
        {
            studentNumbers[recordNumber] = Integer.parseInt(sc.next());
            studentMarks[recordNumber] = Double.parseDouble(sc.next());
            recordNumber++;
        }
        sc.close();
        NumberOfActvRec = getRecordsNumberInCSV("..\\cfg\\Logs_Course A_StudentsActivities.csv", 5);
        String[] activity = new String[NumberOfActvRec];
        String[] descritpion = new String[NumberOfActvRec];
        int activityRcrdCnt = 0;
        int uploadedFilesViaStudent = 0;
        try {
            sc = new Scanner(new File("..\\cfg\\Logs_Course A_StudentsActivities.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Data file Logs_Course A_StudentsActivities.csv not found.");
            return;
        }
        sc.useDelimiter(",|\n");
        sc.next();
        sc.next();
        sc.next();
        sc.next();
        sc.next();
        while(sc.hasNext())
        {
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            activity[activityRcrdCnt] = sc.next();
            descritpion[activityRcrdCnt] = sc.next();
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
            System.out.println("Selected ID not found, please selecte valid one.");
            return;
        }
        for (int i = 0; i < activityRcrdCnt; i++) {
            if((descritpion[i].contains(Integer.toString(studentId))) && (activity[i].equals("A file has been uploaded.")))
            {
                uploadedFilesViaStudent++;
            }
        }
        System.out.println("Studnet with id " + studentId + " had uploaded " + uploadedFilesViaStudent + " files.\n");
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
