package memory;

import instructions.Instruction;

import java.util.ArrayList;

public class MemoryController implements Memory {

    private final ArrayList<Chunk> chunks = new ArrayList<>();
    private Cache cache = new Cache();

    public int createInstructionMemory(Instruction[] instructions) {
        var start = Integer.MIN_VALUE;
        for(var chunk: chunks) {
            start += chunk.size();
        }
        var newChunk = new InstructionMemory(start, instructions);
        chunks.add(newChunk);
        return start;
    }

    public int createProgramMemory(int size) {
        var start = Integer.MAX_VALUE - 3;
        for(var chunk: chunks) {
            start -= chunk.size();
        }
        // Since we never create more than one program memory we will attach the cache here
        var newChunk = new ProgramMemory(start - size, size, this);
        chunks.add(newChunk);
        return start;
    }


    private Chunk getChunkForLocation(int location) throws MemoryAccessException {
        for(var chunk: chunks) {
            var chunkBaseAddress = chunk.getBaseAddress();
            var size = chunk.size();
            if((location >= chunkBaseAddress && location <= chunkBaseAddress + size) ||( location <= chunkBaseAddress && location >= chunkBaseAddress - size)) {
                return chunk;
            }
        }
        throw new MemoryAccessException("Could not find chunk for specified location");
    }

    @Override
    public Object read(int location, int size) throws MemoryAccessException {
        var chunk = getChunkForLocation(location);
        if(chunk instanceof ProgramMemory) {
            return cache.read(location, size, chunk);
        }
        return chunk.read(location, size);
    }

    @Override
    public void write(int location, byte[] data) throws MemoryAccessException {
        var chunk = getChunkForLocation(location);
        if(chunk instanceof ProgramMemory) {
            cache.write(location, data, chunk);
        } else {
            chunk.write(location, data);
        }
    }
}
