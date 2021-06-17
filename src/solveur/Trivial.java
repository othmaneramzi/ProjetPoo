package solveur;

import instance.Instance;
import instance.Request;
import solution.Solution;

public class Trivial implements Solveur{

    @Override
    public String getNom() {
        return "Résolution trivial";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution solu = new Solution(instance);

        for(Request r : instance.getRequests()) {
            solu.ajouterClientNouvelleTourneeTruck(r, r.getFirstDay()); // Pour rappel on a camion infini
        }
        for(Request r : instance.getRequests()) {
            int day = 0;
            while(r.getFirstDay()+1+day <= solu.getInstance().getDays() && !solu.ajouterClientNouvelleTourneeTech(r, r.getFirstDay()+1+day, null)){
                day++;
            }
        }

        return solu;
    }
}