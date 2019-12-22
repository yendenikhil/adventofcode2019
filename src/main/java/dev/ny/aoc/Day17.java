package dev.ny.aoc;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Day17 {
    public static void main(String[] args) {
        final IntcodeComputer comp = new IntcodeComputer(INPUT);
        final List<List<Integer>> data = extractData(comp.calcOutput());
        partA(data);
        partB();
    }

    private static void partB() {
        // TODO find the movements by program. and three groups.
        final String rawMovement = "R,12,L,8,R,6,R,12,L,8,R,6,R,12,L,6,R,6,R,8,R,6,L,8,R,8,R,6,R,12,R,12,L,8,R,6,L,8,R,8,R,6,R,12,R,12,L,8,R,6,R,12,L,6,R,6,R,8,R,6,L,8,R,8,R,6,R,12,R,12,L,6,R,6,R,8,R,6";
        final String A = "R,12,L,8,R,6";
        final String B = "R,12,L,6,R,6,R,8,R,6";
        final String C = "L,8,R,8,R,6,R,12";
        final IntcodeComputer comp = new IntcodeComputer(INPUT.replaceFirst("1", "2"));
        comp.setInput("A,A,B,C,A,C,A,B,C,B\n" + A + "\n" + B + "\n" + C + "\nn\n");
        final List<Integer> outputs = comp.calcOutput();
        outputs.forEach(s -> System.out.print((char) s.intValue()));
        System.out.println(outputs.get(outputs.size() - 1));
    }

    private static void partA(final List<List<Integer>> data) {
        int result = 0;
        for (int i = 1; i < data.size() - 1; i++) {
            final List<Integer> prevLine = data.get(i - 1);
            final List<Integer> line = data.get(i);
            final List<Integer> postLine = data.get(i + 1);
            for (int j = 1; j < line.size() - 1; j++) {
                if (line.get(j) == 35 && line.get(j + 1) == 35 && line.get(j - 1) == 35 && prevLine.get(j) == 35 && postLine.get(j) == 35) {
                    result += (i * j);
                }
            }
        }
        System.out.println("part A: " + result);
    }

    private static List<List<Integer>> extractData(List<Integer> input) {
        input.forEach(s -> System.out.print((char) s.intValue()));
        List<List<Integer>> output = new ArrayList<>();
        List<Integer> line = new ArrayList<>();
        for (Integer num : input) {
            if (num == 10) {
                output.add(line);
                line = new ArrayList<>();
            } else {
                line.add(num);
            }
        }
        return output;
    }

    private static final String INPUT = "1,330,331,332,109,3468,1102,1182,1,16,1101,0,1479,24,101,0,0,570,1006,570,36,1002,571,1,0,1001,570,-1,570,1001,24,1,24,1106,0,18,1008,571,0,571,1001,16,1,16,1008,16,1479,570,1006,570,14,21102,58,1,0,1105,1,786,1006,332,62,99,21101,0,333,1,21101,73,0,0,1105,1,579,1101,0,0,572,1102,1,0,573,3,574,101,1,573,573,1007,574,65,570,1005,570,151,107,67,574,570,1005,570,151,1001,574,-64,574,1002,574,-1,574,1001,572,1,572,1007,572,11,570,1006,570,165,101,1182,572,127,101,0,574,0,3,574,101,1,573,573,1008,574,10,570,1005,570,189,1008,574,44,570,1006,570,158,1105,1,81,21101,0,340,1,1106,0,177,21102,477,1,1,1106,0,177,21101,0,514,1,21102,176,1,0,1105,1,579,99,21101,0,184,0,1106,0,579,4,574,104,10,99,1007,573,22,570,1006,570,165,1001,572,0,1182,21102,375,1,1,21102,211,1,0,1105,1,579,21101,1182,11,1,21102,222,1,0,1106,0,979,21101,0,388,1,21101,233,0,0,1106,0,579,21101,1182,22,1,21102,244,1,0,1106,0,979,21101,0,401,1,21101,0,255,0,1105,1,579,21101,1182,33,1,21102,266,1,0,1105,1,979,21102,414,1,1,21102,1,277,0,1106,0,579,3,575,1008,575,89,570,1008,575,121,575,1,575,570,575,3,574,1008,574,10,570,1006,570,291,104,10,21101,1182,0,1,21102,313,1,0,1106,0,622,1005,575,327,1101,1,0,575,21101,0,327,0,1105,1,786,4,438,99,0,1,1,6,77,97,105,110,58,10,33,10,69,120,112,101,99,116,101,100,32,102,117,110,99,116,105,111,110,32,110,97,109,101,32,98,117,116,32,103,111,116,58,32,0,12,70,117,110,99,116,105,111,110,32,65,58,10,12,70,117,110,99,116,105,111,110,32,66,58,10,12,70,117,110,99,116,105,111,110,32,67,58,10,23,67,111,110,116,105,110,117,111,117,115,32,118,105,100,101,111,32,102,101,101,100,63,10,0,37,10,69,120,112,101,99,116,101,100,32,82,44,32,76,44,32,111,114,32,100,105,115,116,97,110,99,101,32,98,117,116,32,103,111,116,58,32,36,10,69,120,112,101,99,116,101,100,32,99,111,109,109,97,32,111,114,32,110,101,119,108,105,110,101,32,98,117,116,32,103,111,116,58,32,43,10,68,101,102,105,110,105,116,105,111,110,115,32,109,97,121,32,98,101,32,97,116,32,109,111,115,116,32,50,48,32,99,104,97,114,97,99,116,101,114,115,33,10,94,62,118,60,0,1,0,-1,-1,0,1,0,0,0,0,0,0,1,24,22,0,109,4,2102,1,-3,586,21002,0,1,-1,22101,1,-3,-3,21101,0,0,-2,2208,-2,-1,570,1005,570,617,2201,-3,-2,609,4,0,21201,-2,1,-2,1105,1,597,109,-4,2106,0,0,109,5,1202,-4,1,630,20101,0,0,-2,22101,1,-4,-4,21102,0,1,-3,2208,-3,-2,570,1005,570,781,2201,-4,-3,652,21002,0,1,-1,1208,-1,-4,570,1005,570,709,1208,-1,-5,570,1005,570,734,1207,-1,0,570,1005,570,759,1206,-1,774,1001,578,562,684,1,0,576,576,1001,578,566,692,1,0,577,577,21101,702,0,0,1105,1,786,21201,-1,-1,-1,1105,1,676,1001,578,1,578,1008,578,4,570,1006,570,724,1001,578,-4,578,21101,0,731,0,1105,1,786,1105,1,774,1001,578,-1,578,1008,578,-1,570,1006,570,749,1001,578,4,578,21101,756,0,0,1106,0,786,1105,1,774,21202,-1,-11,1,22101,1182,1,1,21102,1,774,0,1105,1,622,21201,-3,1,-3,1105,1,640,109,-5,2106,0,0,109,7,1005,575,802,20101,0,576,-6,20101,0,577,-5,1106,0,814,21102,0,1,-1,21101,0,0,-5,21102,0,1,-6,20208,-6,576,-2,208,-5,577,570,22002,570,-2,-2,21202,-5,51,-3,22201,-6,-3,-3,22101,1479,-3,-3,1202,-3,1,843,1005,0,863,21202,-2,42,-4,22101,46,-4,-4,1206,-2,924,21101,1,0,-1,1106,0,924,1205,-2,873,21102,1,35,-4,1106,0,924,1201,-3,0,878,1008,0,1,570,1006,570,916,1001,374,1,374,1201,-3,0,895,1101,0,2,0,1201,-3,0,902,1001,438,0,438,2202,-6,-5,570,1,570,374,570,1,570,438,438,1001,578,558,922,20101,0,0,-4,1006,575,959,204,-4,22101,1,-6,-6,1208,-6,51,570,1006,570,814,104,10,22101,1,-5,-5,1208,-5,39,570,1006,570,810,104,10,1206,-1,974,99,1206,-1,974,1102,1,1,575,21101,973,0,0,1105,1,786,99,109,-7,2106,0,0,109,6,21102,0,1,-4,21102,1,0,-3,203,-2,22101,1,-3,-3,21208,-2,82,-1,1205,-1,1030,21208,-2,76,-1,1205,-1,1037,21207,-2,48,-1,1205,-1,1124,22107,57,-2,-1,1205,-1,1124,21201,-2,-48,-2,1106,0,1041,21102,1,-4,-2,1106,0,1041,21102,-5,1,-2,21201,-4,1,-4,21207,-4,11,-1,1206,-1,1138,2201,-5,-4,1059,1201,-2,0,0,203,-2,22101,1,-3,-3,21207,-2,48,-1,1205,-1,1107,22107,57,-2,-1,1205,-1,1107,21201,-2,-48,-2,2201,-5,-4,1090,20102,10,0,-1,22201,-2,-1,-2,2201,-5,-4,1103,2102,1,-2,0,1105,1,1060,21208,-2,10,-1,1205,-1,1162,21208,-2,44,-1,1206,-1,1131,1105,1,989,21102,439,1,1,1105,1,1150,21102,477,1,1,1105,1,1150,21102,1,514,1,21101,1149,0,0,1105,1,579,99,21101,0,1157,0,1106,0,579,204,-2,104,10,99,21207,-3,22,-1,1206,-1,1138,1201,-5,0,1176,1202,-4,1,0,109,-6,2105,1,0,22,7,44,1,5,1,40,7,3,1,40,1,3,1,1,1,3,1,22,9,9,1,3,1,1,1,3,1,22,1,7,1,9,1,3,1,1,1,3,1,22,1,7,1,1,13,1,1,3,1,22,1,7,1,1,1,7,1,5,1,3,1,22,1,7,1,1,1,7,1,3,7,22,1,7,1,1,1,7,1,5,1,26,7,1,9,1,9,30,1,3,1,5,1,7,1,1,1,30,1,3,1,5,1,7,1,1,1,30,1,3,1,5,1,7,1,1,1,30,1,3,1,1,13,1,1,9,7,14,1,3,1,1,1,3,1,9,1,9,1,5,1,14,1,3,1,1,1,3,1,9,9,1,1,5,1,14,1,3,1,1,1,3,1,17,1,1,1,5,1,14,1,3,7,17,1,1,1,5,1,14,1,5,1,21,1,1,1,5,1,14,1,5,1,21,1,1,1,5,1,14,1,5,1,21,1,1,1,5,1,14,7,11,13,1,9,38,1,3,1,3,1,3,1,38,1,3,1,3,1,3,1,38,1,3,1,3,1,3,1,38,1,3,1,3,9,34,1,3,1,7,1,3,1,34,13,3,1,38,1,11,1,32,7,11,1,32,1,17,1,32,1,5,13,32,1,5,1,44,1,5,1,44,1,5,1,44,1,5,1,44,1,5,1,44,7,12";

    @Data
    private static class IntcodeComputer {
        private final List<Long> series;

        IntcodeComputer(final String input) {
            series = Arrays.stream(input.split(","))
                    .map(Long::parseLong)
                    .collect(toList());
        }

        private Integer pointer = 0;
        private Integer relativeBase = 0;
        boolean halted = false;
        private LinkedList<Integer> inputs;
        private List<Integer> output = new ArrayList<>();

        void setInput(final String input) {
            inputs = input.chars().boxed().collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
        }

        List<Integer> calcOutput() {
            while (!halted) {
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
                        pointer += 4;
                        break;
                    case 2: // multiplication
                        setVal(series, pointer + 3, point3, getVal(series, pointer + 1, point1) * getVal(series, pointer + 2, point2));
                        pointer += 4;
                        break;
                    case 3: // input
                        setVal(series, pointer + 1, point1, Long.valueOf(inputs.pop()));
                        pointer += 2;
                        break;
                    case 4: // output
                        final Long val = getVal(series, pointer + 1, point1);
                        output.add(Math.toIntExact(val));
                        pointer += 2;
                        break;
//                        return Math.toIntExact(val);
                    case 5: // jump if true
                        if (getVal(series, pointer + 1, point1) != 0) {
                            pointer = Math.toIntExact(getVal(series, pointer + 2, point2));
                        } else {
                            pointer += 3;
                        }
                        break;
                    case 6: // jump if false
                        if (getVal(series, pointer + 1, point1) == 0) {
                            pointer = Math.toIntExact(getVal(series, pointer + 2, point2));
                        } else {
                            pointer += 3;
                        }
                        break;
                    case 7: // less than
                        setVal(series, pointer + 3, point3, getVal(series, pointer + 1, point1) < getVal(series, pointer + 2, point2) ? 1L : 0L);
                        pointer += 4;
                        break;
                    case 8: // equals
                        setVal(series, pointer + 3, point3, getVal(series, pointer + 1, point1).equals(getVal(series, pointer + 2, point2)) ? 1L : 0L);
                        pointer += 4;
                        break;
                    case 9:
                        relativeBase = Math.toIntExact(relativeBase + getVal(series, pointer + 1, point1));
                        pointer += 2;
                        break;
                    case 99:
                        halted = true;
                        break;
                    default:
                        System.out.println("ERROR");
                }
            }
//            System.out.println("This point should not be reached!");
            return output;
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
}
/*
......................*******......................
......................*.....*......................
..................*******...*......................
..................*...*.*...*......................
*********.........*...*.*...*......................
*.......*.........*...*.*...*......................
*.......*.*************.*...*......................
*.......*.*.......*.....*...*......................
*.......*.*.......*...*******......................
*.......*.*.......*.....*..........................
*******.*********.*********........................
......*...*.....*.......*.*........................
......*...*.....*.......*.*........................
......*...*.....*.......*.*........................
......*...*.*************.*.........*******........
......*...*.*...*.........*.........*.....*........
......*...*.*...*.........*********.*.....*........
......*...*.*...*.................*.*.....*........
......*...*******.................*.*.....*........
......*.....*.....................*.*.....*........
......*.....*.....................*.*.....*........
......*.....*.....................*.*.....*........
......*******...........^************.*********....
..................................*...*...*...*....
..................................*...*...*...*....
..................................*...*...*...*....
..................................*...*...*********
..................................*...*.......*...*
..................................*************...*
......................................*...........*
................................*******...........*
................................*.................*
................................*.....*************
................................*.....*............
................................*.....*............
................................*.....*............
................................*.....*............
................................*.....*............
................................*******............
R,12,L,8,R,6,R,12,L,8,R,6,R,12,L,6,R,6,R,8,R,6,L,8,R,8,R,6,R,12,R,12,L,8,R,6,L,8,R,8,R,6,R,12,R,12,L,8,R,6,R,12,L,6,R,6,R,8,R,6,L,8,R,8,R,6,R,12,R,12,L,6,R,6,R,8,R,6

R,12,L,8,R,6
R,12,L,6,R,6,R,8,R,6
L,8,R,8,R,6,R,12

 */