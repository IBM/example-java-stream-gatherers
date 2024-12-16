package com.ibm.developer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Gatherers;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class BuiltinGathererTests {

    @Test
    public void testFold() {
        var actual = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .gather(
                        Gatherers.fold(
                                () -> Integer.valueOf(1),
                                (result, element) -> {
                                    return result * element;
                                }))
                // remember, output of a gatherer is a stream, so if we want a
                // single value, we just call findFirst()
                .findFirst()
                .get();

        // This would end up being 9 factorial
        assertEquals(Integer.valueOf(362880), actual);
    }

}
