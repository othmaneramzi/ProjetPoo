package operateur;

import solution.Tournee;

public class InterDeplacementTech extends OperateurInterTournee{

    public InterDeplacementTech() {
        super();
    }

    public InterDeplacementTech(Tournee t, Tournee t2, int pos1, int pos2){
        super(t,t2,pos1,pos2);
    }

    @Override
    protected int evalDeltaCoutTournee() {
        return deltaDistanceTournee*tournee.getCoutTechDistance();
    }

    @Override
    protected int evalDeltaCoutAutreTournee() {
        return deltaDistanceAutreTournee*autreTournee.getCoutTechDistance();
    }

    @Override
    public int evalDeltaDistanceTournee() {
        return tournee.deltaCoutSuppression(positionI);
    }

    @Override
    public int evalDeltaDistanceAutreTournee() {
        return autreTournee.deltaCoutInsertionInterTech(positionJ,requestI);
    }

    @Override
    protected int evalDeltaIDLE() {
        return tournee.getCoutSuppDeplacementTech(this);
    }

    /*@Override
    protected int evalDeltaCoutDistance() {
        return tournee.deltaCoutSuppression(positionI)+autreTournee.deltaCoutInsertionInterTruck(positionJ,requestI);
    }*/


    @Override
    protected boolean doMovement() {
        if(this.isMouvementRealisable())
            return tournee.doDeplacementTech(this);
        else
            return false;
    }

    @Override
    public String toString() {
        return "InterDeplacementTech[ "+/*Tournee1 = "+tournee+", Tournee2 = "+tournee2+", "+*/" Client1 = "+requestI.getClient().getId()+
                ", position1 = "+positionI+", Client2 = "+requestJ.getClient().getId()+
                ", position2 = "+positionJ+", cout = "+deltaCout+", distance = "+getDeltaDistance()+"]";
    }
}
