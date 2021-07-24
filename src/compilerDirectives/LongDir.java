package compilerDirectives;

import Utils.TriConsumer;
import instructions.ConstantAlias;
import instructions.Instruction;
import instructions.LabelAliasInstruction;

import java.util.ArrayList;

public class LongDir {
    public static TriConsumer<String[], String, ArrayList<Instruction>> create = (parts, line, instructionList) -> {
        if(parts.length != 2) {
            throw Instruction.badInput(parts);
        }
        var num = Instruction.getImmediate(parts[1]);
        num.ifPresent(integer -> instructionList.add(new ConstantAlias(integer)));
        instructionList.add(new LabelAliasInstruction(parts[1]));
    };
}
