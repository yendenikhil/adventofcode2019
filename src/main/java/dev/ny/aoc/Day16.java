package dev.ny.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 {
    private static final String INPUT = "03036732577212944063491565474664";

    public static void main(String[] args) {
        List<Integer> input = INPUT.chars().mapToObj(e -> e - 48).collect(Collectors.toList());
        System.out.println(calc(input, 1, 0));
//        System.out.println(calc(input, 10000, Integer.parseInt(INPUT.substring(0,8))));

    }

    private static String calc(List<Integer> input, final int repeat, final int offset) {
        List<Integer> replicator = new ArrayList<>(input.size() * repeat);
        for (int i = 0; i < repeat; i++) {
            replicator.addAll(input);
        }
        input = replicator;
        for (int k = 0; k < 100; k++) {
            List<Integer> temp = new ArrayList<>(input.size());
            for (int i = 1; i <= input.size(); i++) {
                final List<Integer> pattern = IntStream.generate(new PatternGenerator(i))
                        .boxed()
                        .limit(input.size())
                        .collect(Collectors.toList());
                int result = 0;
                for (int j = 0; j < input.size(); j++) {
                    result += (pattern.get(j) * input.get(j));
                }
                final String s = String.valueOf(result);
                temp.add(Integer.parseInt(s.substring(s.length() - 1)));
            }
            System.out.println(input.stream().map(String::valueOf).collect(Collectors.joining()));
            input = temp;

        }
        final String resultRaw = input.stream().map(String::valueOf).collect(Collectors.joining());
        System.out.println(resultRaw);
        return resultRaw.substring(offset, offset + 8);
    }

    private static class PatternGenerator implements IntSupplier {
        private static final int[] PHASE = {0, 1, 0, -1};
        int counter = 0;
        final int mult;
        int multCounter = 1;

        PatternGenerator(int mult) {
            this.mult = mult;
        }

        @Override
        public int getAsInt() {
            if (multCounter++ % mult == 0) {
                counter++;
            }
            return PHASE[counter % 4];
        }
    }
}
