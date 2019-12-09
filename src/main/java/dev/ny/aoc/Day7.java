package dev.ny.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {

    private static final String INPUT_INSTRUCTIONS = "3,8,1001,8,10,8,105,1,0,0,21,34,51,76,101,114,195,276,357,438,99999,3,9,1001,9,3,9,1002,9,3,9,4,9,99,3,9,101,4,9,9,102,4,9,9,1001,9,5,9,4,9,99,3,9,1002,9,4,9,101,3,9,9,102,5,9,9,1001,9,2,9,1002,9,2,9,4,9,99,3,9,1001,9,3,9,102,2,9,9,101,4,9,9,102,3,9,9,101,2,9,9,4,9,99,3,9,102,2,9,9,101,4,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,99,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,99";
//    private static final String INPUT_INSTRUCTIONS = "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5";

    public static void main(String[] args) {
        final List<String> phaseChoicesPart1 = perms("01234", 0, 4);
        final List<String> phaseChoicesPart2 = perms("56789", 0, 4);
        System.out.println(maxOutputPart1(phaseChoicesPart1));
//        System.out.println(maxOutputPart2(phaseChoicesPart2));
    }

    private static List<String> perms(String input, final int initialPos, final int length) {
        List<String> ans = new ArrayList<>();
        if (initialPos == length) {
            ans.add(input);
        }
        for (int i = initialPos; i <= length; i++) {
            input = swap(input, initialPos, i);
            ans.addAll(perms(input, initialPos + 1, length));
            input = swap(input, initialPos, i);
        }
        return ans;
    }

    private static String swap(final String input, final int i, final int j) {
        final char[] chars = input.toCharArray();
        final char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return String.valueOf(chars);
    }


    private static int maxOutputPart1(List<String> phaseChoices) {
        int ans = 0;
        for (String phaseChoice : phaseChoices) {
            int prevOutput = 0;
            for (int p : phaseChoice.toCharArray()) {
                prevOutput = intCodeOutput(p - 48, prevOutput);
            }
            ans = Math.max(prevOutput, ans);
        }
        return ans;
    }
    private static int maxOutputPart2(List<String> phaseChoices) {
        int ans = 0;
        for (String phaseChoice : phaseChoices) {
            int prevOutput = 0;
            final List<Integer> phaseInput = phaseChoice.chars()
                    .map(num -> num - 48).boxed().collect(Collectors.toList());
            for (int p : phaseChoice.toCharArray()) {
                prevOutput = intCodeOutput(p - 48, prevOutput);
            }
            ans = Math.max(prevOutput, ans);
        }
        return ans;
    }

    private static int intCodeOutput(final int phase, final int prevOutput) {
        final List<Integer> tokens = Arrays.stream(INPUT_INSTRUCTIONS.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        int output = 0;
        boolean flag = false;
        boolean usePhase = true;
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
                    tokens.set(getPos(tokens, i + 1, point1), usePhase ? phase : prevOutput);
                    usePhase = false;
                    i++;
                    break;
                case 4: // output
//                    System.out.println(tokens.get(getPos(tokens, i + 1, point1)));
                    output = tokens.get(getPos(tokens, i + 1, point1));
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
        return output;
    }

    private static int getPos(final List<Integer> series, final int pos, final String mode) {
        if (mode.equals("1")) {
            return pos;
        } else {
            return series.get(pos);
        }
    }
}
