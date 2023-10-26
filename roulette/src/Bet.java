public abstract class Bet {
    private final BetType type;
    private final double probability;

    public Bet(BetType type, double probability) {
        this.type = type;

        this.probability = probability;
    }

    public BetType getType() {
        return type;
    }

    public double getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "type='" + type + '\'' +
                ", probability=" + probability +
                '}';
    }

    public abstract int processBetPayment();
}
