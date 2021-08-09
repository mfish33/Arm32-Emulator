package computer;

import instructions.Instruction;
import memory.MemoryController;

public class Computer {
    public final Register[] registers = new Register[16];
    public final MemoryController memory = new MemoryController();
    public final Cpsr cpsr = new Cpsr();
    public final Statistics statistics = Statistics.getInstance();
    private Instruction nextInstruction;

    public Computer() {
        for(int i = 0; i < registers.length; i++) {
            registers[i] = new Register();
        }
    }

    public void loadProgram(Instruction[] instructions, int pcStartOffset) throws Exception {
        int instructionStart = memory.createInstructionMemory(instructions);
        int spStart = memory.createProgramMemory(50000);
        var pc = registers[Reg.pc.num];
        pc.set(instructionStart + pcStartOffset);
        nextInstruction = (Instruction) memory.read(pc.get(), 4);

        registers[Reg.sp.num].set(spStart);
    }

    public void cycle() throws Exception {
        var pc = registers[Reg.pc.num];
        // Increment PC
        pc.increment(4);
        // Execute instruction
        nextInstruction.execute(this);
        statistics.instructionExecuted();
        nextInstruction = (Instruction) memory.read(pc.get(), 4);
    }

    public void printState() {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < registers.length; i++) {
            out.append("Register r").append(i).append(":").append(registers[i].get()).append("\n");
        }
        System.out.println(out);
        statistics.print();
    }

    public boolean shouldBreak() {
        return this.nextInstruction.isBreakPoint();
    }

}
