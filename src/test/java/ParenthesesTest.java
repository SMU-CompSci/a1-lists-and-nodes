import com.student_word.Parentheses;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParenthesesTest {

    @Test
    void exampleBalanced_isTrue() {
        String input = "[()]{}{[()()]()}";
        assertTrue(Parentheses.isBalanced(input));
    }

    @Test
    void exampleUnbalanced_isFalse() {
        String input = "[(])";
        assertFalse(Parentheses.isBalanced(input));
    }
}
