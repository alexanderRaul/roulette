public class Token {
    private final String color;
    private final int value;

    public Token(String color) {
        this.color = color;
        this.value = color.equals("red") ? 5 : (color.equals("green") ? 25 : 100);
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token [color=" + color + ", value=" + value + "]";
    }
}