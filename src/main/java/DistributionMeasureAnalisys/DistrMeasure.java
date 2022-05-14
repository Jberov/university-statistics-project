package DistributionMeasureAnalisys;

import Entities.UserLogs;
import FileOps.FileDataReader;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class DistrMeasure {
    public void distributionAnalysisReport() throws IOException, ParseException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter file name");
        String filename = scan.nextLine();
        Map<String, Integer> idUploadedFilesMap = getSubmissionDataForUploadedFiles(filename);
        int distributionResult = getDataRange(idUploadedFilesMap);

        System.out.println("Distribution analysis of uploaded exercises: " + distributionResult);
        System.out.println();
    }

    public HashMap<String, Integer> getSubmissionDataForUploadedFiles(String filepath) throws IOException, ParseException {
        FileDataReader reader = new FileDataReader();
        String studentID;
        HashMap<String, Integer> submissionData = new HashMap<>();
        List<UserLogs> logsData =  reader.readAllDataFromFile(filepath);

        for(int index = 0; index < logsData.size (); index++){
            if(contextIsExercise(logsData, index) && componentIsFileUpload(logsData, index)){
                UserLogs log = logsData.get(index);
                studentID = log.getDescription().split("\\s")[4].replace("'","");
                if(log.getDescription().contains("uploaded a file") || log.getDescription().contains("uploaded '1' file/s")){
                    if(submissionData.containsKey(studentID)){
                        submissionData.replace(studentID, submissionData.get(studentID), submissionData.get(studentID) + 1);
                    }else{
                        submissionData.put(studentID, 1);
                    }
                }else if(log.getDescription().contains("uploaded")){
                    int fileCount = Integer.parseInt(log.getDescription().split("uploaded")[1].split("\\s")[1].replace("'",""));
                    if(submissionData.containsKey(studentID)){
                        submissionData.replace(studentID, submissionData.get(studentID), submissionData.get(studentID) + fileCount);
                    }else{
                        submissionData.put(studentID, fileCount);
                    }
                }
            }
        }
        return submissionData;
    }

    public int getDataRange(Map<String, Integer> idUploadedFilesMap) {
        int dataRangeResult;
        int maxValue = 0;
        int minValue = 0;
        int maxValueInMap = (Collections.max(idUploadedFilesMap.values()));
        int minValueInMap = (Collections.min(idUploadedFilesMap.values()));

        for (Map.Entry<String, Integer> entry : idUploadedFilesMap.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                maxValue = entry.getValue();
            }
        }

        for (Map.Entry<String, Integer> entry : idUploadedFilesMap.entrySet()) {
            if (entry.getValue() == minValueInMap) {
                minValue = entry.getValue();
            }
        }

        dataRangeResult = maxValue - minValue;

        return dataRangeResult;
    }

    private boolean contextIsExercise(List<UserLogs> logsData, int i){
        return logsData.get (i).getContext ().contains ("Assignment: Качване на Упр.");
    }
    private boolean componentIsFileUpload(List<UserLogs> logsData, int i){
        return logsData.get (i).getComponent ().contains ("File submissions");
    }
}
