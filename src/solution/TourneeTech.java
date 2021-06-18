package solution;

import instance.Instance;
import instance.Request;
import network.Depot;
import network.Tech;
import network.Client;
import network.Point;

public class TourneeTech extends Tournee {
    private Tech technician;
    private int distance;

    public TourneeTech(Instance instance, int jour, Tech technician) {
        super(instance, jour);
        this.technician = technician;
        this.depot = technician.getDepot();
    }

    public TourneeTech(TourneeTech tourneetech) {
        super(tourneetech);
        this.technician = tourneetech.technician;
        this.depot = tourneetech.technician.getDepot();
    }

    public Tech getTechnician(){
        return technician;
    }

    @Override
    public boolean ajouterRequest(Request request) {

        if(canInsererRequest(request)){

            coutTotal += this.calculCoutAjoutRequest(request);
            request.setJourInstallation(jour);
            technician.ajouterRequest(request, jour, this);
            //System.out.println(coutTotal); // TODO : OOF
            this.listRequest.add(request);
            return true;
        }
        return false;
    }

    @Override
    public boolean canInsererRequest(Request request) {

        if (request.getJourLivraison() >= this.jour)
            return false;


        if(request.getLastDay() < this.jour)
            return false;

        return technician.isDisponible(request, jour, this);
    }

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public boolean checkAjoutDistance(int delta) {
        return distance + delta <= getTechnician().getMaxDistance();
    }

    public boolean retirerRequete(int position){
        if (isPositionvalide(position)){
            coutTotal -= this.deltaCoutSuppression(position);
            this.listRequest.remove(position);
            return true;
        }else {
            return false;
        }
    }
}