package solveur;

import instance.Instance;
import instance.Request;
import operateur.IntraEchangeTruck;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;
import solution.Solution;

public class RechercheLocale implements Solveur{

    @Override
    public String getNom() {
        return "Résolution RechercheLocale";
    }

    @Override
    public Solution solve(Instance instance) {
        Solveur solvPPV = new InsertionPPV();
        Solution solu = solvPPV.solve(instance);

        boolean improve = true;
        while(improve){
            improve = false;
            for (TypeOperateurLocal type : TypeOperateurLocal.values()) {
                OperateurLocal best = solu.getMeilleurOperateurLocal(type);
                if (best != null && best.isMouvementRealisable() && best.isMouvementAmeliorant()) {
                    improve = true;
                    solu.doMouvementRechercheLocale(best);
                }
            }
        }

        return solu;
    }
}