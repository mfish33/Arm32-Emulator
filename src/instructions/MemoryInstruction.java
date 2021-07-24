package instructions;

import Parser.InvalidInputException;
import computer.Computer;
import computer.Conditional;
import computer.Reg;
import org.javatuples.Triplet;

import java.util.Optional;

public abstract class MemoryInstruction extends Instruction {

    protected final Reg primary;
    protected final Reg pointer;
    private Optional<Integer> offset = Optional.empty();
    private Reg offsetReg = null;
    protected int shift = 0;

    public MemoryInstruction(Conditional cond, Reg primary, Reg pointer, int offset) {
        super(cond, false);
        this.primary = primary;
        this.pointer = pointer;
        this.offset = Optional.of(offset);
    }

    public MemoryInstruction(Conditional cond, Reg primary, Reg pointer, Reg offsetReg) {
        super(cond, false);
        this.primary = primary;
        this.pointer = pointer;
        this.offsetReg = offsetReg;
    }

    public MemoryInstruction(Conditional cond, Reg primary, Reg pointer, int offset, int shift) {
        super(cond, false);
        this.primary = primary;
        this.pointer = pointer;
        this.offset = Optional.of(offset);
        this.shift = shift;
    }

    public MemoryInstruction(Conditional cond, Reg primary, Reg pointer, Reg offsetReg, int shift) {
        super(cond, false);
        this.primary = primary;
        this.pointer = pointer;
        this.offsetReg = offsetReg;
        this.shift = shift;
    }

    public int getOffset(Computer computer) {
        return doShift(
                this.offset.orElseGet(() -> computer.registers[offsetReg.num].get())
        );
    }

    private int doShift(int val) {
        if(this.shift > 0) {
            return val << this.shift;
        }
        return val;
    }

}
