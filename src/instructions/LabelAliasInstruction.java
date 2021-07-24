package instructions;

import computer.Computer;
import computer.Conditional;

public class LabelAliasInstruction extends LabelReplacementInstruction implements AliasInstruction{

    public LabelAliasInstruction(String label) {
        super(Conditional.al, label);
    }

    @Override
    protected int doWork(Computer computer) throws Exception {
        throw new UnsupportedOperationException("Not a valid instruction. Created in assembly directive");
    }

    @Override
    public byte[] alias() {
        return Instruction.intToData(numericalValue);
    }
}
