public class TableEntry {
    private final Token token;
    private final Bet bet;

    public TableEntry(Token token, Bet bet) {
        this.token = token;
        this.bet = bet;
    }

    public Token getToken() {
        return token;
    }

    public Bet getBet() {
        return bet;
    }

    @Override
    public String toString() {
        return "TableEntry{" +
                "token=" + token +
                ", bet=" + bet +
                '}';
    }
}