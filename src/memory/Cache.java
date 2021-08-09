package memory;

import computer.Statistics;

import java.util.Arrays;

public class Cache {
    public static final int CacheSize = 256;
    private static final int idxAmount = log2(CacheSize / CacheChunk.CacheChunkSize);
    private static final int offsetAmount = log2(CacheChunk.CacheChunkSize);
    private static final int tagAmount = 32 - idxAmount - offsetAmount;
    private CacheChunk[] buckets = new CacheChunk[CacheSize / CacheChunk.CacheChunkSize];
    private final Statistics statistics = Statistics.getInstance();

    public static int getTag(int address) {
        return address >>> (idxAmount + offsetAmount);
    }

    public static int getIdx(int address) {
        return address << tagAmount >>> (tagAmount + offsetAmount);
    }

    public static int getOffset(int address) {
        return  address << (tagAmount + idxAmount) >>> (tagAmount + idxAmount);
    }

    public static int log2(int N) {
        return (int)(Math.log(N) / Math.log(2));
    }

    // I am assuming in this cache that nobody will be reading or writing across chunk boundary's

    public Object read(int location, int size, Chunk memory) throws MemoryAccessException {
        statistics.memRead();
        var chunk = getCacheChunk(location, memory);
        var chunkOffset = getOffset(location);
        var chunkData = chunk.data();
        return Arrays.copyOfRange(chunkData, chunkOffset, chunkOffset + size);
    }

    public void write(int location, byte[] data, Chunk memory) throws MemoryAccessException {
        statistics.memWrite();
        var chunk = getCacheChunk(location, memory);
        var chunkOffset = getOffset(location);
        var chunkData = chunk.data();
        System.arraycopy(data, 0, chunkData, chunkOffset, data.length);
        memory.write(location, data);
    }

    private CacheChunk getCacheChunk(int address, Chunk memory) throws MemoryAccessException {
        var idx = getIdx(address);
        var chunk = buckets[idx];
        if(chunk == null || chunk.tag() != getTag(address)) {
            loadCacheLine(address, memory);
            chunk = buckets[idx];
            statistics.cacheMiss();
        } else {
            statistics.cacheHit();
        }
        return chunk;
    }

    private void loadCacheLine(int address, Chunk memory) throws MemoryAccessException {
        var blockOffset = getOffset(address);
        var blockBase = address ^ blockOffset;
        var cacheLine = (byte[]) memory.read(blockBase, CacheChunk.CacheChunkSize);
        var chunk = new CacheChunk(cacheLine, getTag(address));
        var idx = getIdx(address);
        buckets[idx] = chunk;
    }
}
