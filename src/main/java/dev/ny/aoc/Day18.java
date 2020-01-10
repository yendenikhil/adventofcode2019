package dev.ny.aoc;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class Day18 {
    public static void main(String[] args) {
        final Navigator nav = new Navigator(INPUT);
        nav.solveByBruteForce();
    }

    private static final String INPUT = "#################\n" +
            "#i.G..c...e..H.p#\n" +
            "########.########\n" +
            "#j.A..b...f..D.o#\n" +
            "########@########\n" +
            "#k.E..a...g..B.n#\n" +
            "########.########\n" +
            "#l.F..d...h..C.m#\n" +
            "#################";

}

@Data
class Point {
    final int x;
    final int y;
    @EqualsAndHashCode.Exclude
    String label;

    private Point next(final int dx, final int dy) {
        return new Point(this.x + dx, this.y + dy);
    }

    List<Point> neighbours() {
        List<Point> list = new ArrayList<>(4);
        list.add(this.next(1, 0));
        list.add(this.next(-1, 0));
        list.add(this.next(0, 1));
        list.add(this.next(0, -1));
        return list;
    }


}

@Data
class Edge {
    final Point head;
    final Point tail;
    int distance;
}

class Navigator {
    private final List<Point> walls = new ArrayList<>();
    private final List<Point> coords = new ArrayList<>();

    Navigator(final String input) {
        final String[] lines = input.split("\n");
        for (int i = 0; i < lines.length; i++) {
            final char[] chars = lines[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                final String s = String.valueOf(chars[j]);
                if (s.equals("#")) {
                    walls.add(new Point(j, i));
                } else if (s.matches("^[a-zA-Z]$")) {
                    final Point p = new Point(j, i);
                    p.setLabel(s);
                    coords.add(p);
                } else if (s.equals("@")) {
                    final Point p = new Point(j, i);
                    p.setLabel("@");
                    coords.add(0, p);
                }
            }
        }
    }

    // starting with brute force
    void solveByBruteForce() {
        // Get first possible list of edges as starting condition for the building stack / queue
        final Queue<List<Edge>> queue = getNextPossible(new ArrayList<>());
        int minDistance = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            final List<Edge> partPaths = queue.remove();
            // this condition checks whether we have all the paths to reach to all coords.
            if (partPaths.size() == coords.size() - 1) {
                final String finalPath = partPaths.stream().map(e -> e.getHead().getLabel() + " -> " + e.getTail().getLabel()).collect(Collectors.joining(", "));
                System.out.println(finalPath);
                final int distance = partPaths.stream().mapToInt(Edge::getDistance).sum();
                System.out.println(distance);
                if (distance < minDistance) {
                    minDistance = distance;
                }

            } else {
                getNextPossible(partPaths).stream().filter(l -> !queue.contains(l)).forEach(queue::add);
            }
        }
        System.out.println("Minimum distance: " + minDistance);
    }

    Queue<List<Edge>> getNextPossible(final List<Edge> partPaths) {
        Queue<List<Edge>> queue = new ArrayDeque<>();
        final List<Point> visited = partPaths.stream().map(Edge::getHead).collect(Collectors.toList());
        Point start = coords.get(0);
        if (!partPaths.isEmpty()) {
            start = partPaths.get(partPaths.size() - 1).getTail();
        }
        visited.add(start);
        final List<Edge> possibles = findAdjacent(start, visited);
        for (Edge edge : possibles) {
            final Point tail = edge.getTail();
            if (isValidPoint(tail, visited)) {
                final List<Edge> updatedPath = new ArrayList<>(partPaths);
                updatedPath.add(edge);
                queue.add(updatedPath);
            }
        }

        return queue;
    }


    List<Edge> findAdjacent(final Point zero, final List<Point> visited) {
        List<Edge> paths = new ArrayList<>();
        Map<Point, Integer> distanceMap = new HashMap<>();
        distanceMap.put(zero, 0);
        // we will do BFS to get shortest distance from zero in case there are more than one path to take
        final Queue<Point> interestingPoints = new ArrayDeque<>();
        interestingPoints.add(zero);
        while (!interestingPoints.isEmpty()) {
            final Point check = interestingPoints.remove();
            if (coords.contains(check) && !zero.equals(check) && !visited.contains(check)) {
                final Edge edge = new Edge(zero, coords.get(coords.indexOf(check)));
                edge.setDistance(distanceMap.get(check));
                paths.add(edge);
//                System.out.println(edge);
                // if I want just immediate links and not all the links then if the point is part of coords then do not follow to get its neighbours.
                continue;
            }
            final List<Point> neighbours = check.neighbours();
            neighbours.removeIf(walls::contains); // if it is wall ignore
            neighbours.removeIf(distanceMap::containsKey); // if it is traversed ignore

            interestingPoints.addAll(neighbours); // check remaining points
            neighbours.forEach(e -> distanceMap.put(e, distanceMap.get(check) + 1)); // as we calculated neighbours the distance is incremented by one.
        }
        return paths;
    }

    boolean isValidPoint(final Point point, final List<Point> visited) {
        if (visited.stream().anyMatch(point::equals)) {
            return false;
        }
        final String label = point.getLabel();
        if (label.matches("[A-Z]")) {
            return visited.stream().map(Point::getLabel).anyMatch(l -> l.equals(label.toLowerCase()));
        }
        return true;
    }
}

