package FrequencyAnalysis;

import Entities.UserLogs;
import FileOps.FileDataReader;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FrequencyAnalyzer {
    private List<?> data;
    private FileDataReader reader = new FileDataReader ();

    public void frequencyReport(String filepath) throws IOException, ParseException {
        Map<String, Integer> studentData = findAbsoluteFrequency (filepath);
        Map<Integer, Integer> exerciseFrequencyCount = new HashMap<> ();
        int sumExercises = 0;

        for(int exerciseCount : studentData.values ()){
            if(exerciseFrequencyCount.containsKey (exerciseCount)){
                exerciseFrequencyCount.replace (exerciseCount, exerciseFrequencyCount.get (exerciseCount), exerciseFrequencyCount.get (exerciseCount) + 1);

            }else{
                exerciseFrequencyCount.put (exerciseCount, 1);
            }
        }

        for(int index: exerciseFrequencyCount.values ()){
             sumExercises += index;
         }

        Set<Integer> exerciseFrequencyCountKeySet = exerciseFrequencyCount.keySet ();
        Iterator<Integer> iterator = exerciseFrequencyCountKeySet.iterator ();

        System.out.println ("Frequency analysis of number of exercises uploaded per student");
        System.out.println ("----------------------------------------------------------------------");
        System.out.println ("| Count uploaded exercises | Absolute frequency | Relative frequency |");
        while(iterator.hasNext ()){
            int index = iterator.next ();
            System.out.println ("|          " + index + "               |" + "          " + exerciseFrequencyCount.get (index) + "         |" + "          " + findRelativeFrequency (sumExercises, (double) exerciseFrequencyCount.get (index)) + "%  |");
        }
        System.out.println ("----------------------------------------------------------------------");
        System.out.println();
    }

    private HashMap<String, Integer> findAbsoluteFrequency(String filepath) throws IOException, ParseException {
        String studentID;
        HashMap<String, Integer> submissionData = new HashMap<> ();
        List<UserLogs> logsData =  reader.readAllDataFromFile (filepath);

        for(int index = 0; index < logsData.size (); index++){
            if(contextIsExercise (logsData, index) && componentIsFileUpload (logsData, index)){
                UserLogs log = logsData.get (index);
                studentID = log.getDescription ().split ("\\s")[4].replace ("'","");
                if(log.getDescription ().contains ("uploaded a file") || log.getDescription ().contains("uploaded '1' file/s")){
                    if(submissionData.containsKey (studentID)){
                        submissionData.replace (studentID, submissionData.get (studentID), submissionData.get (studentID) + 1);
                    }else{
                        submissionData.put (studentID, 1);
                    }
                }else if(log.getDescription ().contains ("uploaded")){
                    int fileCount = Integer.parseInt (log.getDescription ().split ("uploaded")[1].split ("\\s")[1].replace ("'",""));
                    if(submissionData.containsKey (studentID)){
                        submissionData.replace (studentID, submissionData.get (studentID), submissionData.get (studentID) + fileCount);
                    }else{
                        submissionData.put (studentID, fileCount);
                    }
                }
            }
        }
        return submissionData;
    }

    private double findRelativeFrequency(double size, double value){
        return Math.round (value/size * 100);
    }

    private boolean contextIsExercise(List<UserLogs> logsData, int i){
        return logsData.get (i).getContext ().contains ("Assignment: Качване на Упр.");
    }
    private boolean componentIsFileUpload(List<UserLogs> logsData, int i){
        return logsData.get (i).getComponent ().contains ("File submissions");
    }
}
