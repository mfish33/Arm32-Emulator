package instructions;

import Utils.TriFunction;
import computer.Conditional;
import computer.Reg;


public class Sub extends ArithmeticInstruction{
    public Sub(Conditional conditional, boolean setCpsr, Reg target, Reg op1, Reg op2) {
        super(conditional, setCpsr, target, op1, op2, Math::subtractExact, Integer::min);
    }
    public Sub(Conditional conditional, boolean setCpsr, Reg target, Reg op1, int op2) {
        super(conditional, setCpsr, target, op1, op2, Math::subtractExact, Integer::min);
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> {
        var arguments = Instruction.regMaybeImm(parts, 3, true);
        var regs = arguments.getValue0();
        if(arguments.getValue1().isPresent()) {
            return new Sub(cond, setCpsr, regs[0], regs[1], arguments.getValue1().get());
        }
        return new Sub(cond, setCpsr, regs[0], regs[1], regs[2]);
    };
}
