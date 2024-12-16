package com.ibm.developer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MediaPostGathererTests {

    @ParameterizedTest
    // Add more tests to test out even more scenarios...
    @CsvSource({
        "basic.txt,output-80.txt,80"
    })
    public void testCreateMediaPosts(String inputFile, String expectedFile, Integer maxLength) {
        // Read from a file that has a bunch of text in it, and convert the content into
        // individual posts that are limited to 80 characters, but each one has full
        // words...
        try (
                InputStream isSrc = this.getClass().getClassLoader().getResourceAsStream("examples/%s".formatted(inputFile));
                InputStream isExpected = this.getClass().getClassLoader()
                        .getResourceAsStream("examples/%s".formatted(expectedFile));) {
            try (
                    InputStreamReader srcReader = new InputStreamReader(isSrc);
                    BufferedReader srcBuffer = new BufferedReader(srcReader);
                    InputStreamReader expReader = new InputStreamReader(isExpected);
                    BufferedReader expBuffer = new BufferedReader(expReader);) {
                List<String> posts = srcBuffer.lines()
                        .gather(new CharactersGatherer().andThen(
                            new MediaPostGatherer(maxLength.intValue())
                            )
                        ).toList();
                List<String> expected = expBuffer.lines().toList();
                assertEquals(expected.size(), posts.size());
                for (int i = 0; i < expected.size(); i++) {
                    assertEquals(expected.get(i), posts.get(i));
                }
            }
        } catch (Exception ex) {
            fail("Error while loading resources for tests:", ex);
        }
    }

}
