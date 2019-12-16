package dev.ny.aoc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {

    private static List<Equation> equations;

    public static void main(String[] args) {
        equations = INPUT.lines()
                .map(Equation::new)
                .collect(Collectors.toList());

        // part 1
        final List<Item> excess = new ArrayList<>();
        final Item partA = solveEquation(new Item("1 FUEL"), excess);
        System.out.println(partA);
        System.out.println(excess);
        // part 2
        partB(partA.getQuantity());


    }

    private static void partB(long oreForOneRun) {
        final long expected = 1_000_000_000_000L;
        long fuelQ = (long) Math.ceil(expected / oreForOneRun);
        Item fuel = new Item("FUEL", fuelQ);
        while (true) {
            final List<Item> excess = new ArrayList<>();
            final Item partB = solveEquation(fuel, excess);
            System.out.println(partB);
            System.out.println(fuel.getQuantity());
//            System.out.println(excess);
            if (partB.getQuantity() > expected) {
                break;
            }
            long newNum = (long) (fuel.getQuantity() + Math.ceil((expected - partB.getQuantity()) / oreForOneRun));
            if (newNum <= fuel.getQuantity()) {
                newNum = fuel.getQuantity() + 1L;
            }
            fuel = new Item("FUEL", newNum);
        }
    }

    private static Item solveEquation(final Item output, final List<Item> excess) {
        final String in = "IN: " + output + " " + excess;

        if (output.getName().equals("ORE")) {
            return output;
        }
        excess.stream()
                .filter(e -> output.getName().equals(e.getName()))
                .filter(e -> e.getQuantity() > 0)
                .findFirst()
                .ifPresent(found -> {
                    final long existingQ = found.getQuantity();
                    final long requiredQ = output.getQuantity();
                    if (requiredQ > existingQ) {
                        output.setQuantity(requiredQ - existingQ);
                        found.setQuantity(0L);
                    } else {
                        output.setQuantity(0L);
                        found.setQuantity(existingQ - requiredQ);
                    }
                });
        final long quantity = output.getQuantity();
        final Equation eq = equations.stream()
                .filter(e -> output.getName().equals(e.getResult().getName()))
                .findFirst().get();
        final int scale = (int) Math.ceil(quantity / (double) eq.getResult().getQuantity());
        final int excessAmount = (int) (scale * eq.getResult().getQuantity() - quantity);
        final Item excessItem = new Item();
        excessItem.setName(output.getName());
        excessItem.setQuantity(excessAmount);
        excess.add(excessItem);
        excess.removeIf(e -> e.getQuantity() == 0);
        final List<Item> collect = eq.getInput().stream()
                .map(e -> {
                    final Item clone = e.clone();
                    clone.setQuantity(clone.getQuantity() * scale);
                    return solveEquation(clone, excess);
                })
                .collect(Collectors.toList());
        final Item ore = collect.stream()
                .filter(e -> e.getName().equals("ORE"))
                .reduce(new Item("0 ORE"), (ans, e) -> {
                    ans.setQuantity(ans.getQuantity() + e.getQuantity());
                    return ans;
                });
//        System.out.println(in);
//        System.out.println("OUT: " + ore + " " + excess);
        return ore;
    }

    private static final String INPUT = "171 ORE => 8 CNZTR\n" +
            "7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL\n" +
            "114 ORE => 4 BHXH\n" +
            "14 VRPVC => 6 BMBT\n" +
            "6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL\n" +
            "6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT\n" +
            "15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW\n" +
            "13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW\n" +
            "5 BMBT => 4 WPTQ\n" +
            "189 ORE => 9 KTJDG\n" +
            "1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP\n" +
            "12 VRPVC, 27 CNZTR => 2 XDBXC\n" +
            "15 KTJDG, 12 BHXH => 5 XCVML\n" +
            "3 BHXH, 2 VRPVC => 7 MZWV\n" +
            "121 ORE => 7 VRPVC\n" +
            "7 XCVML => 6 RJRHP\n" +
            "5 BHXH, 4 VRPVC => 5 LTCX";

    @Data
    private static class Equation {
        Item result;
        List<Item> input;

        Equation(final String rawInput) {
            final String[] both = rawInput.split(" => ");
            result = new Item(both[1]);
            input = Stream.of(both[0].split(", "))
                    .map(Item::new)
                    .collect(Collectors.toList());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Item {
        String name;
        long quantity;

        Item(final String rawInput) {
            final String[] tokens = rawInput.split(" ");
            name = tokens[1];
            quantity = Long.parseLong(tokens[0]);
        }

        @Override
        protected Item clone() {
            return new Item(this.name, this.quantity);
        }
    }
}
