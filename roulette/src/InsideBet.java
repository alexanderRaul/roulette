public class InsideBet extends Bet {
    public InsideBet(BetType type) {
        super(type, 0.5);
    }

    @Override
    public int processBetPayment() {
        return switch (getType()) {
            case STRAIGHT_UP -> 35;
            case SPLIT -> 17;
            case STREET -> 11;
            case CORNER -> 8;
        };
    }
}