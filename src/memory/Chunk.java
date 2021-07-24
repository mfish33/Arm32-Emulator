package memory;

public abstract class Chunk implements Memory {
    protected int baseAddress;
    public abstract int size();
    public int getBaseAddress() {
        return baseAddress;
    }
}
