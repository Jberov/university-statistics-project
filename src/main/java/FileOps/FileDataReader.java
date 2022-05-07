package FileOps;

import Entities.Grades;
import Entities.UserLogs;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class FileDataReader {

    private final FileVerifier fileVerifier = new FileVerifier();

    public static void setFileType(String fileType) {
        FileDataReader.fileType = fileType;
    }

    private static String fileType;

    public List readAllDataFromFile(String filepath) throws IOException, ParseException {
        Sheet sheet = openFile (filepath);
        if(fileType.equalsIgnoreCase ("logs")) {
            List <UserLogs> data = new ArrayList<> ();
            Row header =  sheet.getRow (0);
            sheet.removeRow (header);
            for (Row row : sheet) {
                data.add ( setLogsObject (row));
            }
            for (UserLogs log : data){
                System.out.println (log.getDate ());
            }
            return data;
        }else if(fileType.equalsIgnoreCase ("grades")){
            List <Grades> data = new ArrayList<> ();
            for (Row row : sheet) {
                if(sheet.getRow (0).equals (row)){
                    continue;
                }
                data.add ( setGradesEntry (row));
            }
            return data;
        }else{
            throw new InputMismatchException ("Invalid data type");
        }
    }

    private UserLogs setLogsObject( Row row) throws ParseException {
        UserLogs logs = new UserLogs ();
        logs.setDate (row.getCell (0).getStringCellValue ());
        logs.setContext (row.getCell (1).getStringCellValue ());
        logs.setComponent (row.getCell (2).getStringCellValue ());
        logs.setEventName (row.getCell (3).getStringCellValue ());
        logs.setDescription (row.getCell (4).getStringCellValue ());
        return logs;
    }

    private Grades setGradesEntry( Row row){
        Grades grades = new Grades ();
        grades.setId (row.getCell (0).getStringCellValue ());
        grades.setGrade (row.getCell (1).getNumericCellValue ());
        return grades;
    }

    private Sheet openFile(String filepath) throws IOException {
        if(!fileVerifier.verifyFile (filepath)){
            throw new IOException ("Invalid file or non-existant file");
        }
        FileInputStream file = new FileInputStream (filepath);
        Workbook workbook = new XSSFWorkbook (file);
        return workbook.getSheetAt (0);
    }
}
