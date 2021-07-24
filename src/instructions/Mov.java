package instructions;

import Utils.TriFunction;
import computer.Computer;
import computer.Conditional;
import computer.Reg;

public class Mov extends Instruction {

    private Reg dest;
    private Reg src;
    private int imm = 0;

    public Mov(Conditional cond, boolean setCpsr, Reg dest, Reg src) {
        super(cond, setCpsr);
        this.dest = dest;
        this.src = src;
    }

    public Mov(Conditional cond, boolean setCpsr, Reg dest, int imm) {
        super(cond, setCpsr);
        this.dest = dest;
        this.imm = imm;
    }

    @Override
    protected int doWork(Computer computer) {
        var dest = computer.registers[this.dest.num];
        var srcVal = src == null ? imm : computer.registers[src.num].get();
        dest.set(srcVal);
        return srcVal;
    }


    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> {
        var arguments = Instruction.regMaybeImm(parts, 2, true);
        var regs = arguments.getValue0();
        if(arguments.getValue1().isPresent()) {
            return new Mov(cond, setCpsr, regs[0], arguments.getValue1().get());
        }
        return new Mov(cond, setCpsr, regs[0], regs[1]);
    };

}
