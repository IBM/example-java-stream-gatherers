package com.ibm.developer;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

record MediaPost() {

}

// This record will create a Gatherer implementation that will read from a
// stream
// of characters. It uses the MediaPost as a temmporary holding area. It fills
//
record MediaPostGatherer(Integer maxLength) implements Gatherer<Character, StringBuffer, String> {

    private static final Character SPACE = Character.valueOf(' ');

    @Override
    public Supplier<StringBuffer> initializer() {
        return () -> new StringBuffer();
    }

    @Override
    public Integrator<StringBuffer, Character, String> integrator() {
        return Integrator.of(
                (buffer, current, downstream) -> {
                    if (buffer.length() == 0 && current.equals(SPACE)) {
                        // don't add leading spaces.
                        return true;
                    }
                    // Before adding the Character to the buffer, check the size of the buffer
                    if (buffer.length() == maxLength) {
                        var tempBuffer = new StringBuffer();
                        // Now, take a look at the current c and make sure it's not space so we
                        // can just add it to the buffer.
                        if (!current.equals(SPACE)) {
                            buffer.append(current.charValue());
                            // roll back the buffer to the last space, taking the characters
                            // back out of the buffer and placing them on a temp
                            for (int i = buffer.length() - 1; i > 0; i--) {
                                var c = buffer.charAt(i);
                                if (c == SPACE.charValue()) {
                                    // Push the string downstream...
                                    downstream.push(buffer.substring(0, i + 1).trim());
                                    buffer.delete(0, buffer.length());
                                    buffer.append(tempBuffer.reverse());
                                    return true;
                                } else {
                                    tempBuffer.append(c);
                                }
                            }
                        } else {
                            // just add the character, push the string downstream and
                            // make sure to clear out the buffer;
                            downstream.push(buffer.toString());
                            buffer.delete(0, buffer.length());
                        }
                    }
                    buffer.append(current.charValue());
                    return true;
                });
    }

    @Override
    public BiConsumer<StringBuffer, Downstream<? super String>> finisher() {
        return (buffer, downstream) -> {
            if (!buffer.isEmpty()) {
                downstream.push(buffer.toString().trim());
            }
        };
    }

    

}
