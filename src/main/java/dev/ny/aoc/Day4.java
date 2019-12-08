package dev.ny.aoc;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 {
    public static void main(String[] args) {
        final int start = 168630;
        final int end = 718098;
        final long count = IntStream.range(start, end)
                .mapToObj(Integer::toString)
//                .filter(part1())
                .filter(part2())
                .filter(checkNoDecrease())
                .count();
        System.out.println(count);
    }

    private static Predicate<String> part2() {
        return input -> {
            boolean flag = false;
            int[] pos = new int[10];
            for (char c : input.toCharArray()) {
                pos[c - 48] += 1;
            }
            for (int po : pos) {
                if (po == 2) {
                    flag = true;
                    break;
                }
            }
            return flag;
        };
    }

    private static Predicate<String> part1() {
        return input -> {
            boolean flag = false;
            for (int i = 0; i < input.length() - 1; i++) {
                if (input.charAt(i) == input.charAt(i + 1)) {
                    flag = true;
                    break;
                }
            }
            return flag;
        };
    }

    private static Predicate<String> checkNoDecrease() {
        return input -> {
            final String sortedInput = input.chars().mapToObj(c -> (char) c)
                    .map(String::valueOf)
                    .sorted()
                    .collect(Collectors.joining());
            return sortedInput.equals(input);
        };
    }
}
