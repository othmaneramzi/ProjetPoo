package solution;

import instance.Instance;
import instance.Request;
import network.Client;
import network.Depot;
import network.Point;
import operateur.*;

import java.util.Collections;
import java.util.Currency;
import java.util.LinkedList;


public abstract class Tournee {
    /*
     * PARAMETRE
     */
    protected static int incID;
    protected int id;
    protected int coutTotal;
    protected Instance instance;
    protected Point depot;
    protected int jour;
    protected final LinkedList<Request> listRequest;

    /*
     * CONSTRUCTEUR
     */

    public Tournee(Tournee t) {
        coutTotal = t.coutTotal;
        instance = t.instance;
        depot = t.depot;
        jour = t.jour;
        listRequest = new LinkedList<>(t.listRequest);
        id = incID;
    }

    public Tournee() {
        this.coutTotal = 0;
        this.instance = null;
        this.listRequest = new LinkedList<Request>();
    }

    public Tournee(Instance instance, int jour) {
        this.coutTotal = 0;
        this.listRequest = new LinkedList<Request>();
        this.jour = jour;
        this.instance = instance;
    }
    /*
     * METHODES
     */

    public int getJour(){
        return jour;
    }

    public int getCoutTotal() {
        return coutTotal;
    }

    public LinkedList<Request> getListRequest() {
        return listRequest;
    }

    public boolean isEmpty() {
        return listRequest.isEmpty();
    }

    public Point getDepot(){
        return this.depot;
    };

    public int getId(){
        return id;
    }

    /**
     * Fonction qui renvoie le client à la position pos de la liste de clients
     * @param pos la position du client à récupérer
     * @return le client à la position pos ou null
     */
    public Request getRequestAt(int pos) {
        if (pos < 0 || pos >= listRequest.size()) return null;
        return listRequest.get(pos);
    }

    /**
     * Fonction qui ajoute un client à la tournée
     * @param request le client à ajouter à la tournée
     * @return true si l'ajout a pu se faire et false sinon
     */
    public abstract boolean ajouterRequest(Request request);

    /**
     * Fonction testant la possibilité d'insérer un client
     * @param request le client à insérer
     * @return true si le client peut être inséré et false sinon
    */
    public abstract boolean canInsererRequest(Request request);

    public abstract boolean check();

    public abstract boolean checkAjoutDistance(int delta);

    /**
     * Fonction qui vérifie si une position est valide
     * @param pos la position à tester
     * @return true si la position est valide et false sinon
     */
    public boolean isPositionvalide(int pos) {
        return pos >= 0 && pos < listRequest.size();
    }

    public int calculCoutAjoutRequest(Request request){
        // considére que la requette est ajouté en derniére position, à update plus tard pour prendre en compte une position n
        int delta=0;
        if(listRequest.isEmpty()){
            delta += depot.getCoutVers(request.getClient())*2;
        }
        else {
            //System.out.println("else");
            delta += listRequest.get(listRequest.size() - 1).getClient().getCoutVers(request.getClient());
            delta -= listRequest.get(listRequest.size() - 1).getClient().getCoutVers(depot); // :friJudge:
            delta += request.getClient().getCoutVers(depot);
        }
        //System.out.println("delta ="+delta);
        return delta;
    }

    /**
     * Fonction qui calcule le coût total en itérant sur tous les clients et vérifie s'il correspond au deltaCout total de la
     * tournée
     * @return true si le coût total théorique correspond au coût total effectif
     */
    private boolean checkCalculerCoutTotal() {
        int cTotal = 0;
        for (int i = 0; i < listRequest.size() - 1; i++) {
            // On calcul les liens entre tous les clients
            cTotal += listRequest.get(i).getClient().getCoutVers(listRequest.get(i+1).getClient());
        }
        // On ajoute les liens entre le premier et dernier points au dépôt
        cTotal += depot.getCoutVers(listRequest.getFirst().getClient()) + listRequest.getLast().getClient().getCoutVers(depot);

        boolean test = cTotal == coutTotal;
        if (!test)
            System.out.println("Erreur Test checkCalculerCoutTotal:\n\tcoût total théorique: " + cTotal +
                    "\n\tcoût effectif: " + coutTotal);

        return test;
    }

    /**
     * Fonction qui renvoie le point de la tournée qui précède la position pos
     * @param pos la position à interroger
     * @return le point en position pos-1, si pos <= 0 il s'agit du dépot
     */
    private Point getPrec(int pos) {
        if (pos < 0 || pos > listRequest.size()) return null;
        if (pos == 0) return depot;
        return listRequest.get(pos-1).getClient();
    }

