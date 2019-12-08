package dev.ny.aoc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
    public static void main(String[] args) {
        final String inputInstructions = "3,225,1,225,6,6,1100,1,238,225,104,0,1101,33,37,225,101,6,218,224,1001,224,-82,224,4,224,102,8,223,223,101,7,224,224,1,223,224,223,1102,87,62,225,1102,75,65,224,1001,224,-4875,224,4,224,1002,223,8,223,1001,224,5,224,1,224,223,223,1102,49,27,225,1101,6,9,225,2,69,118,224,101,-300,224,224,4,224,102,8,223,223,101,6,224,224,1,224,223,223,1101,76,37,224,1001,224,-113,224,4,224,1002,223,8,223,101,5,224,224,1,224,223,223,1101,47,50,225,102,43,165,224,1001,224,-473,224,4,224,102,8,223,223,1001,224,3,224,1,224,223,223,1002,39,86,224,101,-7482,224,224,4,224,102,8,223,223,1001,224,6,224,1,223,224,223,1102,11,82,225,1,213,65,224,1001,224,-102,224,4,224,1002,223,8,223,1001,224,6,224,1,224,223,223,1001,14,83,224,1001,224,-120,224,4,224,1002,223,8,223,101,1,224,224,1,223,224,223,1102,53,39,225,1101,65,76,225,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,1107,677,226,224,1002,223,2,223,1005,224,329,101,1,223,223,8,677,226,224,102,2,223,223,1006,224,344,1001,223,1,223,108,677,677,224,1002,223,2,223,1006,224,359,1001,223,1,223,1108,226,677,224,102,2,223,223,1006,224,374,1001,223,1,223,1008,677,226,224,102,2,223,223,1005,224,389,101,1,223,223,7,226,677,224,102,2,223,223,1005,224,404,1001,223,1,223,1007,677,677,224,1002,223,2,223,1006,224,419,101,1,223,223,107,677,226,224,102,2,223,223,1006,224,434,101,1,223,223,7,677,677,224,1002,223,2,223,1005,224,449,101,1,223,223,108,677,226,224,1002,223,2,223,1006,224,464,101,1,223,223,1008,226,226,224,1002,223,2,223,1006,224,479,101,1,223,223,107,677,677,224,1002,223,2,223,1006,224,494,1001,223,1,223,1108,677,226,224,102,2,223,223,1005,224,509,101,1,223,223,1007,226,677,224,102,2,223,223,1005,224,524,1001,223,1,223,1008,677,677,224,102,2,223,223,1005,224,539,1001,223,1,223,1107,677,677,224,1002,223,2,223,1006,224,554,1001,223,1,223,1007,226,226,224,1002,223,2,223,1005,224,569,1001,223,1,223,7,677,226,224,1002,223,2,223,1006,224,584,1001,223,1,223,108,226,226,224,102,2,223,223,1005,224,599,1001,223,1,223,8,677,677,224,102,2,223,223,1005,224,614,1001,223,1,223,1107,226,677,224,102,2,223,223,1005,224,629,1001,223,1,223,8,226,677,224,102,2,223,223,1006,224,644,1001,223,1,223,1108,226,226,224,1002,223,2,223,1006,224,659,101,1,223,223,107,226,226,224,1002,223,2,223,1006,224,674,1001,223,1,223,4,223,99,226";
        final List<Integer> tokens = Arrays.stream(inputInstructions.split(",")).map(Integer::parseInt).collect(Collectors.toList());
//        final int input = 1;
        final int input = 5; // for part 2
        boolean flag = false;
        for (int i = 0; i < tokens.size(); i++) {
            // understand the opcode
            final String opRaw = "0000" + tokens.get(i);
            final String opFull = opRaw.substring(opRaw.length() - 5);
            final String point3 = opFull.substring(0, 1);
            final String point2 = opFull.substring(1, 2);
            final String point1 = opFull.substring(2, 3);
            final int op = Integer.parseInt(opFull.substring(3));
            // applying opcode
            switch (op) {
                case 1: // addition
                    tokens.set(getPos(tokens, i + 3, point3), tokens.get(getPos(tokens, i + 1, point1)) + tokens.get(getPos(tokens, i + 2, point2)));
                    i += 3;
                    break;
                case 2: // multiplication
                    tokens.set(getPos(tokens, i + 3, point3), tokens.get(getPos(tokens, i + 1, point1)) * tokens.get(getPos(tokens, i + 2, point2)));
                    i += 3;
                    break;
                case 3: // input
                    tokens.set(getPos(tokens, i + 1, point1), input);
                    i++;
                    break;
                case 4: // output
                    System.out.println(tokens.get(getPos(tokens, i + 1, point1)));
                    i++;
                    break;
                case 5: // jump if true
                    if (tokens.get(getPos(tokens, i + 1, point1)) != 0) {
                        i = tokens.get(getPos(tokens, i + 2, point2)) - 1;
                    } else {
                        i += 2;
                    }
                    break;
                case 6:
                    if (tokens.get(getPos(tokens, i + 1, point1)) == 0) {
                        i = tokens.get(getPos(tokens, i + 2, point2)) - 1;
                    } else {
                        i += 2;
                    }
                    break;
                case 7: // less than
                    tokens.set(getPos(tokens, i + 3, point3), tokens.get(getPos(tokens, i + 1, point1)) < tokens.get(getPos(tokens, i + 2, point2)) ? 1 : 0);
                    i += 3;
                    break;
                case 8: // equals
                    tokens.set(getPos(tokens, i + 3, point3), tokens.get(getPos(tokens, i + 1, point1)).equals(tokens.get(getPos(tokens, i + 2, point2))) ? 1 : 0);
                    i += 3;
                    break;
                case 99:
                    flag = true;
                    break;
                default:
                    System.out.println("ERROR");
            }
            if (flag) {
                break;
            }

        }

    }

    private static int getPos(final List<Integer> series, final int pos, final String mode) {
        if (mode.equals("1")) {
            return pos;
        } else {
            return series.get(pos);
        }
    }

}
