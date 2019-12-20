package dev.ny.aoc;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DrawMaze extends JComponent {
    private final int width = 10;
    private final int height = 10;
    private final int gutter = 2;
    private int offsetX = 0;
    private int offsetY = 0;
    private Set<Day15.Point> content = new HashSet<>();

    DrawMaze() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Test");
        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    private void adjustOffset() {
        offsetX = content.stream().mapToInt(Day15.Point::getX).min().orElse(0);
        offsetY = content.stream().mapToInt(Day15.Point::getY).min().orElse(0);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        adjustOffset();
        for (Day15.Point p : content) {
            g.setPaint(p.getColor());
            g.fillRect((p.getX() - offsetX) * (gutter + width), (p.y - offsetY) * (gutter + height), width, height);
        }
    }

    void setContent(final Set<Day15.Point> content) {
        this.content = content;
        repaint();
    }

    public static void main(String[] args) throws InterruptedException {
        DrawMaze maze = new DrawMaze();
        Set<Day15.Point> test = new HashSet<>();
        test.add(Day15.Point.builder().x(1).y(1).build());
        maze.setContent(test);
        TimeUnit.SECONDS.sleep(3);
        test.add(Day15.Point.builder().x(2).y(3).build());
        maze.setContent(test);
    }


}