    /**
     * Fonction qui renvoie le point de la tournée qui est en position pos
     * @param pos la position à interroger
     * @return le point en position pos, si pos >= taille listeClients ils s'agit du dépot
     */
    private Point getCurrent(int pos) {
        if (pos < 0 || pos > listRequest.size()) return null;
        if (pos == listRequest.size()) return depot;
        return listRequest.get(pos).getClient();
    }

    /**
     * Fonction qui renvoie le point suivant la position pos
     * @param pos la position actuelle
     * @return le point en position pos+1 si pos est la position du dernier client alors pos+1 est le dépôt
     */
    private Point getNext(int pos) {
        if (pos+1 <= 0 || pos+1 > listRequest.size()) return null;
        if (pos+1 == listRequest.size()) return depot;
        return listRequest.get(pos+1).getClient();
    }

    /**
     * Fonction qui teste si une position est valide pour une insertion
     * @param pos la position où insérer
     * @return true si la position est possible et false sinon
     */
    private boolean isPositionInsertionValide(int pos) {
        return getPrec(pos) != null && getCurrent(pos) != null;
    }

    private boolean doublePositionValide(int pI, int pJ) {
        return isPositionvalide(pI) && isPositionvalide(pJ) && pI != pJ && Math.abs(pI - pJ) != 1;
    }

    public int deltaCoutDeplacementTech(int pI, int pJ) {
        return deltaCoutDeplacementTruck(pI, pJ); // ara ara
    }

    public boolean doDeplacementTech(IntraDeplacementTech infos) {
        if (infos == null || !infos.isMouvementRealisable()) return false;
        int i = infos.getPositionI(); int j = infos.getPositionJ();
        if (!doublePositionValide(i, j)) return false;

        Request r = infos.getRequestI();

        listRequest.remove(i);
        if (i < j)
            j--;
        listRequest.add(j, r);

        coutTotal += infos.getDeltaCout();

        return true;
    }

    public int deltaCoutDeplacementTruck(int posI, int posJ){
        if(doublePositionValide(posI,posJ)){
            return getCurrent(posI).getCoutVers(getCurrent(posJ))
                    +getPrec(posJ).getCoutVers(getCurrent(posI))
                    +getPrec(posI).getCoutVers(getNext(posI))
                    -getPrec(posI).getCoutVers(getCurrent(posI))
                    -getCurrent(posI).getCoutVers(getNext(posI))
                    -getPrec(posJ).getCoutVers(getCurrent(posJ));
        }
        return Integer.MAX_VALUE;
    }

    public boolean doDeplacementTruck(IntraDeplacementTruck infos) {
        if (infos == null || !infos.isMouvementRealisable()) return false;
        int i = infos.getPositionI(); int j = infos.getPositionJ();
        if (!doublePositionValide(i, j)) return false;

        Request r = infos.getRequestI();

        listRequest.remove(i);
        if (i < j)
            j--;
        listRequest.add(j, r);

        coutTotal += infos.getDeltaCout();

        return true;
    }

    public boolean doEchangeTech(IntraEchangeTech infos){
        if(infos == null || !infos.isMouvementRealisable()){
            return false;
        }
        int i = infos.getPositionI();
        int j = infos.getPositionJ();
        if(!isPositionvalide(i) || !isPositionvalide(j)){
            return false;
        }
        Collections.swap(listRequest, i, j);
        coutTotal += infos.getDeltaCout();
        return true;
    }


    public boolean doEchangeTruck(IntraEchangeTruck infos){
        if(infos == null || !infos.isMouvementRealisable()){
            return false;
        }
        int i = infos.getPositionI();
        int j = infos.getPositionJ();
        if(!isPositionvalide(i) || !isPositionvalide(j)){
            return false;
        }
        Collections.swap(listRequest, i, j);
        coutTotal += infos.getDeltaCout();
        return true;
    }


    public int deltaCoutEchange(int positionI,int positionJ){
        if(!isPositionvalide(positionI) || !isPositionvalide(positionJ)){
            return Integer.MAX_VALUE;
        }
        if(positionI == positionJ){
            return 0;
        }
        if(positionJ < positionI){
            return deltaCoutEchange(positionJ, positionI);
        }
        if(positionI+1 == positionJ){
            return deltaCoutEchangeConsecutif(positionI);
        }
        int delta = deltaCoutRemplacement(positionI, listRequest.get(positionJ)) + deltaCoutRemplacement(positionJ, listRequest.get(positionI));
        if(checkAjoutDistance(delta))
            return delta;
        return Integer.MAX_VALUE;
    }

