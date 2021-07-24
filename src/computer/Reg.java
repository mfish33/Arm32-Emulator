package computer;

import javax.swing.text.AttributeSet;
import javax.swing.text.html.Option;
import java.util.Optional;

public enum Reg {
    r0(0),
    r1(1),
    r2(2),
    r3(3),
    r4(4),
    r5(5),
    r6(6),
    r7(7),
    r8(8),
    r9(9),
    r10(10),
    r11(11),
    r12(12),
    r13(13),
    r14(14),
    r15(15),
    ip(12),
    sp(13),
    lr(14),
    pc(15),
    INVALID(-1);
    public final int num;
    Reg(int num) {
        this.num = num;
    }
    public static Optional<Reg> fromStr(String s) {
        try {
            var reg = Reg.valueOf(s);
            return Optional.of(reg);
        }catch (Exception e) {
            return Optional.empty();
        }
    }
}
