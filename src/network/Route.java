package network;

import java.util.Objects;

public class Route {
    /**
     * PARAMETRES
     */
    private final int cout;
    private final Point debut;
    private final Point fin;

    /**
     * CONSTRUCTEUR
     */
    public Route(Point debut, Point fin) {
        this.debut = debut;
        this.fin = fin;
        cout = calculerCout();
    }

    /*
     * METHODES
     */
    /**
     * Méthode qui permet de calculer le deltaCout d'une route à partir de ses points.
     * @return le deltaCout entier d'une route.
     */
    private int calculerCout() {
        return (int)Math.ceil(
                        Math.sqrt(
                                (fin.getX() - debut.getX())*(fin.getX() - debut.getX())
                              + (fin.getY() - debut.getY())*(fin.getY() - debut.getY())
                        )
        );
    }

    public int getCout() {
        return cout;
    }

    public Point getDebut() {
        return debut;
    }

    public Point getFin() {
        return fin;
    }

    @Override
    public String toString() {
        return "Route{" +
                "deltaCout=" + cout +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(debut, route.debut) && Objects.equals(fin, route.fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debut, fin);
    }
}
