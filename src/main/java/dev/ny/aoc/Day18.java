package dev.ny.aoc;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.stream.Collectors;

public class Day18 {
    public static void main(String[] args) {
        final Navigator nav = new Navigator(INPUT);
        final Set<Edge> edges = nav.definePaths();
        final Map<String, List<String>> collect = edges.stream().collect(Collectors.groupingBy(e -> e.getHead().getLabel(), Collectors.mapping(e -> e.getTail().getLabel(), Collectors.toList())));
        System.out.println(collect);

//        edges.forEach(System.out::println);
//        System.out.println(nav.shortestPath(edges));
//        edges.forEach(System.out::println);
    }

    private static final String INPUT = "########################\n" +
            "#f.D.E.e.C.b.A.@.a.B.c.#\n" +
            "######################.#\n" +
            "#d.....................#\n" +
            "########################";

}

@Data
class Point implements Comparable<Point> {
    final int x;
    final int y;
    @EqualsAndHashCode.Exclude
    String label;
    @EqualsAndHashCode.Exclude
    int distance = Integer.MAX_VALUE;
    @EqualsAndHashCode.Exclude
    String predecessor;


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

    @Override
    public int compareTo(Point o) {
        return Integer.compare(this.distance, o.distance);
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
                    p.setDistance(0);
                    coords.add(0, p);
                }
            }
        }
    }

    Set<Edge> definePaths() {
        final Set<Edge> paths = new HashSet<>();
        final Queue<Point> points = new ArrayDeque<>(coords.size());
        final List<Point> donePoints = new ArrayList<>();
        points.add(coords.get(0));
        while (!points.isEmpty()) {
            final Point head = points.remove();
            final List<Point> visited = new ArrayList<>();
            visited.add(head);
            final Queue<Point> checkList = new ArrayDeque<>();
            checkList.add(head);
            donePoints.add(head);
            final Map<Point, Integer> distanceMap = new HashMap<>();
            distanceMap.put(head, 0);
            while (!checkList.isEmpty()) {
                Point check = checkList.remove();
                visited.add(check);
                if (coords.contains(check) && !donePoints.contains(check)) {
                    final Point source = coords.get(coords.indexOf(head));
                    final Point dest = coords.get(coords.indexOf(check));
                    final Edge e1 = new Edge(source, dest);
                    final Edge e2 = new Edge(dest, source);
                    e1.setDistance(distanceMap.get(check));
                    e2.setDistance(distanceMap.get(check));
                    paths.add(e1);
                    paths.add(e2);
                    points.add(check);
                    continue;
                }
                final List<Point> neighbours = check.neighbours();
                neighbours.removeIf(walls::contains);
                neighbours.removeIf(visited::contains);
                neighbours.forEach(p -> distanceMap.put(p, distanceMap.get(check) + 1));
                checkList.addAll(neighbours);
            }
        }
        return paths;
    }

    int shortestPath(final Set<Edge> edges) {
        List<Point> visited = new ArrayList<>();
        List<Point> unvisited = new ArrayList<>(coords);
        // starting point
        Point head = unvisited.remove(0);
        while (!unvisited.isEmpty()) {
            visited.add(head);
            Point finalHead = head;
            final Set<Edge> adjucentEdges = edges.stream().filter(e -> e.getHead().equals(finalHead)).collect(Collectors.toSet());
            for (Edge e : adjucentEdges) {
                final Point tail = e.getTail();
                // if there is a shorter path then update the Point.
                final int distance = head.getDistance() + e.getDistance();
                if (tail.getDistance() > distance) {
                    tail.setDistance(distance);
                    tail.setPredecessor(head.getLabel());
                }
            }
            if (!unvisited.isEmpty()) {
                final Point possibleHead = adjucentEdges.stream()
                        .map(Edge::getTail)
                        .filter(p -> !visited.contains(p))
                        .sorted(Comparator.comparing(Point::getDistance))
                        .findFirst()
                        .orElseThrow();
                head = unvisited.remove(unvisited.indexOf(possibleHead));
            }
        }
        return 0;
    }
}

