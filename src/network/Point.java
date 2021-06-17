package network;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Point {
    /**
     * PARAMETRES
     */
    private final int id;
    private final int x;
    private final int y;

    private Map<Integer, Route> mapRoutes;

    /**
     * CONSTRUCTEUR
     */
    public Point(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
        this.mapRoutes = new HashMap<>();
    }

    /**
     * METHODES
     */
    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Map<Integer, Route> getMapRoutes() {
        return mapRoutes;
    }

    /**
     * Méthode qui ajoute une route à la liste des routes partant de ce point. Elle ajoute aussi la route au point de
     * destination.
     * @param dest le point de destination de la route
     */
    public void ajouterRoute(Point dest) {
        // On retire la limite de la route car c'est le démon et ça te fait disparaitre genre 2-3 km
        if (dest != null /*&& dest != this*/) {
            dest.mapRoutes.put(this.id, new Route(dest, this));
            mapRoutes.put(dest.id, new Route(this, dest));
        }
    }

    /**
     * Méthode qui renvoie le deltaCout de la route entre ce point et sa destination
     * @param dest le point de destination de la route
     * @return le deltaCout entre ce point et la destination
     */
    public int getCoutVers(Point dest) {
        if (this == dest)
            return 0;
        Route r = mapRoutes.get(dest.id);
        if (r == null)
            return Integer.MAX_VALUE;
        return r.getCout();
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return id == point.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}