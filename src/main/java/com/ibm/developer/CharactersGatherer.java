package com.ibm.developer;

import java.nio.CharBuffer;
import java.util.stream.Gatherer;

public class CharactersGatherer implements Gatherer<String, CharBuffer, Character> {

    @Override
    public Integrator<CharBuffer, String, Character> integrator() {
        return Integrator.of(
                (buffer, str, downstream) -> {
                    str.chars().forEachOrdered(c -> {
                        if ((char)c != '\n') {
                            downstream.push(Character.valueOf((char) c));
                        }
                    });
                    // There is no point at which we would stop processing the stream.
                    return true;
                });
    }

}
