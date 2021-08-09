package memory;

public record CacheChunk(
        byte[] data,
        int tag
) {
    public final static int CacheChunkSize = 8;
}
