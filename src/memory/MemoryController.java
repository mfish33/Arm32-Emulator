package memory;

import instructions.Instruction;

import java.util.ArrayList;

public class MemoryController implements Memory {

    private final ArrayList<Chunk> chunks = new ArrayList<>();


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
        var start = Integer.MAX_VALUE;
        for(var chunk: chunks) {
            start -= chunk.size();
        }
        var newChunk = new ProgramMemory(start - size, size, this);
        chunks.add(newChunk);
        return start;
    }


    private Chunk getChunkForLocation(int location) throws MemoryAccessException {
        for(var chunk: chunks) {
            var chunkBaseAddress = chunk.getBaseAddress();
            var size = chunk.size();
            if((location >= chunkBaseAddress && location <= chunkBaseAddress + chunk.size()) ||( location <= chunkBaseAddress && location >= chunkBaseAddress - chunk.size())) {
                return chunk;
            }
        }
        throw new MemoryAccessException("Could not find chunk for specified location");
    }

    @Override
    public Object read(int location, int size) throws MemoryAccessException {
        var chunk = getChunkForLocation(location);
        return chunk.read(location, size);
    }

    @Override
    public void write(int location, byte[] data) throws MemoryAccessException {
        var chunk = getChunkForLocation(location);
        chunk.write(location, data);
    }
}
