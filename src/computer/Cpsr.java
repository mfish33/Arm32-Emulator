package computer;

// Emulating arm cpsr register https://developer.arm.com/documentation/ddi0601/2021-03/AArch32-Registers/CPSR--Current-Program-Status-Register?lang=en#fieldset_0-28_28
public class Cpsr {
    private boolean z = false;
    private boolean n = false;
    private boolean c = false;
    private boolean v = false;


    public void setSigns(int number) {
        if(number == 0) {
            this.z = true;
        } else if(number < 0) {
            n = true;
        }
    }

    public void reset() {
        this.n = false;
        this.z = false;
        this.v = false;
        this.c = false;
    }


    public void setC(boolean c) {
        this.c = c;
    }


    public void setO(boolean o) {
        this.v = o;
    }

    public boolean evaluateCondition(Conditional cond) {
        return switch (cond) {
            case eq -> this.z;
            case ne -> !this.z;
            case gt -> !this.z && this.n == this.v;
            case lt -> this.n != this.v;
            case ge -> this.n == this.v;
            case le -> this.z || this.n != this.v;
            case cs -> this.c;
            case cc, lo -> !this.c;
            case mi -> this.n;
            case pl -> !this.n;
            case al -> true;
            case nv -> false;
            case vs -> this.v;
            case vc -> !this.v;
            case hi -> this.c && !this.z;
            case ls -> !this.c || !this.z;
        };
    }


}
