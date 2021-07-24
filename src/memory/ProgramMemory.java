package memory;

import java.util.Arrays;

public class ProgramMemory extends Chunk {
    private final byte[] rawMemory;
    private final MemoryController memoryController;

    ProgramMemory(int baseAddress, int size, MemoryController memoryController) {
        this.baseAddress = baseAddress;
        this.rawMemory = new byte[size];
        this.memoryController = memoryController;
    }

    @Override
    public byte[] read(int location, int size)  throws MemoryAccessException {
        int accessPoint = location - baseAddress;
        if(accessPoint % 4 != 0 && size != 1) {
            throw new MemoryAccessException("Reading into unaligned instruction data");
        }
        return Arrays.copyOfRange(rawMemory, accessPoint, accessPoint + size);
    }

    @Override
    public void write(int location, byte[] data) throws MemoryAccessException {
        int accessPoint = location - baseAddress;
        System.arraycopy(data, 0, rawMemory, accessPoint, data.length);
    }

    @Override
    public int size() {
        return rawMemory.length;
    }
}
