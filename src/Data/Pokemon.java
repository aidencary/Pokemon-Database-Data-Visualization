package Data;

public record Pokemon(int id, String name, String type1, String type2, int total, int hp, int attack, int defense, int spAtk, int spDef, int speed, int generation, boolean legendary) {
    public Pokemon(String[] data) {
        this(
                Integer.parseInt(data[0]),
                data[1],
                data[2],
                data[3].isEmpty() ? "None" : data[3],
                Integer.parseInt(data[4]),
                Integer.parseInt(data[5]),
                Integer.parseInt(data[6]),
                Integer.parseInt(data[7]),
                Integer.parseInt(data[8]),
                Integer.parseInt(data[9]),
                Integer.parseInt(data[10]),
                Integer.parseInt(data[11]),
                Boolean.parseBoolean(data[12])
        );
    }

    @Override
    public String toString() {
        return String.format("%d - %s (%s/%s)", id, name, type1, type2);
    }
}

