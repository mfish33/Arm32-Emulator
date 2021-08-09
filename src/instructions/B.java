package instructions;

import Parser.Parser;
import Utils.TriFunction;
import computer.Computer;
import computer.Conditional;
import computer.Reg;
import computer.Statistics;

public class B extends LabelReplacementInstruction {

    public B(Conditional cond, String label) {
        super(cond, label);
    }

    @Override
    protected int doWork(Computer computer) throws Exception {
        var pc = computer.registers[Reg.pc.num];
        pc.set(pc.get() + numericalValue);
        return 0;
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> {
        if(parts.length != 2 || setCpsr) {
            throw Instruction.badInput(parts);
        }
        if(Parser.isLabel(parts[1])) {
            return new B(cond, parts[1]);
        }
        throw Instruction.badInput(parts);
    };

    @Override
    protected void instructionExecutedStatistics(Statistics statistics) {
        if(conditional == Conditional.al) {
            return;
        }
        if(numericalValue > 0) {
            statistics.branchForward();
        } else {
            statistics.branchBackwards();
        }
    }

    @Override
    protected void instructionNotExecutedStatistics(Statistics statistics) {
        if(numericalValue > 0) {
            statistics.branchForwardsSkipped();
        } else {
            statistics.branchBackwardsSkipped();
        }
    }
}
