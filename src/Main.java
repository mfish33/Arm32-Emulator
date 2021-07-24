import Parser.Parser;
import computer.Computer;
import computer.Conditional;
import computer.Reg;
import computer.Register;
import instructions.*;
import org.apache.commons.cli.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        var cmd = parseInput(args);
        var inputFilePath = cmd.getOptionValue("input");

        var fileContents = Files.readString(Path.of(inputFilePath));
        var breakpoints = new ArrayList<Integer>();
        breakpoints.add(150);

        var instructions = Parser.parse(fileContents, breakpoints);
        var computer = new Computer();
        computer.loadProgram(instructions, 0);
        try {
            while (true) {
                if(computer.shouldBreak()) {
                    // Need something here to not get optimized out
                    var stop = 0;
                }
                computer.cycle();
            }
        } catch (Exception e) {
            System.out.println("System Exited with error");
            computer.printState();
            throw e;
        }

    }

    private static CommandLine parseInput(String[] args) {
        Options options = new Options();

        var input = new Option("f", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        var parser = new DefaultParser();
        var formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }
        return cmd;
    }
}