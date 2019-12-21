package dev.ny.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 {
    private static final String INPUT = "59738571488265718089358904960114455280973585922664604231570733151978336391124265667937788506879073944958411270241510791284757734034790319100185375919394328222644897570527214451044757312242600574353568346245764353769293536616467729923693209336874623429206418395498129094105619169880166958902855461622600841062466017030859476352821921910265996487329020467621714808665711053916709619048510429655689461607438170767108694118419011350540476627272614676919542728299869247813713586665464823624393342098676116916475052995741277706794475619032833146441996338192744444491539626122725710939892200153464936225009531836069741189390642278774113797883240104687033645";

    public static void main(String[] args) {
        List<Integer> input = INPUT.chars().mapToObj(e -> e - 48).collect(Collectors.toList());
//        System.out.println(calcA(input, 1, 0));
        System.out.println(calcB(input, Integer.parseInt(INPUT.substring(0, 7))));

    }

    private static String calcB(List<Integer> input, final int offset) {
        List<Integer> replicator = new ArrayList<>(input.size() * 10000);
        for (int i = 0; i < 10000; i++) {
            replicator.addAll(input);
        }
        input = IntStream.range(offset, replicator.size())
                .mapToObj(replicator::get)
                .collect(Collectors.toList());
        for (int i = 0; i < 100; i++) {
            int result = 0;
            for (int j = input.size() - 1; j >= 0; j--) {
                result += input.get(j);
                result = result % 10;
                input.set(j, result);
            }
//            System.out.println(input);

        }
        return input.stream().limit(8).map(String::valueOf).collect(Collectors.joining());
    }

    private static String calcA(List<Integer> input, final int repeat, final int offset) {
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
