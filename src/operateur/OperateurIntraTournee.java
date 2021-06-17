package operateur;

import solution.Tournee;

public abstract class OperateurIntraTournee extends OperateurLocal {
    /*
     * CONSTRUCTEURS
     */
    public OperateurIntraTournee() {
        super();
    }

    public OperateurIntraTournee(Tournee t, int pI, int pJ) {
        super(t, pI, pJ);
        deltaCout = evalDeltaCout();
    }
}
