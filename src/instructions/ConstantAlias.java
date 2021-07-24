package instructions;

import computer.Computer;
import computer.Conditional;

public class ConstantAlias extends Instruction implements AliasInstruction {
    public byte[] data;

    public ConstantAlias(byte[] data) throws InstructionCreationException {
        super(Conditional.al, false);
        if(data.length != 4) {
            throw new InstructionCreationException("Data for alias instruction is not correct size");
        }
        this.data = data;
    }

    public ConstantAlias(int data) {
        super(Conditional.al, false);
        this.data = Instruction.intToData(data);
    }

    @Override
    protected int doWork(Computer computer) throws Exception {
        throw new UnsupportedOperationException("Not a valid instruction. Created in assembly directive");
    }

    @Override
    public byte[] alias() {
        return data;
    }
}
