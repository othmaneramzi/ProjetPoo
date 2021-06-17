package solveur;

import instance.Instance;
import instance.Request;
import solution.Solution;
import solution.Tournee;
import solution.TourneeTech;
import solution.TourneeTruck;

import java.util.ArrayList;
import java.util.LinkedList;

public class Ajout implements Solveur {

    @Override
    public String getNom() {
        return "Résolution par ajouts";
    }

    /*
    Ajoute la requete à la suite d'une tournée existante, si aucune n'est possible, crée une nouvelle tournée
     */
    @Override
    public Solution solve(Instance instance) {
        Solution solu = new Solution(instance);
        for(Request r : instance.getRequests()){
            int i=0;
            LinkedList<Tournee> list_t = solu.getListeTournees().get(r.getFirstDay());
            if(list_t.isEmpty()){
                solu.ajouterClientNouvelleTourneeTruck(r, r.getFirstDay());
            }else {
                Tournee t = list_t.get(i);
                while (!solu.ajouterClientTourneeTruck(r, t)) {
                    if (i == solu.getListeTournees().get(r.getFirstDay()).size()) {
                        solu.ajouterClientNouvelleTourneeTruck(r, r.getFirstDay());
                        break;
                    }
                    t = solu.getListeTournees().get(r.getFirstDay()).get(i);
                    i++;
                }
            }
        }

        for (Request r : instance.getRequests()) {
            boolean ok = false;
            int day = 0;
            for (int j = 0; j <= solu.getListeTournees().size(); j++) {
                if (solu.getListeTournees().get(j) != null){
                    for (Tournee t : solu.getListeTournees().get(j)) {
                        if (t instanceof TourneeTech) {
                            if (!ok && solu.ajouterClientTourneeTech(r, (TourneeTech) t)) ok = true;
                        }
                    }
                }
            }
            while (!ok && solu.ajouterClientNouvelleTourneeTech(r, r.getJourLivraison() + day + 1, null)){
                day++;
                ok = true;
            }
        }

        return solu;
    }
}
