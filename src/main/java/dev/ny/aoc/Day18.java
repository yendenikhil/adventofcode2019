package dev.ny.aoc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Day18 {
    public static void main(String[] args) {
        final Navigator18 nav = new Navigator18(INPUT);
    }

    private static final String INPUT = "########################\n" +
            "#f.D.E.e.C.b.A.@.a.B.c.#\n" +
            "######################.#\n" +
            "#d.....................#\n" +
            "########################";

}

@Data
class Point18 {
    final int x;
    final int y;
    @EqualsAndHashCode.Exclude
    int distance = 0;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    String label;

    private Point18 next(final int dx, final int dy, final int dDistance) {
        final Point18 point18 = new Point18(this.x + dx, this.y + dy);
        point18.setDistance(this.distance + dDistance);
        return point18;
    }

    List<Point18> neighbours(final int distance) {
        List<Point18> list = new ArrayList<>(4);
        list.add(this.next(1, 0, distance));
        list.add(this.next(-1, 0, distance));
        list.add(this.next(0, 1, distance));
        list.add(this.next(0, -1, distance));
        return list;
    }

    @Override
    protected Point18 clone() {
        return new Point18(this.x, this.y);
    }
}

class Navigator18 {
    private Point18 start;
    private final List<Point18> walls = new ArrayList<>();
    private final List<Point18> coords = new ArrayList<>();
    private final Graph<Point18> graph = new Graph<>();

    public Navigator18(final String input) {
        final String[] lines = input.split("\n");
        for (int i = 0; i < lines.length; i++) {
            final char[] chars = lines[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                final String s = String.valueOf(chars[j]);
                if (s.equals("#")) {
                    walls.add(new Point18(j, i));
                } else if (s.matches("^[a-zA-Z]$")) {
                    final Point18 p = new Point18(j, i);
                    p.setLabel(s);
                    coords.add(p);
                } else if (s.equals("@")) {
                    start = new Point18(j, i);
                }
            }
        }
        buildGraph();
        System.out.println(graph);
    }

    void buildGraph() {
        graph.addVertex(start);
        coords.forEach(graph::addVertex);
        Queue<Point18> queue = new ArrayDeque<>();
        queue.add(start);
        queue.addAll(coords);
        while (!queue.isEmpty()) {
            final Point18 head = queue.remove();
            final List<Point18> connections = getConnections(head, new ArrayList<>(queue));
            connections.forEach(c -> graph.addEdge(head, c));
        }
    }

    List<Point18> getConnections(final Point18 head, final List<Point18> possibleLinks) {
        List<Point18> links = new ArrayList<>();
        Queue<Point18> buffer = new ArrayDeque<>();
        buffer.add(head.clone());
        List<Point18> visited = new ArrayList<>();
        while (!buffer.isEmpty()) {
            final Point18 e = buffer.remove();
            visited.add(e);
            if (possibleLinks.contains(e)) {
                links.add(e);
                continue;
            }
            final List<Point18> neighbours = e.neighbours(1);
            neighbours.removeIf(visited::contains);
            neighbours.removeIf(walls::contains);
            buffer.addAll(neighbours);
        }
        return links;
    }
}

