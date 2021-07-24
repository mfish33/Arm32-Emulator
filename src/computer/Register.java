package computer;

public class Register {
    private int value = 0;

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }

    public void increment(int amount) {
        this.value += amount;
    }

    public void decrement(int amount) {
        this.value -= amount;
    }

    public Register() {}

    public Register(int value) {
        this.value = value;
    }

}
