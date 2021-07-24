package memory;

public interface Memory {
    Object read(int location, int size) throws MemoryAccessException;
    void write(int location, byte[] data) throws MemoryAccessException;
}
