package operateur;

import solution.Tournee;

public class IntraDeplacementTech extends OperateurIntraTournee {
    /*
     * CONSTRUCTEURS
     */
    public IntraDeplacementTech() {
        super();
    }

    public IntraDeplacementTech(Tournee tournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
    }

    /*
     * METHODES
     */
    @Override
    protected int evalDeltaCout() {
        return tournee.deltaCoutDeplacementTech(positionI, positionJ);
    }

    @Override
    protected boolean doMovement() {
        return tournee.doDeplacementTech(this);
    }


    @Override
    public String toString() {
        return "IntraDeplacementTech{" +
                "tournee=" + tournee +
                ", deltaCout=" + deltaCout +
                ", positionI=" + positionI +
                ", positionJ=" + positionJ +
                ", clientI=" + requestI +
                ", clientJ=" + requestJ +
                '}';
    }
}
