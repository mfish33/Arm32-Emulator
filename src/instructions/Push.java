package instructions;

import Utils.TriFunction;
import computer.Computer;
import computer.Conditional;
import computer.Reg;
import memory.MemoryAccessException;

public class Push extends Instruction{

    private final Reg[] regs;

    public Push(Conditional cond, Reg[] regs) {
        super(cond, false);
        this.regs = regs;
    }

    @Override
    protected int doWork(Computer computer) throws MemoryAccessException {
        var sp = computer.registers[Reg.sp.num];
        for(var reg: regs) {
            var register = computer.registers[reg.num];
            sp.decrement(4);
            var data = Instruction.intToData(register.get());
            computer.memory.write(sp.get(), data);
        }
        return 0;
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> {
        if(parts.length < 2 || setCpsr) {
            throw Instruction.badInput(parts);
        }
        var arguments = Instruction.regMaybeImm(parts, parts.length - 1, false);
        var regs = arguments.getValue0();
        return new Push(cond, regs);
    };
}
