package instance;

import network.Client;

public class Request {

    private int id;
    private Client client;
    private int firstDay;
    private int lastDay;
    private int idMachine;
    private int nbMachine;
    private int jourLivraison;
    private int jourInstallation;

    public Request(int id, Client client, int firstDay, int lastDay, int idMachine, int nbMachine) {
        this.id = id;
        this.client = client;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.idMachine = idMachine;
        this.nbMachine = nbMachine;
        this.jourLivraison = Integer.MAX_VALUE;
        this.jourInstallation = Integer.MAX_VALUE;
    }

    public int getNbMachine(){
        return this.nbMachine;
    }

    public Client getClient() { return this.client; }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getId() { return this.id; }

    public int getIdMachine() { return this.idMachine; }

    public int getJourLivraison() {
        return jourLivraison;
    }

    public int getJourInstallation() {
        return jourInstallation;
    }

    public int getFirstDay() {
        return firstDay;
    }

    public int getLastDay() {
        return lastDay;
    }

    public void setJourLivraison(int jourLivraison) {
        this.jourLivraison = jourLivraison;
    }

    public void setJourInstallation(int jourInstallation) {
        this.jourInstallation = jourInstallation;
    }

    @Override
    public String toString() {
        return "Request{id="+id+", Client="+client.getId()+", Machine="+nbMachine+" type "+idMachine+", delais entre"+firstDay+" et "+lastDay+"}";
    }
}
