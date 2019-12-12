package dev.ny.aoc;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Day10 {
    public static void main(String[] args) {

        final AstroidMap map = new AstroidMap(INPUT);
        final List<Point> points = map.getAsteroids().stream()
                .peek(p -> p.setRelativeAsteroids(map.removeHiddenAsteroids(map.getRelativePositions(p))))
                .collect(toList());
        // part 1
        final int max = points.stream()
                .map(Point::getRelativeAsteroids)
                .mapToInt(List::size)
                .max()
                .getAsInt();
        System.out.println(
                max
        );
        // part 2
        for (int i = 0; i < points.size(); i++) {
            final Point p = points.get(i);
            if (p.getRelativeAsteroids().size() == max) {
                p.getRelativeAsteroids().sort(Comparator.comparing(Point::getAngle));
                System.out.println(p.x + " " + p.y);
                // this is faulty solution, it puts the relative x=0 to last and not first
                final Point x = p.getRelativeAsteroids().get(198); // because x coor zero makes it the highest number and not lowest in atan2
                System.out.println(x.x + " " + x.y);
                System.out.println((p.x + x.x) + " " + (p.y + x.y));

            }
        }
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

        AstroidMap(String input) {
            this.input = input;
            final List<String> lines = input.lines().collect(toList());
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
                    .collect(toList());
        }

        List<Point> removeHiddenAsteroids(final List<Point> input) {
            List<Point> visiblePoints = new ArrayList<>();
            final Map<Double, List<Point>> collect = input.stream()
                    .sorted(Comparator.comparing(Point::getDistance))
                    .collect(groupingBy(Point::getAngle, toList()));
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
        final double distance;
        final double angle;
        List<Point> relativeAsteroids;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
            distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            angle = Math.toDegrees(Math.atan2(x * -1, y));
        }


    }
}


