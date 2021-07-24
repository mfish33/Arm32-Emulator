package instructions;

import computer.Conditional;

public abstract class LabelReplacementInstruction extends Instruction implements LabelReplacement {
    protected int numericalValue = 0;
    protected String label;

    LabelReplacementInstruction(Conditional cond, String label) {
        super(cond, false);
        this.label = label;
    }

    public void setNumericalValue(int numericalValue) {
        this.numericalValue = numericalValue;
    }

    public String getLabel() {
        return label;
    }
}
