package dev.ny.aoc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
@EqualsAndHashCode
public class Graph<T> {
    private Map<Vertex<T>, List<Vertex<T>>> edges = new HashMap<>();

    void addVertex(T label) {
        edges.putIfAbsent(new Vertex<>(label), new ArrayList<>());
    }

    void removeVertex(T label) {
        final Vertex<T> v = new Vertex<>(label);
        edges.values().forEach(list -> list.remove(v));
        edges.remove(v);
    }

    void addEdge(T src, T dest) {
        final Vertex<T> s = new Vertex<>(src);
        final Vertex<T> d = new Vertex<>(dest);
        edges.get(s).add(d);
        edges.get(d).add(s);
    }

    void removeEdge(T src, T dest) {
        final Vertex<T> s = new Vertex<>(src);
        final Vertex<T> d = new Vertex<>(dest);
        final List<Vertex<T>> es = edges.get(s);
        final List<Vertex<T>> ed = edges.get(d);
        if (es != null) {
            es.remove(d);
        }
        if (ed != null) {
            ed.remove(s);
        }
    }

    List<Vertex<T>> getLinked(T label) {
        final Vertex<T> head = new Vertex<>(label);
        return edges.get(head);
    }
}

@Data
class Vertex<T> {
    final T label;
}
