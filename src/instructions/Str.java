package instructions;

import Utils.TriFunction;
import computer.Computer;
import computer.Conditional;
import computer.Reg;

import java.util.Arrays;
import java.util.stream.Stream;

public class Str extends MemoryInstruction {
    public Str(Conditional cond, Reg primary, Reg pointer, int offset, int shift) {
        super(cond, primary, pointer, offset, shift);
    }

    public Str(Conditional cond, Reg primary, Reg pointer, Reg offsetReg, int shift) {
        super(cond, primary, pointer, offsetReg, shift);
    }

    @Override
    protected int doWork(Computer computer) throws Exception {
        var primary = computer.registers[this.primary.num];
        var pointer = computer.registers[this.pointer.num];
        var offset = this.getOffset(computer);
        var address = pointer.get() + offset;
        computer.memory.write(address, Instruction.intToData(primary.get()));
        return 0;
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> {
        int shiftAmount = 0;
        if(parts.length == 6 && parts[4].equals("lsl")) {
            shiftAmount = Instruction.getImmediate(parts[5]).get();
            parts = Arrays.copyOfRange(parts, 0, 4);
        }
        if(parts.length == 3) {
            parts =  Stream.concat(Arrays.stream(parts), Stream.of("0")).toArray(String[]::new);
        }
        var arguments = Instruction.regMaybeImm(parts, 3, true);
        var regs = arguments.getValue0();
        if(arguments.getValue1().isPresent()) {
            return new Str(cond, regs[0], regs[1], arguments.getValue1().get(), shiftAmount);
        }
        return new Str(cond, regs[0], regs[1], regs[2], shiftAmount);
    };
}
