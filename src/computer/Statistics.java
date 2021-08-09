package computer;

public class Statistics {
    private final long creationTime = System.nanoTime();
    private int instructionCount = 0;
    private double cacheHits = 0;
    private double cacheMisses = 0;
    private int memReads = 0;
    private int memWrites = 0;
    private int branchForwardTaken = 0;
    private int branchBackwardsTaken = 0;
    private int branchForwardsNotTaken = 0;
    private int branchBackwardsNotTaken = 0;

    private static final Statistics instance = new Statistics();

    private Statistics() {}

    public static Statistics getInstance() {
        return instance;
    }

    public void cacheHit() {
        cacheHits++;
    }

    public void cacheMiss() {
        cacheMisses++;
    }

    public void memRead() {
        memReads++;
    }

    public void memWrite() {
        memWrites++;
    }

    public void branchForward() {
        branchForwardTaken++;
    }

    public void branchBackwards() {
        branchBackwardsTaken++;
    }

    public void branchForwardsSkipped() {
        branchForwardsNotTaken++;
    }

    public void branchBackwardsSkipped() {
        branchBackwardsNotTaken++;
    }

    public void instructionExecuted() { instructionCount++; }

    public void print() {
        System.out.println("--------------------------------");
        System.out.println("Statistics");
        var now = System.nanoTime();
        var elapsedMs = (double) ((now - creationTime) / 1_000_000);
        System.out.println("executed " + instructionCount + " instructions in " + elapsedMs + " milliseconds");
        System.out.printf("Performance was on average %.4f Mhz\n", (instructionCount / (elapsedMs / 1000)) / 1_000_000);
        System.out.println("Number of cache hits: " + (int) cacheHits);
        System.out.println("Number of cache misses: " + (int) cacheMisses);
        System.out.println("Cache hit percentage: " + cacheHits / (cacheHits + cacheMisses) * 100);
        System.out.println("Number of memory reads: " + memReads);
        System.out.println("Number of memory writes: " + memWrites);
        System.out.println("Number of branches forwards: " + branchForwardTaken);
        System.out.println("Number of branches backwards: " + branchBackwardsTaken);
        System.out.println("Number of branches forwards not taken: " + branchForwardsNotTaken);
        System.out.println("Number of branches backwards not taken: " + branchBackwardsNotTaken);
    }
}
