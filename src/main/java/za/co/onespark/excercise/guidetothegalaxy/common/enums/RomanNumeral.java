package za.co.onespark.excercise.guidetothegalaxy.common.enums;

public enum RomanNumeral {
    I(1, true),
    V(5, false),
    X(10, true),
    L(50, false),
    C(100, true),
    D(500, false),
    M(1000, true);

    RomanNumeral (int value, boolean isRepeatable){
        this.value = value;
        this.isRepeatable = isRepeatable;
    }

    private final int value;
    private final boolean isRepeatable;

    public int getValue() {
        return value;
    }

    public boolean isRepeatable() {
        return isRepeatable;
    }

    public boolean isSubtractableFrom(RomanNumeral otherNumeral) {
        if (otherNumeral == null || !this.isRepeatable()) {
            return false;
        }

        int thisNumeralPosition = this.ordinal();
        int otherNumeralPosition = otherNumeral.ordinal();

        return (thisNumeralPosition == otherNumeralPosition - 1 ||  thisNumeralPosition == otherNumeralPosition - 2);
    }
}
