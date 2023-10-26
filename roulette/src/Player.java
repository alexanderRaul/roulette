public class Player {

    private final String name;
    private final int[] tokens;
    private double funds;

    public Player(String name, double funds) {
        this.name = name;
        this.funds = funds;

        // [0] = bl|a|ck, [1] = re|d|, [2] = gr|e|en
        this.tokens = new int[3];
    }

    public void receiveTokens(TableEntry entry) {
        final Bet bet = entry.getBet();
        final Token token = entry.getToken();
        final int tokenColorIndex = getTokenColorIndex(token);

        tokens[tokenColorIndex] += bet.processBetPayment();
    }

    private static int getTokenColorIndex(Token token) {
        final String tokenColor = token.getColor();
        return (tokenColor.charAt(2) - 'a') / 3 + (tokenColor.charAt(2) - 'a') % 3;
    }

    public void exchangeFundsForTokens(Token token, int quantity) throws InterruptedException {
        final double tokenCost = token.getValue();
        final double totalCost = tokenCost * quantity;
        final int tokenColorIndex = getTokenColorIndex(token);

        if (funds >= totalCost) {
            funds -= totalCost;
            tokens[tokenColorIndex] += quantity;
            System.out.println("\n" + name + " exchanged " + totalCost + " funds for " + quantity + " " + token.getColor() + " tokens.");
        } else {
            Thread.sleep(500);
            System.out.println("Insufficient funds to exchange for tokens.");
        }
    }

    public boolean placeBet(TableEntry[] table, int position, TableEntry entry) {
        final Token token = entry.getToken();
        final int tokenColorIndex = getTokenColorIndex(token);

        if (tokens[tokenColorIndex] > 0) {
            table[position] = entry;
            tokens[tokenColorIndex]--;
            System.out.println("bet made");
            return true;
        }

        System.out.println("\n" + token.getColor() + " insufficient to place a bet");
        return false;
    }

    public String displayTokens() {
        return "Tokens [" + tokens[0] + " black, " + tokens[1] + " red, " + tokens[2] + " green]";
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", funds=" + funds +
                ", " + displayTokens() +
                '}';
    }
}