package instance;

public class Machine {
    private int id;
    private int size;
    private int penalityCost;

    public Machine(int id, int size, int penalityCost) {
         this.id = id;
         this.size = size;
         this.penalityCost = penalityCost;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public int getPenalityCost() {
        return penalityCost;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", size=" + size +
                ", penalityCost=" + penalityCost +
                '}';
    }
}