    public int deltaCoutEchangeConsecutif(int position){
        Point current = getCurrent(position);
        Point jPoint = getCurrent(position+1);
        Point precI = getPrec(position);
        Point nextJ = getNext(position+1);
        return precI.getCoutVers(jPoint)+current.getCoutVers(nextJ)-precI.getCoutVers(current)-jPoint.getCoutVers(nextJ);
    }

    public int deltaCoutRemplacement(int position,Request request){
        Point current = getCurrent(position);
        Point prec = getPrec(position);
        Point next = getNext(position);
        return prec.getCoutVers(request.getClient())+request.getClient().getCoutVers(next)-prec.getCoutVers(current)-current.getCoutVers(next);
    }

    public int deltaCoutSuppression(int position){
        if(isPositionvalide(position)) {
            Point current = getCurrent(position);
            Point prec = getPrec(position);
            Point next = getNext(position);
            return prec.getCoutVers(next) - prec.getCoutVers(current) - current.getCoutVers(next);
        }
        return Integer.MAX_VALUE;
    }


    /**
     * Fonction qui renvoie le meilleur opérateur intra tournée
     * @param type le type d'opérateur
     * @return le meilleur opérateur de ce type
     */
    public OperateurIntraTournee getMeilleurOperateurIntra(TypeOperateurLocal type) {
        OperateurIntraTournee meilleur = (OperateurIntraTournee) OperateurLocal.getOperateur(type);

        for (int i = 0; i < listRequest.size(); i++) {
            for (int j = 0; j < listRequest.size(); j++) {
                if (i == j) continue;
                OperateurIntraTournee op = (OperateurIntraTournee) OperateurLocal.getOperateurIntra(type, this, i, j);
                if (op.isMeilleur(meilleur))
                    meilleur = op;
            }
        }

        return meilleur;
    }

    /**
     * Fonction qui renvoie le meilleur opérateur inter tournée
     * @param autreTournee l'autre tournée
     * @param type le type d'opérateur
     * @return le meilleur opérateur de ce type
     */
    public OperateurInterTournee getMeilleurOperateurInter(Tournee autreTournee, TypeOperateurLocal type) {
        OperateurInterTournee meilleur = (OperateurInterTournee) OperateurLocal.getOperateur(type);

        for (int i = 0; i < listRequest.size(); i++) {
            for (int j = 0; j < autreTournee.listRequest.size(); j++) {
                OperateurInterTournee op = (OperateurInterTournee) OperateurLocal.getOperateurInter(type, this, autreTournee, i, j);
                if (op.isMeilleur(meilleur))
                    meilleur = op;
            }
        }

        return meilleur;
    }

    public int getCoutTruckDistance(){
        return instance.getTruckDistanceCost();
    }

    public int getCoutTechDistance(){
        return instance.getTechnicianDistanceCost();
    }

    public int deltaCoutInsertionInterTruck(int position, Request request){
        if(this instanceof TourneeTruck) {
            TourneeTruck tourneeTruck = (TourneeTruck) this;

            if(jour < request.getFirstDay() || jour > request.getLastDay()-1)
                return Integer.MAX_VALUE;
            if(jour >= request.getJourInstallation())
                return Integer.MAX_VALUE;

            int tailleMachine = instance.getMapMachines().get(request.getIdMachine()).getSize();
            if(tourneeTruck.getCapacity()+request.getNbMachine()*tailleMachine > tourneeTruck.getMaxCapacity())
                return Integer.MAX_VALUE;
            int cout = deltaCoutInsertion(position,request);

            if(tourneeTruck.getDistance()+cout > tourneeTruck.getMaxDistance())
                return Integer.MAX_VALUE;
            return cout;
        }else
            return Integer.MAX_VALUE;
    }


    public int deltaCoutInsertionInterTech(int position, Request request){
        if(this instanceof TourneeTech) {
            TourneeTech tourneeTech = (TourneeTech) this;

            if(jour < request.getFirstDay()+1 || jour > request.getLastDay())
                return Integer.MAX_VALUE;
            if(jour <= request.getJourLivraison())
                return Integer.MAX_VALUE;

            if(!tourneeTech.getTechnician().canInstallerMachine(request.getIdMachine()))
                return Integer.MAX_VALUE;
            //int tailleMachine = instance.getMapMachines().get(request.getIdMachine()).getSize();
            if(tourneeTech.getTechnician().getDemande(jour)+request.getNbMachine() > tourneeTech.getTechnician().getMaxDemande())
                return Integer.MAX_VALUE;
            int cout = deltaCoutInsertion(position,request);

            if(tourneeTech.getTechnician().getDistance(jour)+cout > tourneeTech.getTechnician().getMaxDistance())
                return Integer.MAX_VALUE;
            return cout;
        }else
            return Integer.MAX_VALUE;
    }

