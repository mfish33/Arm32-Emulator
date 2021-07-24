package instructions;
import Parser.InvalidInputException;
import computer.Computer;
import computer.Conditional;
import computer.Reg;
import org.javatuples.Pair;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public abstract class Instruction {

    protected Conditional conditional;
    protected boolean setCpsr;
    private boolean breakpoint = false;

    public final void execute(Computer computer) throws Exception {
        if(!computer.cpsr.evaluateCondition(conditional)) {
            return;
        }
        if(!setCpsr) {
           doWork(computer);
           return;
        }
        computer.cpsr.reset();
        var result = doWork(computer);
        computer.cpsr.setSigns(result);
    }

    protected abstract int doWork(Computer computer) throws Exception;

    Instruction(Conditional conditional, boolean setCpsr) {
        this.conditional = conditional;
        this.setCpsr = setCpsr;
    }

    public static byte[] intToData(int myInteger){
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(myInteger).array();
    }

    public static int dataToInt(byte [] byteBarray){
        return ByteBuffer.wrap(byteBarray).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static InvalidInputException badInput(String[] parts) {
        var msg = Arrays.stream(parts).reduce((acc, p) -> acc + " " + p);
        return new InvalidInputException("While creating instruction received bad input: " + msg.get());
    }

    public static Optional<Integer> getImmediate(String s) {
        try {
            var result = Integer.valueOf(s);
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Pair<Reg[], Optional<Integer>> regMaybeImm(String[] parts, int numberOfArguments, boolean immAllowed) throws InvalidInputException {
        if(parts.length != numberOfArguments + 1) {
            throw Instruction.badInput(parts);
        }
        var regs = new ArrayList<Reg>();
        for(int i = 1; i < parts.length; i++) {
            var maybeReg = Reg.fromStr(parts[i]);
            maybeReg.ifPresent(regs::add);
        }
        if(regs.size() == numberOfArguments) {
            return new Pair<>(regs.toArray(Reg[]::new),Optional.empty());
        }
        if(immAllowed && regs.size() == numberOfArguments - 1) {
            var maybeImm = Instruction.getImmediate(parts[parts.length -1]);
            if(maybeImm.isPresent()) {
                return new Pair<>(regs.toArray(Reg[]::new),Optional.of(maybeImm.get()));
            }
        }
        throw Instruction.badInput(parts);
    }

    public boolean isBreakPoint() {
        return this.breakpoint;
    }

    public void setBreakpoint() {
        this.breakpoint = true;
    }

}
