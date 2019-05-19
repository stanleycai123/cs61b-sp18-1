import org.junit.Test;

import static org.junit.Assert.assertEquals;

// h - 104
// e - 101
// l - 108
// o - 111

public class RabinKarpAlgorithmTests {
    @Test
    public void basic() {
        String input = "hello";
        String pattern = "ell";
        assertEquals(1, RabinKarpAlgorithm.bruteForce(input, pattern));
        assertEquals(1, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }
}