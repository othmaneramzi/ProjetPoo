package solveur;

import instance.Instance;
import instance.Request;
import network.Tech;
import solution.Solution;

import java.util.LinkedList;

public class InsertionPPV implements Solveur {

    @Override
    public String getNom() {
        return "InsertionPPV";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution sol = new Solution(instance);
        LinkedList<Request> listRequest = instance.getRequests();
        LinkedList<Tech> listTechs = instance.getTechs();

        // Insertion PPV pour les tournées truck
        Request r = listRequest.getFirst();
        Tech t;

        while (!listRequest.isEmpty()) {
            if (r == null) r = listRequest.getFirst();
            if (!sol.ajouterClientDerniereTourneeTruck(r))
                sol.ajouterClientNouvelleTourneeTruck(r, r.getFirstDay());

            int day = 1;
            boolean test = false;
            while(!test && day < instance.getDays()) {
                t = plusProcheVoisinTech(r, listTechs, r.getFirstDay()+day);
                if(t != null) {
                    if (!sol.ajouterClientDerniereTourneeTech(r, r.getFirstDay() + day, t)) {
                        test = sol.ajouterClientNouvelleTourneeTech(r, r.getJourLivraison() + day, t);
                    }else {
                        test = true;
                    }
                }
                day++;
            }

            listRequest.remove(r);
            r = plusProcheVoisinTruck(r, listRequest);
        }

        return sol;
    }

    private Tech plusProcheVoisinTech(Request r, LinkedList<Tech> listTechs, int jour) {
        if (listTechs.isEmpty() || r == null) return null;

        Tech minT = getFirstTechDispo(r, jour, listTechs);
        if (minT == null) return null;

        int min = minT.getPosition(jour).getCoutVers(r.getClient());

        for (Tech t : listTechs)
            if (min > t.getPosition(jour).getCoutVers(r.getClient()) && t.isDisponible(r, jour, t.getTournee(jour))) {
                minT = t;
                min = t.getPosition(jour).getCoutVers(r.getClient());
            }

        return minT;
    }

    private Tech getFirstTechDispo(Request r, int jour, LinkedList<Tech> listTechs) {
        if (listTechs.isEmpty()) return null;

        for (Tech t : listTechs)
            if (t.isDisponible(r, jour, t.getTournee(jour)))
                return t;

        return null;
    }

    private Request plusProcheVoisinTruck(Request r, LinkedList<Request> listRequest) {
        if (listRequest.isEmpty()) return null;

        Request minR = getFirstSameDay(r.getFirstDay(), listRequest);
        if (minR == null) return null;

        int min = minR.getClient().getCoutVers(r.getClient());

        for (Request req : listRequest)
            if (min > r.getClient().getCoutVers(req.getClient()) && req.getFirstDay() == r.getFirstDay()) {
                minR = req;
                min = r.getClient().getCoutVers(req.getClient());
            }

        return minR;
    }

    private Request getFirstSameDay(int day, LinkedList<Request> listRequest) {
        if (listRequest.isEmpty()) return null;

        for (Request r : listRequest)
            if (r.getFirstDay() == day)
                return r;

        return null;
    }
}