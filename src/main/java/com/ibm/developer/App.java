package com.ibm.developer;

import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        List<List<Character>> windows = Stream.of('m', 'o', 'o', 's', 'h', 'o', 'o').gather(Gatherers.windowFixed(3))
                .toList();
        windows.forEach(System.out::println);

    }
}
