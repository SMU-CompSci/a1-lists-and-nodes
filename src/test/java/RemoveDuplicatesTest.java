import com.student_word.RemoveDuplicates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveDuplicatesTest {
    private PrintStream originalOut;
    private InputStream originalIn;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        originalIn  = System.in;
        outContent  = new ByteArrayOutputStream();
        // Redirect System.out BEFORE invoking main so algs4.StdOut binds to it
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() throws IOException {
        System.setOut(originalOut);
        System.setIn(originalIn);
        outContent.close();
    }

    @Test
    void sampleInput_producesSampleOutput() {
        // Sample Input:
        // 1
        // 5
        // 1
        // 2
        // 2
        // 3
        // 4
        String input = String.join(System.lineSeparator(),
                "1", "5", "1", "2", "2", "3", "4") + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        RemoveDuplicates.main(new String[0]);

        // Normalize line endings for Windows/macOS/Linux
        String output = outContent.toString().replace("\r\n", "\n");
        assertEquals("1 2 3 4\n", output);
    }
}
