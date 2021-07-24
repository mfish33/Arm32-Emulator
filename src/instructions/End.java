package instructions;

import Utils.TriFunction;
import computer.Computer;
import computer.Conditional;

public class End extends Instruction {

    public End() {
        super(Conditional.al, false);
    }

    @Override
    protected int doWork(Computer computer) {
        System.out.println("Execution has reached an end instruction");
        computer.printState();
        System.exit(0);
        return 0;
    }

    public static TriFunction<Conditional, Boolean, String[], Instruction> create = (cond, setCpsr, parts) -> new End();
}
