import static org.junit.jupiter.api.Assertions.assertEquals;

import com.student_word.Josephus;
import edu.princeton.cs.algs4.StdOut;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

public class JosephusTest {

    /** Captures output produced via edu.princeton.cs.algs4.StdOut and System.out. */
    private static String captureStdOut(Runnable r) {
        // Save originals
        PrintStream originalSysOut = System.out;
        PrintWriter originalStdOutWriter = null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream capturePs = new PrintStream(baos, true);

        try {
            // 1) Redirect System.out
            System.setOut(capturePs);

            // 2) Replace StdOut's private static PrintWriter 'out' via reflection
            Field outField = StdOut.class.getDeclaredField("out");
            outField.setAccessible(true);
            originalStdOutWriter = (PrintWriter) outField.get(null);
            outField.set(null, new PrintWriter(capturePs, true));

            // 3) Run the code
            r.run();

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to reflectively swap StdOut writer", e);
        } finally {
            // Restore StdOut writer
            try {
                if (originalStdOutWriter != null) {
                    Field outField = StdOut.class.getDeclaredField("out");
                    outField.setAccessible(true);
                    outField.set(null, originalStdOutWriter);
                }
            } catch (ReflectiveOperationException ignored) {}

            // Restore System.out
            System.setOut(originalSysOut);

            // Ensure buffer flushed
            capturePs.flush();
        }

        return baos.toString().trim();
    }

    @Test
    void printJosephusOrder_example_N7_M2() {
        String out = captureStdOut(() -> Josephus.printJosephusOrder(7, 2));
        assertEquals("1 3 5 0 4 2 6", out);
    }

    @Test
    void main_example_N7_M2() {
        String out = captureStdOut(() -> Josephus.main(new String[]{"7", "2"}));
        assertEquals("1 3 5 0 4 2 6", out);
    }
}
