package instructions;

import Utils.TriFunction;
import computer.Conditional;
import computer.Reg;

public class Cmp extends ArithmeticInstruction{
    public Cmp(Conditional conditional, Reg op1, Reg op2) {
        super(conditional, true, Reg.INVALID, op1, op2, Math::subtractExact, Integer::min);
    }
    public Cmp(Conditional conditional, Reg op1, int op2) {
        super(conditional, true, Reg.INVALID, op1, op2, Math::subtractExact, Integer::min);
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> {
        var arguments = Instruction.regMaybeImm(parts, 2, true);
        var regs = arguments.getValue0();
        if(arguments.getValue1().isPresent()) {
            return new Cmp(cond, regs[0], arguments.getValue1().get());
        }
        return new Cmp(cond, regs[0], regs[1]);
    };
}
