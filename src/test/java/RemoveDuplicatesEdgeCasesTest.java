import com.student_word.RemoveDuplicates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Additional edge-case tests for the RemoveDuplicates assignment (Q1).
 *
 * Each test simulates StdIn in the required format, invokes
 * RemoveDuplicates.main, and checks StdOut for the exact expected result.
 * All text is US-ASCII and output is decoded as US-ASCII to avoid encoding issues.
 */
public class RemoveDuplicatesEdgeCasesTest {
    private PrintStream originalOut;
    private InputStream originalIn;
    private ByteArrayOutputStream outContent;

    /**
     * Rewire StdIn and StdOut to the current System.in and System.out.
     *
     * <p>The algs4 StdIn and StdOut classes keep static references to
     * input and output streams that are initialized when the classes
     * are first loaded. When tests replace System.in and System.out,
     * StdIn and StdOut must be re-bound via reflection to avoid
     * reading from stale streams or writing to a closed PrintWriter.
     */
    private static void rewireStdIO() {
        try {
            // Resync StdIn to current System.in
            Method resync = StdIn.class.getDeclaredMethod("resync");
            resync.setAccessible(true);
            resync.invoke(null);

            // Reset StdOut's internal PrintWriter to current System.out
            Field outField = StdOut.class.getDeclaredField("out");
            outField.setAccessible(true);
            // Use US_ASCII to match the test's output encoding
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.US_ASCII), true);
            outField.set(null, pw);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to rewire StdIn/StdOut for test isolation", e);
        }
    }

    /**
     * Sets System.in to the given ASCII text and resyncs StdIn to read from it.
     *
     * <p>This helper encapsulates resetting the standard input stream
     * and ensuring StdIn uses the new stream. It should be called at
     * the start of each test after constructing the input string.
     *
     * @param text the full input string, including newline separators
     */
    private static void setAsciiInput(String text) {
        System.setIn(new ByteArrayInputStream(text.getBytes(StandardCharsets.US_ASCII)));
        // Rewire both StdIn and StdOut now that System.in has changed
        rewireStdIO();
    }

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        originalIn  = System.in;
        outContent  = new ByteArrayOutputStream();
        // Redirect System.out before invoking main so StdOut binds to this buffer.
        System.setOut(new PrintStream(outContent, true, StandardCharsets.US_ASCII));
        // Rewire StdIn and StdOut to the newly set streams.
        rewireStdIO();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Restore original System.out and System.in first
        System.setOut(originalOut);
        System.setIn(originalIn);
        // Rewire StdIn and StdOut to their original streams so they no longer
        // reference the ByteArrayOutputStream which will be closed.
        rewireStdIO();
        outContent.close();
    }

    /** Normalize line endings to '\n' for cross-platform assertions. */
    private static String normalize(String s) {
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }

    /** Capture normalized US-ASCII text from System.out. */
    private String captured() {
        return normalize(new String(outContent.toByteArray(), StandardCharsets.US_ASCII));
    }

    @Test
    @DisplayName("Empty list (n=0) should print a blank line")
    void emptyList_printsBlankLine() {
        String input = String.join(System.lineSeparator(),
                "1",   // t
                "0"    // n
        ) + System.lineSeparator();
        setAsciiInput(input);

        RemoveDuplicates.main(new String[0]);

        String actual = captured();
        String expected = "\n";
        assertEquals(expected, actual,
                "Expected a single blank line for n=0, but got: " + printable(actual)
                        + "\nHint: When n==0, still print a newline (no spaces, no extra text).");
    }

    @Test
    @DisplayName("Single element list should echo the value")
    void singleElement_echoesValue() {
        String input = String.join(System.lineSeparator(),
                "1",
                "1",
                "42"
        ) + System.lineSeparator();
        setAsciiInput(input);

        RemoveDuplicates.main(new String[0]);

        String actual = captured();
        String expected = "42\n";
        assertEquals(expected, actual,
                "Expected exactly: 42 (with trailing newline), but got: " + printable(actual)
                        + "\nHint: Preserve order and formatting: values separated by a single space, "
                        + "and end the line with a newline.");
    }

    @Test
    @DisplayName("All identical values collapse to one unique element")
    void allIdentical_collapseToOne() {
        String input = String.join(System.lineSeparator(),
                "1",
                "6",
                "7","7","7","7","7","7"
        ) + System.lineSeparator();
        setAsciiInput(input);

        RemoveDuplicates.main(new String[0]);

        String actual = captured();
        String expected = "7\n";
        assertEquals(expected, actual,
                "Expected duplicates of the same value to collapse to one value, but got: " + printable(actual)
                        + "\nHint: For a sorted list, compare current node value to next node value and skip equals.");
    }

    @Test
    @DisplayName("Already unique ascending list remains unchanged")
    void alreadyUnique_unchanged() {
        String input = String.join(System.lineSeparator(),
                "1",
                "5",
                "10","20","30","40","50"
        ) + System.lineSeparator();
        setAsciiInput(input);

        RemoveDuplicates.main(new String[0]);

        String actual = captured();
        String expected = "10 20 30 40 50\n";
        assertEquals(expected, actual,
                "Expected an already-unique list to remain unchanged, but got: " + printable(actual)
                        + "\nHint: Do not remove non-duplicate items or alter order; only skip equal neighbors.");
    }

    @Test
    @DisplayName("Duplicates at the head are removed correctly")
    void duplicatesAtHead_removed() {
        String input = String.join(System.lineSeparator(),
                "1",
                "6",
                "1","1","1","2","3","4"
        ) + System.lineSeparator();
        setAsciiInput(input);

        RemoveDuplicates.main(new String[0]);

        String actual = captured();
        String expected = "1 2 3 4\n";
        assertEquals(expected, actual,
                "Expected head duplicates to be collapsed, but got: " + printable(actual)
                        + "\nHint: Be careful when the first few nodes are equal; advance the head or link around duplicates.");
    }

    @Test
    @DisplayName("Duplicates in the middle are removed correctly")
    void duplicatesInMiddle_removed() {
        String input = String.join(System.lineSeparator(),
                "1",
                "7",
                "1","2","2","2","3","4","5"
        ) + System.lineSeparator();
        setAsciiInput(input);

        RemoveDuplicates.main(new String[0]);

        String actual = captured();
        String expected = "1 2 3 4 5\n";
        assertEquals(expected, actual,
                "Expected middle duplicates to be collapsed to a single '2', but got: " + printable(actual)
                        + "\nHint: When current.value == next.value, set current.next = next.next (skip the duplicate).");
    }

    @Test
    @DisplayName("Duplicates at the tail are removed correctly")
    void duplicatesAtTail_removed() {
        String input = String.join(System.lineSeparator(),
                "1",
                "6",
                "1","2","3","4","4","4"
        ) + System.lineSeparator();
        setAsciiInput(input);

        RemoveDuplicates.main(new String[0]);

        String actual = captured();
        String expected = "1 2 3 4\n";
        assertEquals(expected, actual,
                "Expected tail duplicates to be collapsed, but got: " + printable(actual)
                        + "\nHint: Do not stop early; continue to the end so that trailing duplicates are removed.");
    }

    @Test
    @DisplayName("Handles negative numbers and preserves order")
    void negativeValues_handled() {
        String input = String.join(System.lineSeparator(),
                "1",
                "7",
                "-5","-5","-3","-3","-1","0","0"
        ) + System.lineSeparator();
        setAsciiInput(input);

        RemoveDuplicates.main(new String[0]);

        String actual = captured();
        String expected = "-5 -3 -1 0\n";
        assertEquals(expected, actual,
                "Expected negatives to be kept in ascending order without duplicates, but got: " + printable(actual)
                        + "\nHint: The input is already sorted; only remove equal neighbors, do not re-sort.");
    }

    @Test
    @DisplayName("Handles mixed negative and positive duplicates")
    void mixedNegativePositive_handled() {
        String input = String.join(System.lineSeparator(),
                "1",
                "9",
                "-3","-2","-2","-1","0","0","1","1","2"
        ) + System.lineSeparator();
        setAsciiInput(input);

        RemoveDuplicates.main(new String[0]);

        String actual = captured();
        String expected = "-3 -2 -1 0 1 2\n";
        assertEquals(expected, actual,
                "Expected correct merging across sign changes without duplicates, but got: " + printable(actual)
                        + "\nHint: Only compare adjacent nodes; if equal, skip the next node and keep going.");
    }

    @Test
    @DisplayName("Handles large runs of duplicates (n=1000)")
    void largeRunOfDuplicates_reducesToSingle() {
        StringBuilder builder = new StringBuilder();
        builder.append("1").append(System.lineSeparator());       // t
        builder.append("1000").append(System.lineSeparator());    // n
        for (int i = 0; i < 1000; i++) {
            builder.append("5").append(System.lineSeparator());
        }
        setAsciiInput(builder.toString());

        RemoveDuplicates.main(new String[0]);

        String actual = captured();
        String expected = "5\n";
        assertEquals(expected, actual,
                "Expected a long run of identical values to reduce to a single value, but got: " + printable(actual)
                        + "\nHint: Do not use nested loops over the list; a single pass skipping equal neighbors is enough.");
    }

    @Test
    @DisplayName("Multiple test cases are processed independently")
    void multipleTestCases_processedIndependently() {
        String input = String.join(System.lineSeparator(),
                "3",                 // t
                "0",                 // case 1: n=0
                "5","1","2","2","3","4",   // case 2
                "4","9","9","9","9"        // case 3
        ) + System.lineSeparator();
        setAsciiInput(input);

        RemoveDuplicates.main(new String[0]);

        String actual = captured();
        String expected = String.join("\n",
                "",                  // blank line for case 1
                "1 2 3 4",
                "9"
        ) + "\n";

        assertEquals(expected, actual,
                "Expected each test case output on its own line in order, but got: " + printable(actual)
                        + "\nHint: Read t, then loop t times building a fresh list for each case. "
                        + "Do not carry state across cases.");
    }

    /** Make control characters visible in failure messages. */
    private static String printable(String s) {
        // Replace newlines and carriage returns with visible tokens for debugging.
        return s.replace("\r", "\\r").replace("\n", "\\n");
    }
}
