package FileOps;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FileVerifier {
    private final Scanner scanner = new Scanner (System.in);

    private boolean checkFilePath(String filepath) throws FileNotFoundException {
        File file = new File (filepath);
        if(!file.exists () || !file.isFile () ){
            throw new FileNotFoundException ("No such file");
        }
        return true;
    }

    private boolean checkExtension(String filepath){
        return filepath.contains (".xlsx");
    }

    private boolean checkFileFormatting(String fileLocation, String fileType) throws IOException {
        FileInputStream file = new FileInputStream (fileLocation);
        Workbook workbook = new XSSFWorkbook (file);
        Sheet sheet = workbook.getSheetAt (0);
        if(fileType.equalsIgnoreCase ("grades")){
            for(Row row : sheet){
                System.out.println ("Here" + row.getPhysicalNumberOfCells ());
                if(row.getPhysicalNumberOfCells () != 2){
                    return false;
                }
            }
        }else if(fileType.equalsIgnoreCase ("logs")){
            for(Row row : sheet){
                if(row.getPhysicalNumberOfCells () != 5){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verifyFile(String filePath) throws IOException {
        System.out.println ("Enter what kind of file you are loading: logs or grades?");
        String fileType = scanner.nextLine ();
        FileDataReader.setFileType (fileType);
        System.out.println (checkFileFormatting (filePath, fileType));
        return checkFilePath (filePath) && checkExtension (filePath) && checkFileFormatting (filePath, fileType);
    }
}
