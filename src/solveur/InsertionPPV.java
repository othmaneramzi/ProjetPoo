package solveur;

import instance.Instance;
import instance.Request;
import network.Tech;
import solution.Solution;
import solution.Tournee;
import solution.TourneeTech;

import java.util.Collections;
import java.util.Comparator;
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
        LinkedList<Request> listRequestTech = new LinkedList<Request>();
        LinkedList<Request> listTabou = new LinkedList<Request>();

        while (!listRequest.isEmpty()) {
            if (r == null) r = listRequest.getFirst();
            if (!sol.ajouterClientDerniereTourneeTruck(r))
                sol.ajouterClientNouvelleTourneeTruck(r, r.getFirstDay());

            listRequestTech.add(r);
            LinkedList<Request> listdeParcours = new LinkedList<Request>();
            listdeParcours.addAll(listRequestTech);
            for (Request req: listdeParcours) {
                int day = 1;
                boolean test = false;
                while(!test && req.getJourLivraison()+day < instance.getDays()) {
                    t = plusProcheVoisinTech(req, listTechs, req.getJourLivraison()+day);
                    if(t != null) {
                        if (!sol.ajouterClientDerniereTourneeTech(req, req.getJourLivraison() + day, t)) {
                            test = sol.ajouterClientNouvelleTourneeTech(req, req.getJourLivraison() + day, t);
                        }else {
                            test = true;
                        }
                    }
                    day++;
                }
                if(!test){
                    //TODO problème tech
                    listTabou.add(req);
                    boolean verif = true;
                    int jour = req.getJourLivraison()+1;
                    while(verif && jour <= req.getLastDay()) {
                        LinkedList<Tournee> listeTournee = sol.getListeTournees().get(jour);
                        int i = 0;
                        while (verif && i < listeTournee.size()) {
                            Tournee tournee = listeTournee.get(i);
                            if (tournee instanceof TourneeTech) {
                                Tech potentiel = ((TourneeTech) tournee).getTechnician();
                                int distance = potentiel.getDepot().getCoutVers(req.getClient()) * 2;
                                int maxdistance = potentiel.getMaxDistance();
                                if (potentiel.canInstallerMachine(req.getIdMachine()) && distance <= maxdistance) {
                                    verif = false;
                                    for (Request istabou : tournee.getListRequest()) {
                                        if (listTabou.contains(istabou))
                                            verif = true;
                                    }
                                    if (!verif) {
                                        LinkedList<Request> list = tournee.getListRequest();
                                        for (int j = list.size() - 1; tournee.getListRequest().size() > 0; j--) {
                                            listRequestTech.add(list.get(j));
                                            sol.retirerRequestTourneeTech(j, (TourneeTech) tournee);
                                        }
                                        if (sol.ajouterClientTourneeTech(req, (TourneeTech) tournee))
                                            listRequestTech.remove(req);
                                    }
                                }
                            }
                            i++;
                        }
                        jour++;
                    }
                }else{
                    listRequestTech.remove(req);
                }
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