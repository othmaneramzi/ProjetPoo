package solution;

import instance.Instance;
import instance.Machine;
import instance.Request;

import java.util.LinkedHashMap;
import java.util.Map;

public class TourneeTruck extends Tournee {
    private int capacity;
    private int maxCapacity;
    private int distance;
    private int maxDistance;
    private Map<Integer,Machine> mapMachines;

    public TourneeTruck(Instance instance, int jour) {
        super(instance, jour);
        this.depot = instance.getDepot();
        this.capacity = 0;
        this.mapMachines = instance.getMapMachines();
        this.maxCapacity = instance.getTruckCapacity();
        this.distance = 0;
        this.maxDistance = instance.getTruckMaxDistance();
    }

    public TourneeTruck(TourneeTruck tourneeTruck) {
        super(tourneeTruck);
        this.depot = tourneeTruck.getDepot();
        this.capacity = tourneeTruck.getCapacity();
        this.mapMachines = tourneeTruck.getMapMachines();
        this.maxCapacity = tourneeTruck.getMaxCapacity();
        this.distance= tourneeTruck.getDistance();
        this.maxDistance = tourneeTruck.getMaxDistance();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getDistance(){ return distance; }

    public int getMaxDistance(){ return maxDistance; }

    public Map<Integer, Machine> getMapMachines() {
        return new LinkedHashMap<Integer, Machine>(mapMachines);
    }

    @Override
    public boolean ajouterRequest(Request request) {
        if(canInsererRequest(request)){
            int cout =calculCoutAjoutRequest(request);
            request.setJourLivraison(jour);

            coutTotal += this.calculCoutAjoutRequest(request);
            this.listRequest.add(request);
            //System.out.println(coutTotal); // TODO : OOF
            capacity += this.instance.getMapMachines().get(request.getIdMachine()).getSize() * request.getNbMachine();
            distance += cout;
            return true;
        }
        return false;
    }

    @Override
    public boolean canInsererRequest(Request request) {
        if (request == null) return false;

        if(request.getFirstDay() > this.jour)
            return false;

        if (request.getLastDay() <= this.jour)
            return false;

        if (capacity + (mapMachines.get(request.getIdMachine()).getSize() * request.getNbMachine()) > maxCapacity)
            return false;

        if (!checkAjoutDistance(calculCoutAjoutRequest(request))){
            return false;
        }
        return true;
    }

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public boolean checkAjoutDistance(int delta) {
        return distance + delta <= maxDistance;
    }

    public void addCapacity(int ajout){
        this.capacity += ajout;
    }

    public void addDistance(int ajout){
        this.distance += ajout;
    }
}