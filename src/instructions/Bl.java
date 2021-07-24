package instructions;

import Parser.Parser;
import Utils.TriFunction;
import computer.Computer;
import computer.Conditional;
import computer.Reg;

public class Bl extends LabelReplacementInstruction {

    public Bl(Conditional cond, String label) {
        super(cond, label);
    }

    @Override
    protected int doWork(Computer computer) throws Exception {
        var pc = computer.registers[Reg.pc.num];
        var current_pc = pc.get();
        pc.set(current_pc + numericalValue);
        computer.registers[Reg.lr.num].set(current_pc);
        return 0;
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> {
        if(parts.length != 2 || setCpsr) {
            throw Instruction.badInput(parts);
        }
        if(Parser.isLabel(parts[1])) {
            return new Bl(cond, parts[1]);
        }
        throw Instruction.badInput(parts);
    };
}
