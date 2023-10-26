public interface BetStrategy {
    boolean spin(TableEntry[] table) throws InterruptedException;
}
