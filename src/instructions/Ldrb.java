package instructions;

import Utils.TriFunction;
import computer.Computer;
import computer.Conditional;
import computer.Reg;

import java.util.Arrays;
import java.util.stream.Stream;

public class Ldrb extends MemoryInstruction{
    public Ldrb(Conditional cond, Reg primary, Reg pointer, int offset) {
        super(cond, primary, pointer, offset);
    }

    public Ldrb(Conditional cond, Reg primary, Reg pointer, Reg offsetReg) {
        super(cond, primary, pointer, offsetReg);
    }

    @Override
    protected int doWork(Computer computer) throws Exception {
        var primary = computer.registers[this.primary.num];
        var pointer = computer.registers[this.pointer.num];
        var offset = this.getOffset(computer);
        var address = pointer.get() + offset;
        var memoryRead = (byte[]) computer.memory.read(address + 3, 1);
        primary.set(memoryRead[0]);
        return 0;
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> {
        if(parts.length == 3) {
            parts =  Stream.concat(Arrays.stream(parts), Stream.of("0")).toArray(String[]::new);
        }
        var arguments = Instruction.regMaybeImm(parts, 3, true);
        var regs = arguments.getValue0();
        if(arguments.getValue1().isPresent()) {
            return new Ldrb(cond, regs[0], regs[1], arguments.getValue1().get());
        }
        return new Ldrb(cond, regs[0], regs[1], regs[2]);
    };

}
