package instructions;

import computer.Computer;
import computer.Conditional;
import computer.Reg;

public class LdrLabel extends  LabelReplacementInstruction{
    private final Reg primary;

    LdrLabel(Conditional cond, Reg primary, String label) {
        super(cond, label);
        this.primary = primary;
    }

    @Override
    protected int doWork(Computer computer) throws Exception {
        var primary = computer.registers[this.primary.num];
        var pointer = computer.registers[Reg.pc.num];
        var offset = numericalValue;
        var address = pointer.get() + offset;
        var memoryRead = (byte[]) computer.memory.read(address, 4);
        primary.set(Instruction.dataToInt(memoryRead) + address  + 4);
        return 0;
    }
}