    public int deltaCoutInsertion(int position,Request request){
        if(!isPositionInsertionValide(position)){
            return Integer.MAX_VALUE;
        }
        Point client = request.getClient();
        if(listRequest.isEmpty()){
            return 2 * client.getCoutVers(depot);
        }
        Point prec = getPrec(position);
        Point pos = getCurrent(position);
        return prec.getCoutVers(client)+client.getCoutVers(pos)-prec.getCoutVers(pos);
    }

    public boolean doDeplacementTruck(InterDeplacementTruck infos){
        if(infos == null){
            return false;
        }
        if(infos.isMouvementRealisable()){
            Request i = infos.getRequestI();
            Tournee t2 = infos.getAutreTournee();
            int posi = infos.getPositionI();
            int posj = infos.getPositionJ();
            t2.coutTotal += infos.getDeltaCoutAutreTournee();
            coutTotal += infos.getDeltaCoutTournee();

            ((TourneeTruck) t2).addDistance(infos.evalDeltaDistanceAutreTournee());
            ((TourneeTruck) this).addDistance(infos.evalDeltaDistanceTournee());

            int capacity = i.getNbMachine()*instance.getMapMachines().get(i.getIdMachine()).getSize();
            ((TourneeTruck) this).addCapacity(-i.getNbMachine()*instance.getMapMachines().get(i.getIdMachine()).getSize());
            ((TourneeTruck) t2).addCapacity(capacity);
            listRequest.remove(posi);
            t2.listRequest.add(posj, i);
            return true;
        }else{
            return false;
        }
    }

    public boolean doDeplacementTech(InterDeplacementTech infos){
        if(infos == null){
            return false;
        }
        if(infos.isMouvementRealisable()){
            Request i = infos.getRequestI();
            Tournee t2 = infos.getAutreTournee();
            int posi = infos.getPositionI();
            int posj = infos.getPositionJ();
            t2.coutTotal += infos.getDeltaCoutAutreTournee();
            coutTotal += infos.getDeltaCoutTournee();

            ((TourneeTech) t2).getTechnician().insererRequest(infos.getDeltaCoutAutreTournee(),jour);
            ((TourneeTech) this).getTechnician().retirerRequest(infos.getDeltaCoutTournee(),jour);

            listRequest.remove(posi);
            t2.listRequest.add(posj, i);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "Tournee{" +
                ", coutTotal=" + coutTotal +
                '}';
    }

    public int getCoutSuppDeplacementTruck(InterDeplacementTruck infos){
        int cout = 0;
        int i = infos.getPositionI();
        int j = infos.getPositionJ();
        TourneeTruck t1 = (TourneeTruck) infos.getTournee();
        TourneeTruck t2 = (TourneeTruck) infos.getAutreTournee();
        if(t1.jour != t2.jour){
            if(t2.jour >= t1.listRequest.get(i).getJourInstallation()){
                return Integer.MAX_VALUE;
            }else{
                cout += (t1.jour - t2.jour)*instance.getMapMachines().get(t1.listRequest.get(i).getIdMachine()).getPenalityCost();
            }
        }
        if(t1.listRequest.size() == 1){
            cout += -instance.getTruckDayCost();
        }
        return cout;
    }

    public int getCoutSuppDeplacementTech(InterDeplacementTech infos){
        int cout = 0;
        int i = infos.getPositionI();
        int j = infos.getPositionJ();
        TourneeTech t1 = (TourneeTech) infos.getTournee();
        TourneeTech t2 = (TourneeTech) infos.getAutreTournee();
        if(t1.jour != t2.jour){
            if(t2.jour <= t1.listRequest.get(i).getJourLivraison()){
                return Integer.MAX_VALUE;
            }else{
                cout += (t2.jour - t1.jour)*instance.getMapMachines().get(t1.listRequest.get(i).getIdMachine()).getPenalityCost();
            }
        }
        if(t1.listRequest.size() == 1){
            cout += -instance.getTechnicianDayCost();
            if(!((TourneeTech) this).getTechnician().isUsedAnotherDay(jour))
                cout += -instance.getTechnicianCost();
        }
        return cout;
    }
}