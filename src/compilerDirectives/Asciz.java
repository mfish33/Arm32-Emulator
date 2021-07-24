package compilerDirectives;

import Utils.TriConsumer;
import instructions.ConstantAlias;
import instructions.ConstantAliasPack;
import instructions.Instruction;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;


public class Asciz {
    public static TriConsumer<String[], String, ArrayList<Instruction>> create = (parts, line, instructionList) -> {
        var match = Pattern.compile("\"(.+)\"").matcher(line);
        match.find();
        var bytes = match.group(1).getBytes(StandardCharsets.UTF_8);
        byte[] string= new byte[bytes.length+1];
        System.arraycopy(bytes, 0, string, 0, bytes.length);
        var lastInstruction = instructionList.get(instructionList.size() - 1);

        if(lastInstruction instanceof ConstantAliasPack packInstruction) {
            for(int i = 1; i < packInstruction.data.length - 1; i++) {
                if(packInstruction.data[i] == 0) {
                    var rest = Arrays.copyOfRange(string,0,packInstruction.data.length - i - 1);
                    System.arraycopy(rest, 0, packInstruction.data, i + 1, rest.length);
                    string = Arrays.copyOfRange(string,packInstruction.data.length - i - 1, string.length);
                    break;
                }
            }
        }

        for(var chunk: splitArray(string, 4)) {
            if(chunk.length == 4) {
                instructionList.add(new ConstantAliasPack(chunk));
                continue;
            }
            var rest = new byte[4];
            System.arraycopy(chunk, 0, rest, 0, chunk.length);
            instructionList.add(new ConstantAliasPack(rest));
        }
    };

    // Code to chunk array
    // Taken from https://stackoverflow.com/questions/27857011/how-to-split-a-string-array-into-small-chunk-arrays-in-java
    public static byte[][] splitArray(byte[] arrayToSplit, int chunkSize){
        if(chunkSize<=0){
            return null;  // just in case :)
        }
        // first we have to check if the array can be split in multiple
        // arrays of equal 'chunk' size
        int rest = arrayToSplit.length % chunkSize;  // if rest>0 then our last array will have less elements than the others
        // then we check in how many arrays we can split our input array
        int chunks = arrayToSplit.length / chunkSize + (rest > 0 ? 1 : 0); // we may have to add an additional array for the 'rest'
        // now we know how many arrays we need and create our result array
        var arrays = new byte[chunks][];
        // we create our resulting arrays by copying the corresponding
        // part from the input array. If we have a rest (rest>0), then
        // the last array will have less elements than the others. This
        // needs to be handled separately, so we iterate 1 times less.
        for(int i = 0; i < (rest > 0 ? chunks - 1 : chunks); i++){
            // this copies 'chunk' times 'chunkSize' elements into a new array
            arrays[i] = Arrays.copyOfRange(arrayToSplit, i * chunkSize, i * chunkSize + chunkSize);
        }
        if(rest > 0){ // only when we have a rest
            // we copy the remaining elements into the last chunk
            arrays[chunks - 1] = Arrays.copyOfRange(arrayToSplit, (chunks - 1) * chunkSize, (chunks - 1) * chunkSize + rest);
        }
        return arrays; // that's it
    }
}
