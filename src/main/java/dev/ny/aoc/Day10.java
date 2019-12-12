package dev.ny.aoc;

import lombok.Data;
import lombok.Getter;
import org.w3c.dom.ls.LSOutput;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 {
    public static void main(String[] args) {

        final AstroidMap map = new AstroidMap(INPUT);
        // part 1
        map.getAsteroids().stream()
                .map(map::getRelativePositions)
                .map(map::removeHiddenAsteroids)
                .map(List::size)
                .mapToInt(Integer::valueOf)
                .max().ifPresent(System.out::println);
        // part 2
//        map.getAsteroids().stream()
//                .map(map::getRelativePositions)
//                .forEach(map::removeHiddenAndPrint);
    }

    private static final String INPUT =
            "..#..###....#####....###........#\n" +
                    ".##.##...#.#.......#......##....#\n" +
                    "#..#..##.#..###...##....#......##\n" +
                    "..####...#..##...####.#.......#.#\n" +
                    "...#.#.....##...#.####.#.###.#..#\n" +
                    "#..#..##.#.#.####.#.###.#.##.....\n" +
                    "#.##...##.....##.#......#.....##.\n" +
                    ".#..##.##.#..#....#...#...#...##.\n" +
                    ".#..#.....###.#..##.###.##.......\n" +
                    ".##...#..#####.#.#......####.....\n" +
                    "..##.#.#.#.###..#...#.#..##.#....\n" +
                    ".....#....#....##.####....#......\n" +
                    ".#..##.#.........#..#......###..#\n" +
                    "#.##....#.#..#.#....#.###...#....\n" +
                    ".##...##..#.#.#...###..#.#.#..###\n" +
                    ".#..##..##...##...#.#.#...#..#.#.\n" +
                    ".#..#..##.##...###.##.#......#...\n" +
                    "...#.....###.....#....#..#....#..\n" +
                    ".#...###..#......#.##.#...#.####.\n" +
                    "....#.##...##.#...#........#.#...\n" +
                    "..#.##....#..#.......##.##.....#.\n" +
                    ".#.#....###.#.#.#.#.#............\n" +
                    "#....####.##....#..###.##.#.#..#.\n" +
                    "......##....#.#.#...#...#..#.....\n" +
                    "...#.#..####.##.#.........###..##\n" +
                    ".......#....#.##.......#.#.###...\n" +
                    "...#..#.#.........#...###......#.\n" +
                    ".#.##.#.#.#.#........#.#.##..#...\n" +
                    ".......#.##.#...........#..#.#...\n" +
                    ".####....##..#..##.#.##.##..##...\n" +
                    ".#.#..###.#..#...#....#.###.#..#.\n" +
                    "............#...#...#.......#.#..\n" +
                    ".........###.#.....#..##..#.##...";

    @Getter
    private static class AstroidMap {
        private final String input;
        private final List<Point> asteroids = new ArrayList<>();
        private static final Point ZERO = new Point(0, 0);

        public AstroidMap(String input) {
            this.input = input;
            final List<String> lines = input.lines().collect(Collectors.toList());
            for (int i = 0; i < lines.size(); i++) {
                final String line = lines.get(i);
                final char[] chars = line.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    if (chars[j] == '#') {
                        asteroids.add(new Point(j, i));
                    }
                }
            }
        }

        public List<Point> getRelativePositions(final Point asteroid) {
            return asteroids.stream()
                    .map(a -> new Point(a.x - asteroid.x, a.y - asteroid.y))
                    .filter(a -> !a.equals(ZERO))
                    .sorted((a, b) -> Math.abs(a.x) + Math.abs(a.y) - Math.abs(b.x) - Math.abs(b.y))
                    .collect(Collectors.toList());
        }

        public int removeHiddenAsteroidsAndCount(final List<Point> input) {
            final Set<String> collect = input.stream()
                    .map(p -> p.getQuadrant() + String.valueOf(p.slope()))
                    .collect(Collectors.toSet());
            return collect.size();
        }

        public List<Point> removeHiddenAsteroids(final List<Point> input) {
            List<Point> visiblePoints = new ArrayList<>();
            final Map<Double, List<Point>> collect = input.stream()
                    .sorted(Comparator.comparing(Point::getDistance))
                    .collect(Collectors.groupingBy(Point::getAngle, Collectors.toList()));
            collect.keySet().stream()
                    .sorted()
                    .forEach(v -> visiblePoints.add(collect.get(v).get(0)));
            return visiblePoints;
        }

    }

    @Data
    private static class Point {
        final int x;
        final int y;
        final BigDecimal distance;
        final double angle;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            final BigDecimal bx = BigDecimal.valueOf(x);
            final BigDecimal by = BigDecimal.valueOf(y);

            distance = bx.pow(2).add(by.pow(2));

            angle = Math.toDegrees(Math.atan(y * -1 / (double) x));
        }

        public int getQuadrant() {
            if (x > 0 && y > 0)
                return 2;
            if (x < 0 && y > 0)
                return 4;
            if (x < 0 && y < 0)
                return 6;
            if (x > 0 && y < 0)
                return 8;
            if (x > 0)
                return 3;
            if (x < 0)
                return 7;
            if (y > 0)
                return 1;
            if (y < 0)
                return 5;
            return -1;
        }

        float slope() {
            return y / (float) x;
        }


        Point scale(final int scale) {
            return new Point(scale * x, scale * y);
        }
    }
}


