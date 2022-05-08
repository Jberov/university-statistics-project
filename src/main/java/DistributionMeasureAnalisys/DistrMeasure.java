package DistributionMeasureAnalisys;

import Entities.UserLogs;
import FileOps.FileDataReader;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DistrMeasure {
    public void averageGradesReport() throws IOException, ParseException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter file name");
        String filename = scan.nextLine();

        Map<String, Integer> idUploadedFilesMap = getSubmissionDataForUploadedFiles(filename);
    }

    public HashMap<String, Integer> getSubmissionDataForUploadedFiles(String filepath) throws IOException, ParseException {
        FileDataReader reader = new FileDataReader();
        String studentID;
        HashMap<String, Integer> submissionData = new HashMap<>();
        List<UserLogs> logsData =  reader.readAllDataFromFile(filepath);
        for(int i = 0; i < logsData.size (); i++){
            if(contextIsExercise(logsData, i) && componentIsFileUpload(logsData, i)){
                UserLogs log = logsData.get(i);
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
        //TODO: distribution range
        return 0;
    }

    private boolean contextIsExercise(List<UserLogs> logsData, int i){
        return logsData.get (i).getContext ().contains ("Assignment: Качване на Упр.");
    }
    private boolean componentIsFileUpload(List<UserLogs> logsData, int i){
        return logsData.get (i).getComponent ().contains ("File submissions");
    }
}
