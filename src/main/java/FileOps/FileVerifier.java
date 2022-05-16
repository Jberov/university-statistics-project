package FileOps;

import java.io.File;
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

    public boolean verifyFile(String filePath) throws IOException {
        if (!checkExtension (filePath)) {
            throw new IOException ("Wrong file extension");
        }
        return checkFilePath (filePath) && checkExtension (filePath);
    }
}
