import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws InterruptedException {

        final Roulette roulette = new Roulette();
        final Table table = new Table();

        System.out.print("name: ");
        final String playerName = scanner.nextLine();
        final Player player = new Player(playerName, 100.0);

        String option;
        do {
            System.out.println("\n1: Exchange Funds For Tokens");
            System.out.println("2: Make a bet");
            System.out.println("3: Current status");
            System.out.println("4: Exit");
            System.out.print("Type number and <Enter>: ");

            option = scanner.nextLine();
            switch (option.toLowerCase()) {
                case "1" -> exchangeFundsForTokens(player);
                case "2" -> {
                    makeABet(player, table, roulette);
                    table.cleanTable();
                }
                case "3" -> System.out.println("\n" + player);
            }
        } while (!option.equals("4"));

        scanner.close();
    }

    private static void exchangeFundsForTokens(Player player) throws InterruptedException {
        System.out.println("\n1. red = $5.0");
        System.out.println("2. green = $25.0");
        System.out.println("3. black = $100.0");
        System.out.print("Enter colour and quantity (example: red 2 -> 2 red tokens $10.0), press <Enter> (default: red 1): ");

        String input = scanner.nextLine().trim();
        try {
            String[] parts = input.split("\\s+");

            String color = "red";
            int quantity = 1;

            if (parts.length > 0 && !parts[0].isEmpty()) {
                String inputColor = parts[0].toLowerCase();
                if (inputColor.equals("red") || inputColor.equals("green") || inputColor.equals("black")) {
                    color = inputColor;
                } else {
                    System.out.println("Invalid color. Default value will be used: red");
                }
            }

            if (parts.length > 1) {
                quantity = Integer.parseInt(parts[1]);
                if (quantity <= 0) {
                    quantity = 1;
                }
            }

            final Token token = new Token(color);
            player.exchangeFundsForTokens(token, quantity);
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Default value will be used: 1");
            final Token token = new Token("red");
            player.exchangeFundsForTokens(token, 1);
        }
    }

    private static void makeABet(Player player, Table table, Roulette roulette) throws InterruptedException {
        System.out.println("\n1. bet on a number (Straight Up) [high payout, low probability 1 / 37");
        System.out.println("2. bet on two numbers (Split) [probability 1 / 17");
        System.out.println("3. bet on three numbers (Street) [probability 1 / 11]");
        System.out.println("4. bet on four numbers (Corner) [low payout, probability 1 / 8]");
        System.out.print("Type number (default: 1) <Enter>: ");

        try {
            String optionBet = scanner.nextLine().trim();
            Bet bet = null;

            if (optionBet.isEmpty())
                optionBet = "1";

            switch (optionBet) {
                case "1" -> bet = new StraightUp();
                case "2" -> bet = new Split();
                case "3" -> bet = new Street();
                case "4" -> bet = new Corner();
                default -> new StraightUp();
            }

            System.out.println("\nwhich token colour you want to bet on: ");
            System.out.println("1. red");
            System.out.println("2. green");
            System.out.println("3. black");
            System.out.print("Type colour (default: red) <Enter>: ");

            String color = scanner.nextLine().trim();
            if (color.isEmpty())
                color = "red";
            else if (!isValidColor(color)) {
                System.out.println("Invalid color. Default value will be used: red.");
                color = "red";
            }

            final Token token = new Token(color);

            System.out.println("choose index 0 - 36");
            System.out.print("Type number (default: 7)<Enter>: ");

            int index = 7;
            try {
                String inputIndex = scanner.nextLine().trim();
                if (!inputIndex.isEmpty())
                    index = Integer.parseInt(inputIndex);
            } catch (NumberFormatException ignored) {}

            makeABetHelper(index, table.getTable(), new TableEntry(token, bet));

            if (!player.placeBet(table.getTable(), index, new TableEntry(token, bet)))
                return;

            table.displayFormattedTable();
            if (roulette.spin(table.getTable())) {
                player.receiveTokens(new TableEntry(token, bet));
                System.out.println("win");
            } else
                System.out.println("no win");


        } catch (InputMismatchException ignored) {
            System.out.println("Invalid number. Default value will be used: 7.");
        }
    }

    private static boolean isValidColor(String color) {
        return color.equalsIgnoreCase("red") || color.equalsIgnoreCase("green") || color.equalsIgnoreCase("black");
    }

    private static void makeABetHelper(int index, TableEntry[] table, TableEntry entry) {
        final BetType betType = entry.getBet().getType();

        table[index] = entry;

        switch (betType) {
            case SPLIT -> table[(index + 1) % 37] = entry;
            case STREET -> {
                table[index] = entry;
                table[(index + 1) % 37] = entry;
                table[(index + 2) % 37] = entry;
            }
            case CORNER -> {
                table[index] = entry;
                table[(index + 1) % 37] = entry;
                table[(index + 2) % 37] = entry;
                table[(index + 3) % 37] = entry;
            }
        }
    }
}