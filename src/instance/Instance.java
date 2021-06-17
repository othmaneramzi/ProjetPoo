package instance;

import instance.Request;
import network.Client;
import network.Depot;
import network.Point;
import network.Tech;

import java.util.*;

public class Instance {
    /**
     * PARAMETRE
     */
    private final String dataset;
    private final String nom;
    private final int days;
    private final int truckCapacity;
    private final int truckMaxDistance;
    private final int truckDistanceCost;
    private final int truckDayCost;
    private final int truckCost;
    private final int technicianDistanceCost;
    private final int technicianDayCost;
    private final int technicianCost;
    private final List<Tech> technicians;
    private final Map<Integer, Machine> mapMachines;
    private final Depot depot;
    private final Map<Integer, Client> mapClients;
    private final Map<Integer, Request> mapRequests;

    /**
     * CONSTRUCTEUR
     */
    public Instance(String dataset,
                    String nom,
                    int truckMaxDistance,
                    int technicianCost,
                    Depot depot,
                    int days,
                    int truckCapacity,
                    int truckDistanceCost,
                    int truckDayCost,
                    int truckCost,
                    int technicianDistanceCost,
                    int technicianDayCost
    ) {
        this.dataset = dataset;
        this.nom = nom;
        this.depot = depot;
        this.technicianDistanceCost = technicianDistanceCost;
        this.technicianDayCost = technicianDayCost;
        this.technicianCost = technicianCost;
        this.days = days;
        this.technicians = new LinkedList<>();
        this.mapMachines = new LinkedHashMap<>();
        this.truckCapacity = truckCapacity;
        this.truckDistanceCost = truckDistanceCost;
        this.truckMaxDistance = truckMaxDistance;
        this.truckDayCost = truckDayCost;
        this.truckCost = truckCost;
        this.mapClients = new LinkedHashMap<>();
        this.mapRequests = new LinkedHashMap<>();
    }

    /**
     * METHODES
     */
    public String getNom() {
        return nom;
    }

    public Depot getDepot() {
        return depot;
    }

    public String getDataset() { return dataset; }

    public int getDays() { return days; }

    public int getTruckCapacity(){ return truckCapacity; }

    public int getTruckMaxDistance(){ return truckMaxDistance; }

    public int getTruckDistanceCost() {
        return truckDistanceCost;
    }

    public int getTruckDayCost() {
        return truckDayCost;
    }

    public int getTruckCost() {
        return truckCost;
    }

    public int getTechnicianDistanceCost() {
        return technicianDistanceCost;
    }

    public int getTechnicianDayCost() {
        return technicianDayCost;
    }

    public int getTechnicianCost() {
        return technicianCost;
    }

    public Map<Integer, Machine> getMapMachines(){ return mapMachines; }

    /**
     * Fonction qui renvoie le nombre de client dans cette instance
     * @return le nombre de clients
     */
    public int getNbClients() {
        return mapClients.size();
    }

    public LinkedList<Tech> getTechs() {
        return new LinkedList<>(technicians);
    }

    /**
     * Fonction permettant de récupérer un client à partir de son ID
     * @param id l'id du client à rechercher
     * @return le client dont l'id correspond à la recherche ou null
     */
    public Client getClientByID(int id) {
        return mapClients.get(id);
    }

    /**
     * Fonction pour récupérer la liste de tous les clients de l'instance
     * @return une LinkedList, copie de la liste des clients
     */
    public LinkedList<Client> getClients() {
        return new LinkedList<>(mapClients.values());
    }

    /**
     * Fonction pour récupérer la liste de toutes les requests de l'instance
     * @return une LinkedList, copie de la liste des requests
     */
    public LinkedList<Request> getRequests() {
        return new LinkedList<>(mapRequests.values());
    }

    /**
     * Fonction qui ajoute un client à la liste des clients en créant toutes les routes nécessaires entre ce client,
     * lee dépot et les autres clients
     * @param client le client à ajouter à la liste
     * @return true si l'ajout a été effectué et false sinon
     */
    public boolean ajouterClient(Client client) {
        if (client == null || mapClients.containsValue(client))
            return false;

        mapClients.put(client.getId(), client);
        client.ajouterRoute(depot);
        for (Client c : mapClients.values())
            client.ajouterRoute(c);
        for (Tech t : technicians)
            client.ajouterRoute(t.getDepot());
        return true;
    }

    public boolean ajouterRequest(Request request) {

        if (request == null || mapRequests.containsValue(request))
            return false;
        if(mapClients.containsKey(request.getClient().getId())){
            request.setClient(mapClients.get(request.getClient().getId()));
        }else {
            ajouterClient(request.getClient());
        }
            mapRequests.put(request.getId(), request);
            request.getClient().ajouterRequest(request);
            //System.out.println("request id=" + request.getId() + ", routes=" + request.getClient().getMapRoutes());
        return true;
    }

    /**
     * Fonction qui ajoute un technician à la liste des technicians en créant toutes les routes nécessaires
     * @param technician le technician à ajouter à la liste
     * @return true si l'ajout a été effectué et false sinon
     */
    public boolean ajouterTech(Tech technician) {
        if (technician == null || technicians.contains(technician))
            return false;
        technician.initDays(days);
        if(mapClients.containsKey(technician.getDepot().getId())){
            technician.setDepot(mapClients.get(technician.getDepot().getId()));
        }else {
            technician.getDepot().ajouterRoute(depot);
            for (Client c : mapClients.values())
                technician.getDepot().ajouterRoute(c);
            for (Tech t : technicians)
                technician.getDepot().ajouterRoute(t.getDepot());
        }
        technicians.add(technician);
        //System.out.println("tech id="+technician.getIdTechnician()+", routes="+technician.getDepot().getMapRoutes());
        return true;
    }

    /**
     * Fonction qui ajoute une machine à la liste des machine
     * @param machine la machine à ajouter à la liste
     * @return true si l'ajout a été effectué et false sinon
     */
    public boolean ajouterMachine(Machine machine) {
        if (machine == null || mapMachines.containsValue(machine))
            return false;
        mapMachines.put(machine.getId(), machine);
        return true;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "nom='" + nom + '\'' +
                ", depot=" + depot +
                ", mapClients=" + mapClients +
                ", Techs=" + technicians +
                ", machines=" + mapMachines +
                '}';
    }
}