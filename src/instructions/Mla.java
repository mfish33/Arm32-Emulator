package instructions;

import Utils.TriFunction;
import computer.Computer;
import computer.Conditional;
import computer.Reg;

public class Mla extends Instruction{

    private final Reg target;
    private final Reg multiply1;
    private final Reg multiply2;
    private final Reg add;


    public Mla(Conditional cond, boolean setCpsr, Reg[] regs) {
        super(cond, setCpsr);
        target = regs[0];
        multiply1 = regs[1];
        multiply2 = regs[2];
        add = regs[3];
    }

    @Override
    protected int doWork(Computer computer) throws Exception {
        var target = computer.registers[this.target.num];
        var multiply1 = computer.registers[this.multiply1.num];
        var multiply2 = computer.registers[this.multiply2.num];
        var add = computer.registers[this.add.num];
        var result = (int)((long) multiply1.get() * (long) multiply2.get() + (long) add.get());
        target.set(result);
        return result;
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> {
        var arguments = Instruction.regMaybeImm(parts, 4, false);
        return new Mla(cond, setCpsr, arguments.getValue0());
    };
}
