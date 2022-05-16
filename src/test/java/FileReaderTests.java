import Entities.Grades;
import Entities.UserLogs;
import FileOps.FileDataReader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)

public class FileReaderTests {
    @Test
    public void testFileReadGrades() throws Exception {
        FileDataReader fileDataReader = new FileDataReader ();
        List data = fileDataReader.readAllDataFromFile ("D:\\Java Projects\\PTS_Project\\Course A_StudentsResults_Year 1.xlsx");
        Assert.assertTrue (data.get (0) instanceof Grades);
    }

    @Test
    public void testFileReadLogs() throws Exception {
        FileDataReader fileDataReader = new FileDataReader ();
        List data = fileDataReader.readAllDataFromFile ("D:\\Java Projects\\PTS_Project\\src\\main\\java\\Logs_Course A_StudentsActivities.xlsx");
        Assert.assertTrue (data.get (0) instanceof UserLogs);

    }

    @Test
    public void testFileReadGetRow() throws Exception {
        Grades grade = new Grades ();
        grade.setGrade (3.5);
        grade.setId ("7922.0");
        FileDataReader fileDataReader = new FileDataReader ();
        List<Grades> data = fileDataReader.readAllDataFromFile ("D:\\Java Projects\\PTS_Project\\Course A_StudentsResults_Year 1.xlsx");
        Assert.assertEquals (data.get (1).getGrade (), grade.getGrade (), 0.00);
    }

}
