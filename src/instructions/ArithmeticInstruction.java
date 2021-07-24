package instructions;

import computer.Computer;
import computer.Conditional;
import computer.Reg;
import computer.Register;

import java.util.function.BiFunction;

public abstract class ArithmeticInstruction extends Instruction {
    public Reg target;
    public Reg op1;

    // Can be null
    public Reg op2;
    public int imm = 0;
    BiFunction<Integer,Integer,Integer> safeOperation;
    BiFunction<Integer,Integer,Integer> unsafeOperation;
    public int shift = 0;

    private final static ArithmeticException overFlowError = new ArithmeticException("integer overflow");

    ArithmeticInstruction(Conditional conditional, boolean setCpsr, Reg target, Reg op1, Reg op2, BiFunction<Integer,Integer,Integer> safeOperation, BiFunction<Integer,Integer,Integer> unsafeOperation, int shift) {
        super(conditional, setCpsr);
        this.target = target;
        this.op1 = op1;
        this.op2 = op2;
        this.safeOperation = safeOperation;
        this.unsafeOperation = unsafeOperation;
        this.shift = shift;
    }

    ArithmeticInstruction(Conditional conditional, boolean setCpsr, Reg target, Reg op1, int imm, BiFunction<Integer,Integer,Integer> safeOperation, BiFunction<Integer,Integer,Integer> unsafeOperation, int shift) {
        super(conditional, setCpsr);
        this.target = target;
        this.op1 = op1;
        this.imm = imm;
        this.safeOperation = safeOperation;
        this.unsafeOperation = unsafeOperation;
        this.shift = shift;
    }

    ArithmeticInstruction(Conditional conditional, boolean setCpsr, Reg target, Reg op1, Reg op2, BiFunction<Integer,Integer,Integer> safeOperation, BiFunction<Integer,Integer,Integer> unsafeOperation) {
        super(conditional, setCpsr);
        this.target = target;
        this.op1 = op1;
        this.op2 = op2;
        this.safeOperation = safeOperation;
        this.unsafeOperation = unsafeOperation;
    }

    ArithmeticInstruction(Conditional conditional, boolean setCpsr, Reg target, Reg op1, int imm, BiFunction<Integer,Integer,Integer> safeOperation, BiFunction<Integer,Integer,Integer> unsafeOperation) {
        super(conditional, setCpsr);
        this.target = target;
        this.op1 = op1;
        this.imm = imm;
        this.safeOperation = safeOperation;
        this.unsafeOperation = unsafeOperation;
    }

    @Override
    protected int doWork(Computer computer) {
        var target = this.target != Reg.INVALID ? computer.registers[this.target.num] : new Register();
        var op1 = computer.registers[this.op1.num];
        try {
            var result = safeOperation.apply(op1.get(),this.getLastVal(computer));
            target.set(result);
            return  doShift(result);
        } catch (ArithmeticException e) {
            if(setCpsr) {
                if(e.equals(overFlowError)) {
                    computer.cpsr.setO(true);
                } else {
                    computer.cpsr.setC(true);
                }
            }
            var result = unsafeOperation.apply(op1.get(),this.getLastVal(computer));
            target.set(result);
            return  result;
        }
    }

    private int doShift(int val) {
        if(this.shift > 0) {
            return val << this.shift;
        }
        return val;
    }

    protected final int getLastVal(Computer computer) {
        if(op2 != null) {
            return doShift(computer.registers[op2.num].get());
        } else {
            return doShift(imm);
        }
    }

}
