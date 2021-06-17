package operateur;

import solution.Tournee;

public abstract class Operateur {
    /*
     * PARAMETRES
     */
    protected Tournee tournee;
    protected int deltaCout;

    /*
     * CONSTRUCTEUR
     */
    public Operateur() {
        tournee = null;
        deltaCout = Integer.MAX_VALUE;
    }

    public  Operateur(Tournee t) {
        this();
        tournee = t;
    }

    /*
     * METHODES
     */
    public Tournee getTournee() {
        return tournee;
    }

    public int getDeltaCout() {
        return deltaCout;
    }

    public boolean isMouvementRealisable() {
        return deltaCout != Integer.MAX_VALUE;
    }

    public boolean isMouvementAmeliorant() {
        return deltaCout < 0;
    }

    /**
     * Fonction qui implémente un movement si ce dernier est réalisable
     * @return true si le movement a été implémenté et false sinon
     */
    public boolean doMovementIfRealisable() {
        if (isMouvementRealisable())
            return doMovement();
        return false;
    }

    /**
     * Fonction qui renvoie le deltaCout associé à l'application de l'opérateur
     * @return le deltaCout engendré par l'opérateur
     */
    protected abstract int evalDeltaCout();

    /**
     * Fonction qui implémente le mouvement associé à l'opérateur
     * @return true si le mouvement a été effectué et false sinon
     */
    protected abstract boolean doMovement();

    /**
     * Fonction qui test si cet opérateur est meilleur qu'un autre opérateur (ie. s'il coute moins cher)
     * @param op le second opérateur à tester
     * @return true si this est meilleur que op et false sinon
     */
    public boolean isMeilleur(Operateur op) {
        return getDeltaCout() < op.getDeltaCout();
    }

    @Override
    public String toString() {
        return "Operateur{" +
                "tournee=" + tournee +
                ", deltaCout=" + deltaCout +
                '}';
    }
}
