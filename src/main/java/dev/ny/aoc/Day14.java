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
        long fuelQ = (long) Math.ceil(expected / (double)oreForOneRun);
        Item fuel = new Item("FUEL", fuelQ);
        while (true) {
            final List<Item> excess = new ArrayList<>();
            final Item partB = solveEquation(fuel, excess);
            System.out.println(partB);
            System.out.println(fuel.getQuantity());
            System.out.println(excess);
            if (partB.getQuantity() > expected) {
                break;
            }
            long newNum = (long) (fuel.getQuantity() + Math.ceil((expected - partB.getQuantity()) / (double)oreForOneRun));
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
        final long scale = (long) Math.ceil(quantity / (double) eq.getResult().getQuantity());
        final long excessAmount = scale * eq.getResult().getQuantity() - quantity;
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

    private static final String INPUT = "2 WZMS, 3 NPNFD => 5 SLRGD\n" +
            "4 QTFCJ, 1 RFZF => 1 QFQPN\n" +
            "2 LCDPV => 6 DGPND\n" +
            "1 MVSHM, 3 XSDR, 1 RSJD => 6 GNKB\n" +
            "6 XJRML, 1 LCDPV => 7 HTSJ\n" +
            "3 LQBX => 3 GKNTG\n" +
            "2 NZMLP, 5 FTNZQ => 2 QSLTQ\n" +
            "8 WZMS, 4 XSDR, 2 NPNFD => 9 CJVT\n" +
            "16 HFHB, 1 TRVQG => 8 QTBQ\n" +
            "177 ORE => 7 DNWGS\n" +
            "10 ZJFM, 4 MVSHM => 8 LCDPV\n" +
            "1 LTVKM => 5 ZJFM\n" +
            "5 QFJS => 6 LTVKM\n" +
            "4 CZHM, 12 CJVT => 9 PGMS\n" +
            "104 ORE => 8 QCGM\n" +
            "1 JWLZ, 5 QTFCJ => 4 DHNL\n" +
            "20 VKRBJ => 3 FQCKM\n" +
            "1 FTNZQ, 1 QSLTQ => 4 HFHB\n" +
            "1 JLPVD => 2 JGJFQ\n" +
            "12 PTDL => 1 LVPK\n" +
            "31 JGJFQ, 5 PGMS, 38 PTDL, 1 PGCZ, 3 LVPK, 47 JGHWZ, 21 LVPJ, 27 LTVKM, 5 ZDQD, 5 LCDPV => 1 FUEL\n" +
            "6 WFJT, 2 VKRBJ => 8 NZMLP\n" +
            "21 HNJW, 3 NXTL, 8 WZMS, 5 SLRGD, 2 VZJHN, 6 QFQPN, 5 DHNL, 19 RNXQ => 2 PGCZ\n" +
            "1 QTBQ, 3 MVSHM => 1 XSDR\n" +
            "25 ZKZNB => 9 VZJHN\n" +
            "4 WHLT => 9 PHFKW\n" +
            "29 QPVNV => 9 JGHWZ\n" +
            "13 ZJFM => 2 RNXQ\n" +
            "1 DGPND, 12 PHFKW => 9 BXGXT\n" +
            "25 ZJFM => 6 WHLT\n" +
            "3 QPVNV => 9 BTLH\n" +
            "1 KXQG => 8 TRVQG\n" +
            "2 JWLZ => 8 JLPVD\n" +
            "2 GKNTG => 6 NXTL\n" +
            "28 VKRBJ => 2 DXWSH\n" +
            "126 ORE => 7 VKRBJ\n" +
            "11 WHLT => 8 QTFCJ\n" +
            "1 NZMLP, 1 DNWGS, 8 VKRBJ => 5 XJRML\n" +
            "16 XJRML => 6 SKHJL\n" +
            "3 QTFCJ, 6 ZTHWQ, 15 GKNTG, 1 NXRZL, 1 DGBRZ, 1 SKHJL, 1 VZJHN => 7 LVPJ\n" +
            "1 HFHB, 16 QTBQ, 7 XJRML => 3 NPNFD\n" +
            "2 TRVQG => 4 JWLZ\n" +
            "8 GKNTG, 1 NSVG, 23 RNXQ => 9 NXRZL\n" +
            "3 QTFCJ => 6 CZHM\n" +
            "2 NPNFD => 8 JQSTD\n" +
            "1 DXWSH, 1 DGPND => 4 DGBRZ\n" +
            "3 DXWSH, 24 QFJS, 8 FTNZQ => 8 KXQG\n" +
            "6 FXJQX, 14 ZKZNB, 3 QTFCJ => 2 ZTHWQ\n" +
            "31 NSVG, 1 NXRZL, 3 QPVNV, 2 RNXQ, 17 NXTL, 6 BTLH, 1 HNJW, 2 HTSJ => 1 ZDQD\n" +
            "5 RNXQ, 23 BXGXT, 5 JQSTD => 7 QPVNV\n" +
            "8 NPNFD => 7 WZMS\n" +
            "6 KXQG => 7 ZDZM\n" +
            "129 ORE => 9 WFJT\n" +
            "9 NZMLP, 5 FQCKM, 8 QFJS => 1 LQBX\n" +
            "170 ORE => 9 GDBNV\n" +
            "5 RSJD, 3 CZHM, 1 GNKB => 6 HNJW\n" +
            "14 HTSJ => 7 FXJQX\n" +
            "11 NPNFD, 1 LCDPV, 2 FXJQX => 6 RSJD\n" +
            "9 DGBRZ => 6 ZKZNB\n" +
            "7 GDBNV, 1 QCGM => 8 QFJS\n" +
            "2 QFQPN, 5 JWLZ => 4 NSVG\n" +
            "8 QFJS, 1 ZDZM, 4 QSLTQ => 7 MVSHM\n" +
            "1 LTVKM => 8 RFZF\n" +
            "4 DNWGS => 3 FTNZQ\n" +
            "6 VZJHN => 9 PTDL";

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
