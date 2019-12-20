package dev.ny.aoc;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DrawMaze extends JComponent {
    final int width = 10;
    final int height = 10;
    final int gutter = 2;
    int offsetX = 0;
    int offsetY = 0;
    List<Day15.Point> content = new ArrayList<>();

    public DrawMaze() {
        JFrame frame = new JFrame("Test");
        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    private void adjustOffset() {
        offsetX = content.stream().mapToLong(Day15.Point::getX).mapToInt(Math::toIntExact).min().orElse(0);
        offsetY = content.stream().mapToLong(Day15.Point::getY).mapToInt(Math::toIntExact).min().orElse(0);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        setBackground(Color.DARK_GRAY);
        adjustOffset();
        for (Day15.Point p : content) {
            g.setPaint(p.getColor());
            g.fillRect((Math.toIntExact(p.getX()) - offsetX) * (gutter + width), (Math.toIntExact(p.y) - offsetY) * (gutter + height), width, height);
        }
    }

    public void setContent(final List<Day15.Point> content) {
        this.content = content;
        repaint();
    }

    public static void main(String[] args) throws InterruptedException {
        DrawMaze maze = new DrawMaze();
        maze.setContent(Arrays.asList(Day15.Point.builder().x(1).y(1).build()));
        TimeUnit.SECONDS.sleep(3);
        maze.setContent(Arrays.asList(Day15.Point.builder().x(1).y(1).build(), Day15.Point.builder().x(2).y(3).build()));
    }


}
