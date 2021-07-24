package instructions;

import Utils.TriFunction;
import computer.Computer;
import computer.Conditional;
import computer.Reg;
import memory.MemoryAccessException;

import java.util.ArrayList;

public class Pop extends Instruction{

    private final Reg[] regs;

    public Pop(Conditional cond, Reg[] regs) {
        super(cond, false);
        this.regs = regs;
    }

    @Override
    protected int doWork(Computer computer) throws MemoryAccessException {
        var sp = computer.registers[Reg.sp.num];
        for(int i = regs.length - 1; i >= 0; i--) {
            var register = computer.registers[regs[i].num];
            var data = (byte[]) computer.memory.read(sp.get(), 4);
            register.set(Instruction.dataToInt(data));
            sp.increment(4);
        }
        return 0;
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> {
        if(parts.length < 2 || setCpsr) {
            throw Instruction.badInput(parts);
        }
        var arguments = Instruction.regMaybeImm(parts, parts.length - 1, false);
        var regs = arguments.getValue0();
        return new Pop(cond, regs);
    };
}