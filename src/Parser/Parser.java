package Parser;

import Utils.TriConsumer;
import Utils.TriFunction;
import compilerDirectives.*;
import computer.Conditional;
import instructions.*;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Parser {


    private static final Map<String, TriFunction<Conditional, Boolean, String[], Instruction>> instructionMap;

    static {
        instructionMap = new HashMap<>();
        instructionMap.put("add", Add.create);
        instructionMap.put("b", B.create);
        instructionMap.put("bl", Bl.create);
        instructionMap.put("bx", Bx.create);
        instructionMap.put("cmp", Cmp.create);
        instructionMap.put("end", End.create);
        instructionMap.put("ldr", Ldr.create);
        instructionMap.put("ldrb", Ldrb.create);
        instructionMap.put("mla", Mla.create);
        instructionMap.put("mov", Mov.create);
        instructionMap.put("mul", Mul.create);
        instructionMap.put("mvn", Mvn.create);
        instructionMap.put("pop", Pop.create);
        instructionMap.put("push", Push.create);
        instructionMap.put("str", Str.create);
        instructionMap.put("strb", Strb.create);
        instructionMap.put("sub", Sub.create);
    }

    private static  final Map<String, TriConsumer<String[], String, ArrayList<Instruction>>> assemblyDirectives;

    static  {
        assemblyDirectives = new HashMap<>();
        assemblyDirectives.put("long", LongDir.create);
        assemblyDirectives.put("asciz", Asciz.create);
    }

    private static boolean isLabelCreation(String s) {
        return s.matches("^(\\.|\\w)+:$");
    }

    public static boolean isLabel(String s) {
        return s.matches("^(\\.|\\w)+$");
    }


    public static Instruction[] parse(String input, ArrayList<Integer> breakPoints) throws Exception {
        var labels = new HashMap<String,Integer>();
        var instructions = new ArrayList<Instruction>();
        var lines = Arrays
                .stream(input.toLowerCase().split("[\\r\\n]+"))
                .map(s -> s.trim())
                .toArray(size -> new String[size]);
        for(int i = 0; i < lines.length; i++) {
            var line = lines[i];
            if(Parser.isLabelCreation(line)) {
                var labelReference = Pattern.compile("\\w+").matcher(line).results().findFirst().get().group();
                labels.put(labelReference, instructions.size());
                continue;
            }
            var parts = Pattern.compile("[a-zA-Z0-9_-]+")
                    .matcher(line)
                    .results()
                    .map(MatchResult::group)
                    .toArray(String[]::new);
            var instructionSpecifier = parts[0];
            var assemblyDirective = assemblyDirectives.get(instructionSpecifier);
            if(assemblyDirective != null) {
                assemblyDirective.accept(parts, line, instructions);
                continue;
            }
            var setCpsr = false;
            var cond = Conditional.al;
            if(instructionSpecifier.length() > 2) {
               var maybeConditional = Conditional.fromStr(instructionSpecifier.substring(instructionSpecifier.length() - 2));
               if(maybeConditional.isPresent()) {
                   cond = maybeConditional.get();
                   instructionSpecifier = instructionSpecifier.substring(0, instructionSpecifier.length() - 2);
               }
               if(instructionSpecifier.substring(instructionSpecifier.length() - 1).equals("s")) {
                   setCpsr = true;
                   instructionSpecifier = instructionSpecifier.substring(0, instructionSpecifier.length() - 1);
               }
            }
            var handler = instructionMap.get(instructionSpecifier);
            if(handler == null) {
                throw new UnsupportedOperationException("Could not parse line: " + line);
            }

           var instruction = handler.apply(cond, setCpsr, parts);
            if(breakPoints.size() > 0) {
                var currentBreakpointLine = breakPoints.get(breakPoints.size() - 1);
                if(currentBreakpointLine == i + 1) {
                    instruction.setBreakpoint();
                    breakPoints.remove(breakPoints.size() - 1);
                }
            }
            instructions.add(instruction);
        }

        // Second pass add branch locations
        for(int i = 0; i < instructions.size(); i++) {
            var instruction = instructions.get(i);
            if(instruction instanceof LabelReplacement) {
                var labelTarget = ((LabelReplacement) instruction).getLabel();
                var targetInstruction = labels.get(labelTarget);
                if(targetInstruction == null) {
                    throw new UnsupportedOperationException("Could not find label definition: " + labelTarget);
                }
                ((LabelReplacement) instruction).setNumericalValue((targetInstruction - i - 1) * 4);
            }
        }

        return instructions.toArray(Instruction[]::new);
    }
}
