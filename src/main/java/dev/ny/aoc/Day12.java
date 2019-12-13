package dev.ny.aoc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {
    public static void main(String[] args) {
        List<Step> moons = Stream.of(
                new Point(5, 13, -3),
                new Point(18, -7, 13),
                new Point(16, 3, 4),
                new Point(0, 8, 8)
        )
                .map(Step::new)
                .collect(Collectors.toList());
        // part 1
        for (int i = 0; i < 1000; i++) {
            moons = nextStep(moons);
        }
        final int e1 = moons.stream()
                .mapToInt(s -> (Math.abs(s.getPosition().x) + Math.abs(s.getPosition().y) + Math.abs(s.getPosition().z)) *
                        (Math.abs(s.getVelocity().x) + Math.abs(s.getVelocity().y) + Math.abs(s.getVelocity().z))
                )
                .sum();

        System.out.println(e1);
        // part 2
        moons = Stream.of(
                new Point(0, 13, 0),
                new Point(0, -7, 0),
                new Point(0, 3, 0),
                new Point(0, 8, 0)
        )
                .map(Step::new)
                .collect(Collectors.toList());
        long x = count(moons);
        moons = Stream.of(
                new Point(0, 0, -3),
                new Point(0, 0, 13),
                new Point(0, 0, 4),
                new Point(0, 0, 8)
        )
                .map(Step::new)
                .collect(Collectors.toList());
        long y = count(moons);
        moons = Stream.of(
                new Point(5, 0, 0),
                new Point(18, 0, 0),
                new Point(16, 0, 0),
                new Point(0, 0, 0)
        )
                .map(Step::new)
                .collect(Collectors.toList());
        long z = count(moons);
        System.out.println(lcm(BigDecimal.valueOf(x), lcm(BigDecimal.valueOf(y), BigDecimal.valueOf(z))).toString());
    }

    private static BigDecimal lcm(final BigDecimal a, final BigDecimal b) {
        return a.multiply(b).divide(BigDecimal.valueOf(gcd(a, b)), RoundingMode.HALF_EVEN);
    }

    private static long gcd(final BigDecimal a, final BigDecimal b) {
        long max = Math.min(a.longValue(), b.longValue());
        for (long i = max; i > 1; i--) {
            if (a.longValue() % i == 0 && b.longValue() % i == 0) return i;
        }
        return 1;
    }

    private static long count(List<Step> moons) {
        final List<Step> init = moons;
        long counter = 0;
        do {
            moons = nextStep(moons);
            counter++;
        } while (!moons.equals(init));
        return counter;
    }

    private static List<Step> nextStep(List<Step> steps) {
        List<Step> nextSteps = new ArrayList<>(steps.size());
        for (int i = 0; i < steps.size(); i++) {
            final Step step = steps.get(i);
            final Point position = step.getPosition();
            final Point newVelocity = steps.stream()
                    .filter(s -> !s.equals(step))
                    .map(Step::getPosition)
                    .map(position::diff)
                    .reduce(step.getVelocity(), Point::add);
            final Point newPosition = position.add(newVelocity);
            final Step next = new Step();
            next.setPosition(newPosition);
            next.setVelocity(newVelocity);
            nextSteps.add(next);
        }
        return nextSteps;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Step {
        Point position;
        Point velocity;

        Step(Point position) {
            this.position = position;
            this.velocity = new Point(0, 0, 0);
        }
    }

    @Data
    private static class Point {
        final int x;
        final int y;
        final int z;

        Point add(Point add) {
            return new Point(this.x + add.x, this.y + add.y, this.z + add.z);
        }

        Point diff(Point add) {
            return new Point(u(this.x, add.x), u(this.y, add.y), u(this.z, add.z));
        }

        private int u(int a, int b) {
            if (a < b) {
                return 1;
            } else if (a > b) {
                return -1;
            }
            return 0;
        }

    }
}
