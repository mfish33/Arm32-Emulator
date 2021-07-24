package instructions;

import Utils.TriFunction;
import computer.Computer;
import computer.Conditional;
import computer.Reg;

public class Bx extends Instruction {
    private Reg target;

    public Bx(Conditional cond, Reg target) {
        super(cond, false);
        this.target = target;
    }

    @Override
    protected int doWork(Computer computer) {
        var pc = computer.registers[Reg.pc.num];
        var target = computer.registers[this.target.num];
        pc.set(target.get());
        return 0;
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> {
        if(parts.length != 2 || setCpsr) {
            throw Instruction.badInput(parts);
        }
        var target = Reg.fromStr(parts[1]);
        if(target.isEmpty()) {
            throw Instruction.badInput(parts);
        }
        return new Bx(cond, target.get());
    };
}
