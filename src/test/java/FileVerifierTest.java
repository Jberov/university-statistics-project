import FileOps.FileVerifier;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import java.io.FileNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class FileVerifierTest {
    @Mock
    FileVerifier fileVerifier;

    @Test
    public void testInvalidFile() throws Exception {
        Assert.assertFalse (fileVerifier.verifyFile ("filePath"));
    }

    @Test(expected = FileNotFoundException.class)
    public void testNonExistentFile() throws Exception {
        PowerMockito.when (fileVerifier, "checkFilePath", "filepath").thenThrow (FileNotFoundException.class);
        FileNotFoundException expected = Assert.assertThrows (FileNotFoundException.class, () -> {
            fileVerifier.verifyFile ("file.xlsx");
        });
        Assert.assertEquals ("No such file", expected.getMessage ());
    }
}
