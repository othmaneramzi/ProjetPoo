package network;

import instance.Request;

import java.util.LinkedList;
import java.util.List;

public class Client extends Point{
    /**
     * PARAMETRES
     */
    private List<Request> listRequests;

    /**
     * CONSTRUCTEUR
     */
    public Client(int id, int x, int y){
        super(id, x, y);
        listRequests = new LinkedList<>();
    }

    /**
     * METHODES
     */
    public boolean ajouterRequest(Request request) {
        if (request == null || listRequests.contains(request))
            return false;

        return listRequests.add(request);
    }

    @Override
    public String toString() {
        return "Client{" +
                "point= "+ super.toString() +
                "listRequests=" + listRequests +
                "} ";
    }
}
