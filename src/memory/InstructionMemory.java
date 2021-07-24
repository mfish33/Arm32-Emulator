package memory;
import instructions.AliasInstruction;
import instructions.Instruction;

public class InstructionMemory extends Chunk {

    private final Instruction[] instructions;

    InstructionMemory(int baseAddress, Instruction[] instructions) {
        this.baseAddress = baseAddress;
        this.instructions = instructions;
    }

    @Override
    public Object read(int location, int size)  throws MemoryAccessException{
        int accessPoint = location - baseAddress;
        // Support ldrb from instruction memory
        if(size == 1) {
            var instruction =  instructions[accessPoint / 4];
            if(instruction instanceof AliasInstruction) {
                var data = ((AliasInstruction) instruction).alias();
                var bite =  data[accessPoint % 4];
                return new byte[]{bite};
            }
        }
        if(size != 4 || accessPoint % 4 != 0) {
            throw new MemoryAccessException("Reading into unaligned instruction data");
        }
        var instruction =  instructions[accessPoint / 4];
        if(instruction instanceof AliasInstruction) {
            return ((AliasInstruction) instruction).alias();
        }
        return instruction;
    }

    @Override
    public void write(int location, byte[] data) throws MemoryAccessException {
        throw new MemoryAccessException("Trying to write to readonly memory");
    }

    @Override
    public int size() {
        return instructions.length * 4;
    }
}
