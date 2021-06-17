package network;


public class Depot extends Point{
    /**
     * CONSTRUCTEUR
     */
    public Depot(int id, int x, int y){
        super(id, x, y);
    }

    @Override
    public String toString() {
        return "Depot{" +
                "point=" + super.toString() +
                '}';
    }
}
