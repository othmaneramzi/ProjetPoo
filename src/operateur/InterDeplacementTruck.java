package operateur;

import solution.Tournee;

public class InterDeplacementTruck extends OperateurInterTournee{

    public InterDeplacementTruck() {
        super();
    }


    public InterDeplacementTruck(Tournee t, Tournee t2, int pos1, int pos2){
        super(t,t2,pos1,pos2);
    }

    @Override
    protected int evalDeltaCoutTournee() {
        return deltaDistanceTournee*tournee.getCoutTruckDistance();
    }

    @Override
    protected int evalDeltaCoutAutreTournee() {
        return deltaDistanceAutreTournee*autreTournee.getCoutTruckDistance();
    }

    @Override
    public int evalDeltaDistanceTournee() {
        return tournee.deltaCoutSuppression(positionI);
    }

    @Override
    public int evalDeltaDistanceAutreTournee() {
        return autreTournee.deltaCoutInsertionInterTruck(positionJ,requestI);
    }

    @Override
    protected int evalDeltaIDLE() {
        return tournee.getCoutSuppDeplacementTruck(this);
    }

    /*@Override
    protected int evalDeltaCoutDistance() {
        return tournee.deltaCoutSuppression(positionI)+autreTournee.deltaCoutInsertionInterTruck(positionJ,requestI);
    }*/


    @Override
    protected boolean doMovement() {
        if(this.isMouvementRealisable())
            return tournee.doDeplacementTruck(this);
        else
            return false;
    }

    @Override
    public String toString() {
        return "InterDeplacement[ "+/*Tournee1 = "+tournee+", Tournee2 = "+tournee2+", "+*/" Client1 = "+requestI+
                ", position1 = "+positionI+", Client2 = "+requestJ+
                ", position2 = "+positionJ+", cout = "+deltaCout+"]";
    }
}
