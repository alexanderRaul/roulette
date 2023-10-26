import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Roulette {
    private final Map<BetType, BetStrategy> betStrategies;
    private final Random random = new Random();

    public Roulette() {
        this.betStrategies = new HashMap<>();

        betStrategies.put(BetType.STRAIGHT_UP, this::spinStraightUp);
        betStrategies.put(BetType.SPLIT, this::spinSplit);
        betStrategies.put(BetType.STREET, this::spinStreet);
        betStrategies.put(BetType.CORNER, this::spinCorner);
    }

    public boolean spin(TableEntry[] table) throws InterruptedException {
        int indexBet = findBetIndex(table);

        final Bet bet = table[indexBet].getBet();
        final BetStrategy strategy = betStrategies.get(bet.getType());

        return strategy.spin(table);
    }

    private int findBetIndex(TableEntry[] table) {
        for (int i = 0; i < 37; i++)
            if (table[i] != null)
                return i;
        return -1;
    }

    private boolean spinStraightUp(TableEntry[] table) throws InterruptedException {
        System.out.println("\n straight Up");

        for (int i = 0; i < 35; i++) {
            final int randomNumber = random.nextInt(37);
            System.out.print("\u001B[K");
            System.out.print("Generated number: " + randomNumber);

            Thread.sleep(225);

            if (table[randomNumber] != null) {
                final double randomDouble = random.nextDouble();
                final double probability = table[randomNumber].getBet().getProbability();

                if (randomDouble < probability && random.nextBoolean())
                    return true;
            }
            System.out.print("\r");
        }
        return false;
    }

    private boolean spinSplit(TableEntry[] table) throws InterruptedException {
        System.out.println("Split");

        for (int i = 0; i < 35; i++) {
            int randomNumber = random.nextInt(37);
            System.out.print("\u001B[K");
            System.out.print("Generated number: " + randomNumber);

            Thread.sleep(225);

            TableEntry entry1 = table[randomNumber];
            TableEntry entry2 = table[(randomNumber + 1) % 37];

            if (entry1 != null || entry2 != null) {
                double probability = (entry1 != null ? entry1 : entry2).getBet().getProbability();
                double randomDouble = random.nextDouble();

                if (probability > randomDouble)
                    return true;
            }
            System.out.print("\r");
        }
        return false;
    }

    private boolean spinStreet(TableEntry[] table) throws InterruptedException {
        System.out.println("Street");

        for (int i = 0; i < 30; i++) {
            int randomNumber = random.nextInt(37);
            System.out.print("[K");
            System.out.print("Generated number: " + randomNumber);

            Thread.sleep(225);

            TableEntry entry1 = table[randomNumber];
            TableEntry entry2 = table[(randomNumber + 1) % 37];
            TableEntry entry3 = table[(randomNumber + 2) % 37];

            if (entry1 != null || entry2 != null || entry3 != null) {
                double probability = (entry1 != null ? entry1 : entry2 != null ? entry2 : entry3).getBet().getProbability();
                double randomDouble = random.nextDouble();

                if (probability > randomDouble - 0.02)
                    return true;
            }
            System.out.print("\r");
        }
        return false;
    }

    private boolean spinCorner(TableEntry[] table) throws InterruptedException {
        System.out.println("corner");

        for (int i = 0; i < 30; i++) {
            int randomNumber = random.nextInt(37);
            System.out.print("[K");
            System.out.print("Generated number: " + randomNumber);

            Thread.sleep(225);

            TableEntry entry1 = table[randomNumber];
            TableEntry entry2 = table[(randomNumber + 1) % 37];
            TableEntry entry3 = table[(randomNumber + 2) % 37];
            TableEntry entry4 = table[(randomNumber + 3) % 37];

            if (entry1 != null || entry2 != null || entry3 != null || entry4 != null) {
                double probability = (entry1 != null ? entry1 : entry2 != null ? entry2 : entry3 != null ? entry3 : entry4).getBet().getProbability();
                double randomDouble = random.nextDouble();

                if (probability > randomDouble - 0.04)
                    return true;
            }
            System.out.print("\r");
        }
        return false;
    }

    @Override
    public String toString() {
        return "Roulette{" +
                "betStrategies=" + betStrategies +
                ", random=" + random +
                '}';
    }
}