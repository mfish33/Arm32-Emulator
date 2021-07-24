package computer;

import java.util.Optional;

public enum Conditional {
    eq,
    ne,
    gt,
    lt,
    ge,
    le,
    cs,
    cc,
    lo,
    mi,
    pl,
    al,
    nv,
    vs,
    vc,
    hi,
    ls;

    public static Optional<Conditional> fromStr(String s) {
        try {
            var result = Conditional.valueOf(s);
            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
