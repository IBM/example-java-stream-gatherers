package com.ibm.developer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class CharactersGathererTests {

    @Test
    public void testCreateMediaPosts() {
        var charStream = Stream.of("this is a first string", "and now here is the second string", "and then the third",
                "four should be enough").gather(new CharactersGatherer());
        var chars = charStream.toArray();
        var expectedString = "this is a first string" + "and now here is the second string" + "and then the third"
                + "four should be enough";
        var charArray = expectedString.toCharArray();
        // they're all there--this is just a simple sanity check
        assertEquals(94, chars.length);
        // It's pretty easy to just loop through and check each one...
        for (int i = 0; i < charArray.length; i++) {
            assertEquals(charArray[i], chars[i]);
        }
    }

}
