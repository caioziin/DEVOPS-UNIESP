package com.uniesp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SomaTest {

    int soma(int a, int b) {
        return a + b;
    }

    @Test
    void testSoma() {
        assertEquals(5, soma(2, 3));
    }
}
