package dev.ny.aoc;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 {
    public static void main(String[] args) {
        Point up = new Point(0, 1);
        Point down = new Point(0, -1);
        Point left = new Point(-1, 0);
        Point right = new Point(1, 0);
        final List<Point> directions = Arrays.asList(up, right, down, left);
        int directionPointer = 0;
        final List<Long> series = Arrays.stream(INPUT.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        final IntcodeComputer comp = new IntcodeComputer(series);
        List<Point> touchedPoints = new ArrayList<>();
        Point point = new Point(0, 0);
        point.setColor(1L);
        while (!comp.isHalted()) {
            if (touchedPoints.contains(point)) {
                point = touchedPoints.get(touchedPoints.indexOf(point));
            } else {
                touchedPoints.add(point);
            }
            comp.setInput(point.getColor());
            comp.calcOut();
            point.setColor(comp.getOutput1());
            point.setTimes(point.getTimes() + 1);
            if (comp.getOutput2() == 0) {
                directionPointer += 3;
            } else {
                directionPointer++;
            }
            point = point.next(directions.get(directionPointer % 4));
        }
        System.out.println(touchedPoints.size());
        final Map<Integer, List<Point>> collect = touchedPoints.stream()
                .collect(Collectors.groupingBy(Point::getY));
        final List<Integer> keys = collect.keySet().stream()
                .sorted(Integer::compareTo)
                .collect(Collectors.toList());
        for (int i = keys.size()-1; i >= 0; i--) {
            final List<Point> points = collect.get(keys.get(i));
            final List<Character> row = IntStream.range(0, 50)
                    .mapToObj(num -> ' ')
                    .collect(Collectors.toList());
            points.stream()
                    .filter(p -> p.getColor() == 1)
                    .forEach(p -> row.set(p.x, '#'));
            row.forEach(System.out::print);
            System.out.println();
        }

    }

    private static final String INPUT = "3,8,1005,8,326,1106,0,11,0,0,0,104,1,104,0,3,8,102,-1,8,10,101,1,10,10,4,10,1008,8,1,10,4,10,1001,8,0,29,2,1003,17,10,1006,0,22,2,106,5,10,1006,0,87,3,8,102,-1,8,10,101,1,10,10,4,10,1008,8,1,10,4,10,1001,8,0,65,2,7,20,10,2,9,17,10,2,6,16,10,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,0,10,4,10,101,0,8,99,1006,0,69,1006,0,40,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,1,10,4,10,101,0,8,127,1006,0,51,2,102,17,10,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,1,8,10,4,10,1002,8,1,155,1006,0,42,3,8,1002,8,-1,10,101,1,10,10,4,10,108,0,8,10,4,10,101,0,8,180,1,106,4,10,2,1103,0,10,1006,0,14,3,8,102,-1,8,10,1001,10,1,10,4,10,108,0,8,10,4,10,1001,8,0,213,1,1009,0,10,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,0,8,10,4,10,1002,8,1,239,1006,0,5,2,108,5,10,2,1104,7,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,102,1,8,272,2,1104,12,10,1,1109,10,10,3,8,102,-1,8,10,1001,10,1,10,4,10,108,1,8,10,4,10,102,1,8,302,1006,0,35,101,1,9,9,1007,9,1095,10,1005,10,15,99,109,648,104,0,104,1,21102,937268449940,1,1,21102,1,343,0,1105,1,447,21101,387365315480,0,1,21102,1,354,0,1105,1,447,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,21101,0,29220891795,1,21102,1,401,0,1106,0,447,21101,0,248075283623,1,21102,412,1,0,1105,1,447,3,10,104,0,104,0,3,10,104,0,104,0,21101,0,984353760012,1,21102,1,435,0,1105,1,447,21102,1,718078227200,1,21102,1,446,0,1105,1,447,99,109,2,21202,-1,1,1,21102,40,1,2,21101,0,478,3,21101,468,0,0,1106,0,511,109,-2,2106,0,0,0,1,0,0,1,109,2,3,10,204,-1,1001,473,474,489,4,0,1001,473,1,473,108,4,473,10,1006,10,505,1102,1,0,473,109,-2,2105,1,0,0,109,4,1202,-1,1,510,1207,-3,0,10,1006,10,528,21102,1,0,-3,22102,1,-3,1,22101,0,-2,2,21101,0,1,3,21102,1,547,0,1105,1,552,109,-4,2105,1,0,109,5,1207,-3,1,10,1006,10,575,2207,-4,-2,10,1006,10,575,21202,-4,1,-4,1105,1,643,21202,-4,1,1,21201,-3,-1,2,21202,-2,2,3,21102,1,594,0,1106,0,552,22102,1,1,-4,21101,1,0,-1,2207,-4,-2,10,1006,10,613,21101,0,0,-1,22202,-2,-1,-2,2107,0,-3,10,1006,10,635,22101,0,-1,1,21101,0,635,0,106,0,510,21202,-2,-1,-2,22201,-4,-2,-4,109,-5,2105,1,0";

    @Data
    private static class IntcodeComputer {
        private final List<Long> series;
        private Integer pointer = 0;
        private Integer relativeBase = 0;
        private Long phase;
        boolean isFirstOut = true;
        boolean halted = false;
        private Long input;
        private Long output1;
        private Long output2;

        void calcOut() {
            for (; pointer < series.size(); pointer++) {
                if (halted) {
                    break;
                }
                // understand the opcode
                final String opRaw = "0000" + series.get(pointer);
                final String opFull = opRaw.substring(opRaw.length() - 5);
                final String point3 = opFull.substring(0, 1);
                final String point2 = opFull.substring(1, 2);
                final String point1 = opFull.substring(2, 3);
                final int op = Integer.parseInt(opFull.substring(3));
                // applying opcode
                switch (op) {
                    case 1: // addition
                        setVal(series, pointer + 3, point3, getVal(series, pointer + 1, point1) + getVal(series, pointer + 2, point2));
                        pointer += 3;
                        break;
                    case 2: // multiplication
                        setVal(series, pointer + 3, point3, getVal(series, pointer + 1, point1) * getVal(series, pointer + 2, point2));
                        pointer += 3;
                        break;
                    case 3: // input
                        setVal(series, pointer + 1, point1, input);
                        pointer++;
                        break;
                    case 4: // output
                        if (isFirstOut) {
                            output1 = getVal(series, pointer + 1, point1);
                        } else {
                            output2 = getVal(series, pointer + 1, point1);
                        }
                        isFirstOut = !isFirstOut;
                        if (isFirstOut) {
                            pointer += 2;
                            return;
                        }
                        pointer++;
                        break;
//                    return output;
                    case 5: // jump if true
                        if (getVal(series, pointer + 1, point1) != 0) {
                            pointer = Math.toIntExact(getVal(series, pointer + 2, point2)) - 1;
                        } else {
                            pointer += 2;
                        }
                        break;
                    case 6:
                        if (getVal(series, pointer + 1, point1) == 0) {
                            pointer = Math.toIntExact(getVal(series, pointer + 2, point2) - 1);
                        } else {
                            pointer += 2;
                        }
                        break;
                    case 7: // less than
                        setVal(series, pointer + 3, point3, getVal(series, pointer + 1, point1) < getVal(series, pointer + 2, point2) ? 1L : 0L);
                        pointer += 3;
                        break;
                    case 8: // equals
                        setVal(series, pointer + 3, point3, getVal(series, pointer + 1, point1).equals(getVal(series, pointer + 2, point2)) ? 1L : 0L);
                        pointer += 3;
                        break;
                    case 9:
                        relativeBase = Math.toIntExact(relativeBase + getVal(series, pointer + 1, point1));
                        pointer++;
                        break;
                    case 99:
                        halted = true;
                        break;
                    default:
                        System.out.println("ERROR");
                }
            }
        }

        private Long getVal(final List<Long> series, final int pos, final String mode) {
            return getValWithDefault(series, getPos(series, pos, mode));
        }

        private void setVal(final List<Long> series, final int pos, final String mode, final Long value) {
            final int addPos = getPos(series, pos, mode);
            addPadding(series, addPos);
            series.set(addPos, value);
        }

        private int getPos(final List<Long> series, final int pos, final String mode) {
            if (mode.equals("1")) { // value mode
                return pos;
            } else if (mode.equals("0")) { // abs position mode
                return Math.toIntExact(getValWithDefault(series, pos));
            } else { // relative position mode
                return Math.toIntExact(getValWithDefault(series, pos) + relativeBase);
            }
        }

        private Long getValWithDefault(final List<Long> series, final int pos) {
            addPadding(series, pos);
            return series.get(pos);
        }

        private void addPadding(final List<Long> series, final int pos) {
            if (pos >= series.size()) {
                for (int i = series.size(); i <= pos; i++) {
                    series.add(i, 0L);
                }
            }
        }
    }

    @Data
    private static class Point {
        final int x;
        final int y;
        @EqualsAndHashCode.Exclude
        int times = 0;
        @EqualsAndHashCode.Exclude
        long color = 0;

        Point next(Point add) {
            return new Point(this.x + add.x, this.y + add.y);
        }
    }
}
