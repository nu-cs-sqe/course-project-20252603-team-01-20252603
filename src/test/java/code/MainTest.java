package code;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the application entry point.
 */
public final class MainTest {

    @Test
    public void mainWithNoInputThrowsNoSuchElementExceptionAfterStartingGame() {
        InputStream originalIn = System.in;

        try {
            System.setIn(new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)));

            assertThrows(NoSuchElementException.class, () -> Main.main(new String[0]));
        } finally {
            System.setIn(originalIn);
        }
    }
}
