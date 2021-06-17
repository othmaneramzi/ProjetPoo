package operateur;

import solution.Tournee;

public abstract class OperateurInterTournee extends OperateurLocal {
    /*
     * PARAMETRES
     */
    protected Tournee autreTournee;
    protected int deltaCoutTournee, deltaCoutAutreTournee, deltaIDLE, deltaDistanceTournee, deltaDistanceAutreTournee;

    /*
     * CONSTRUCTEURS
     */
    public OperateurInterTournee() {
        super();
        autreTournee = null;
        deltaCoutTournee = deltaCoutAutreTournee = 0;
    }

    public OperateurInterTournee(Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        super(tournee, positionI, positionJ);
        this.autreTournee = autreTournee;
        requestJ = autreTournee.getRequestAt(positionJ);
        deltaCout = evalDeltaCout();
    }

    /*
     * METHODES
     */
    public Tournee getAutreTournee() {
        return autreTournee;
    }

    public int getDeltaCoutTournee() {
        return deltaCoutTournee;
    }

    public int getDeltaCoutAutreTournee() {
        return deltaCoutAutreTournee;
    }

    public int getDeltaDistance(){ return deltaDistanceTournee+deltaDistanceAutreTournee;}

    public int getDeltaIDLE(){ return deltaIDLE;}

    /**
     * Calcul le surcout lié à l'application de l'opérateur sur la tournée
     * @return le surcout
     */
    protected abstract int evalDeltaCoutTournee();

    /**
     * Calcul le surcout lié à l'application de l'opérateur sur l'autre tournée
     * @return le surcout
     */
    protected abstract int evalDeltaCoutAutreTournee();


    public abstract int evalDeltaDistanceTournee();

    public abstract int evalDeltaDistanceAutreTournee();


    /**
     * Calcul le surcout lié à l'application de l'opérateur sur l'autre tournée
     * @return le surcout
     */
    protected abstract int evalDeltaIDLE();

    @Override
    protected int evalDeltaCout() {
        if (tournee == null || autreTournee == null)
            return Integer.MAX_VALUE;

        deltaDistanceTournee = evalDeltaDistanceTournee();
        deltaDistanceAutreTournee = evalDeltaDistanceAutreTournee();
        if(deltaDistanceTournee == Integer.MAX_VALUE || deltaDistanceAutreTournee == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        deltaCoutTournee = evalDeltaCoutTournee();
        //System.out.println("Tournee = "+deltaCoutTournee);
        deltaCoutAutreTournee = evalDeltaCoutAutreTournee();
        //System.out.println("Autre Tournee = "+deltaCoutAutreTournee);

        deltaIDLE = evalDeltaIDLE();
        //System.out.println("Tournee = "+deltaIDLE);

        if (deltaCoutTournee == Integer.MAX_VALUE || deltaCoutAutreTournee == Integer.MAX_VALUE)
            return Integer.MAX_VALUE;

        deltaCout = deltaCoutTournee + deltaCoutAutreTournee + deltaIDLE;

        return deltaCout;
    }

    @Override
    public String toString() {
        return "OperateurInterTournee{" +
                "tournee=" + tournee +
                ", deltaCout=" + deltaCout +
                ", autreTournee=" + autreTournee +
                ", deltaCoutTournee=" + deltaCoutTournee +
                ", deltaCoutAutreTournee=" + deltaCoutAutreTournee +
                ", positionI=" + positionI +
                ", positionJ=" + positionJ +
                ", requestI=" + requestI +
                ", requestJ=" + requestJ +
                '}';
    }
}
