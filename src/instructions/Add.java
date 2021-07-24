package instructions;

import Utils.TriFunction;
import computer.Conditional;
import computer.Reg;

import java.util.Arrays;

public class Add extends ArithmeticInstruction{


    public Add(Conditional conditional, boolean setCpsr, Reg target, Reg op1, Reg op2, int shift) {
        super(conditional, setCpsr, target, op1, op2, Math::addExact, Integer::sum, shift);
    }
    public Add(Conditional conditional, boolean setCpsr, Reg target, Reg op1, int op2, int shift) {
        super(conditional, setCpsr, target, op1, op2, Math::addExact, Integer::sum, shift);
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond,setCpsr,parts) -> {
        int shiftAmount = 0;
        if(parts.length == 6 && parts[4].equals("lsl")) {
            shiftAmount = Instruction.getImmediate(parts[5]).get();
        }
        var arguments = Instruction.regMaybeImm(Arrays.copyOfRange(parts, 0, 4), 3, true);
        var regs = arguments.getValue0();
        if(arguments.getValue1().isPresent()) {
            return new Add(cond, setCpsr, regs[0], regs[1], arguments.getValue1().get(),shiftAmount);
        }
        return new Add(cond, setCpsr, regs[0], regs[1], regs[2], shiftAmount);
    };

}
