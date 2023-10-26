import java.util.Arrays;

public class Table {
    private final TableEntry[] table;

    public Table() {
        table = new TableEntry[37];
    }

    public void cleanTable() {
        for (int i = 0; i < 37; i++)
            table[i] = null;
    }

    public TableEntry[] getTable() {
        return table;
    }


    public void displayFormattedTable() {
        for (int i = 0; i < table.length; i++) {
            if (i % 6 == 0) {
                System.out.println();
            }

            if (table[i] == null) {
                System.out.printf("%-8s", "[" + i + "]");
            } else {
                System.out.printf("%-8s", "[X]");
            }
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "Table{" +
                "table=" + Arrays.toString(table) +
                '}';
    }
}