package operateur;

import solution.Tournee;

public class IntraDeplacementTruck extends OperateurIntraTournee {
    /*
     * CONSTRUCTEURS
     */
    public IntraDeplacementTruck() {
        super();
    }

    public IntraDeplacementTruck(Tournee tournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
    }

    /*
     * METHODES
     */
    /*@Override
    public boolean isTabou(OperateurLocal op) {
        return op instanceof IntraDeplacement &&
                this.tournee == op.tournee &&
                this.requestI == op.requestI;
    }*/

    @Override
    protected int evalDeltaCout() {
        return tournee.deltaCoutDeplacementTruck(positionI, positionJ);
    }

    @Override
    protected boolean doMovement() {
        return tournee.doDeplacementTruck(this);
    }


    @Override
    public String toString() {
        return "IntraDeplacementTruck{" +
                "tournee=" + tournee +
                ", deltaCout=" + deltaCout +
                ", positionI=" + positionI +
                ", positionJ=" + positionJ +
                ", requestI=" + requestI +
                ", requestJ=" + requestJ +
                '}';
    }
}
