package computer;

import instructions.Instruction;
import memory.MemoryController;

public class Computer {
    public final Register[] registers = new Register[16];
    public final MemoryController memory = new MemoryController();
    public final Cpsr cpsr = new Cpsr();
    public int instructionCount = 0;
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
        instructionCount++;
        nextInstruction = (Instruction) memory.read(pc.get(), 4);
    }

    public void printState() {
        var out = "";
        for(int i = 0; i < registers.length; i++) {
            out += "Register r" + ( i ) + ":" + registers[i].get() + "\n";
        }
        System.out.println(out);
        System.out.println("executed " + instructionCount + " instructions");
    }

    public boolean shouldBreak() {
        return this.nextInstruction.isBreakPoint();
    }

}
