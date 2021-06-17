package network;

import instance.Request;
import solution.Tournee;
import solution.TourneeTech;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tech {

    private class Etat {
        private int fatigue;
        private int demande;
        private Point position;
        private int distance;
        private TourneeTech tournee;

        public Etat(Point position, int fatigue){
            this.position = position;
            this.fatigue = fatigue;
            this.demande = 0;
            this.distance = 0;
            this.tournee = null;
        }

        public void setPosition(Point valeur){
            this.position = valeur;
        }

        public void setFatigue(int valeur){
            this.fatigue = valeur;
        }

        public void ajouterDistance(int valeur){ this.distance += valeur; }

        public int getDistance(){ return distance; }

        public void ajouterDemande(int valeur){this.demande += valeur; }

    }

    private int idTechnician;
    private int maxDistance;
    private int maxDemande;
    private List<Integer> machines;
    private Point depot;
    private Map<Integer,Etat> disponibilite;

    /**
     * CONSTRUCTEUR
     */
    public Tech(int id, int x, int y, int idTechnician, int maxDistance, int maxDemande, List<Integer> machines) {
        depot = new Depot(id, x, y);
        this.idTechnician = idTechnician;
        this.maxDistance = maxDistance;
        this.maxDemande = maxDemande;
        this.machines = machines;
        this.disponibilite = new HashMap<>();
    }

    public int getDistance(int jour){
        if (disponibilite.get(jour) != null)
            return disponibilite.get(jour).getDistance();
        return Integer.MAX_VALUE;
    }

    public TourneeTech getTournee(int jour){
        if (disponibilite.get(jour) != null)
            return disponibilite.get(jour).tournee;
        return null;
    }

    public Point getPosition(int jour){ return disponibilite.get(jour).position; }

    public int getFatigue(int jour){ return disponibilite.get(jour).fatigue; }

    public int getMaxDistance(){
        return maxDistance;
    }

    public boolean isDisponible(Request request, int jour, TourneeTech tournee){
        if(disponibilite.get(jour).tournee != null && disponibilite.get(jour).tournee != tournee)
            return false;

        if(this.machines.get(request.getIdMachine()-1) != 1) return false;

        if(disponibilite.get(jour).fatigue == 0) {
            if (disponibilite.get(jour - 2) != null && disponibilite.get(jour - 2).fatigue == 5)
                return false;

            int fat = 0;
            if (disponibilite.get(jour - 1) != null) {
                fat = disponibilite.get(jour - 1).fatigue;
            }

            int i = 1;
            while (disponibilite.get(jour + i) != null && disponibilite.get(jour + i).fatigue != 0) {
                fat++;
                i++;
            }
            if (
                    fat + 1 > 5 ||
                            (fat + 1 == 5 && disponibilite.get(jour + i + 2) != null && disponibilite.get(jour + i + 2).fatigue != 0)
            )
                return false;
        }

        return disponibilite.get(jour).demande + 1 <= maxDemande &&
                (disponibilite.get(jour).distance +
                disponibilite.get(jour).position.getCoutVers(request.getClient())+
                request.getClient().getCoutVers(depot) -
                disponibilite.get(jour).position.getCoutVers(depot)) <= maxDistance;
    }

    public boolean ajouterRequest(Request request,int jour, Tournee t){
        if(isDisponible(request, jour, (TourneeTech) t)){
            if(disponibilite.get(jour).tournee == null) {
                disponibilite.get(jour).tournee = (TourneeTech) t;
            }else{
                if (disponibilite.get(jour).tournee != t)
                    return false;
            }
            disponibilite.get(jour).ajouterDistance(t.calculCoutAjoutRequest(request));
            disponibilite.get(jour).ajouterDemande(1);
            int fatigue = 0;
            if(jour != 1) {
                fatigue = this.disponibilite.get(jour - 1).fatigue;
            }
            disponibilite.get(jour).setPosition(request.getClient());
            disponibilite.get(jour).setFatigue(fatigue+1);
            int i = jour;
            while(disponibilite.get(i) != null && disponibilite.get(i).fatigue != 0){
                disponibilite.get(i).fatigue = fatigue+1;
                i++;
            }
            return true;
        }
        return false;
    }

    public boolean isUsed(){
        int i = 1;
        while(disponibilite.get(i) != null){
            if (disponibilite.get(i).fatigue != 0)
                return true;
            i++;
        }
        return false;
    }

    public void initDays(int days) {
        for(int i = 1; i <=days; i++){
            disponibilite.computeIfAbsent(i, k -> new Etat(depot, 0));
            disponibilite.computeIfPresent(i, (k, v) -> new Etat(depot, 0));
        }
    }

    public Point getDepot(){
        return this.depot;
    }

    public void setDepot(Point depot) {
        this.depot = depot;
    }

    public int getIdTechnician(){
        return this.idTechnician;
    }
}