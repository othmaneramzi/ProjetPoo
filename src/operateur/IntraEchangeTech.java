package operateur;

import solution.Tournee;

public class IntraEchangeTech extends OperateurIntraTournee {

	public IntraEchangeTech() {
		super();
	}
	
	public IntraEchangeTech(Tournee t, int pos1, int pos2){
		super(t,pos1,pos2);
	}
	
	@Override
	protected int evalDeltaCout() {
		return tournee.deltaCoutEchange(positionI, positionJ);
	}

	@Override
	protected boolean doMovement() {
		return tournee.doEchangeTech(this);
	}

	@Override
	public String toString() {
		return "IntraEchangeTech[ Client1 = "+requestI.getClient().getId()+
				", position1 = "+positionI+", Client2 = "+requestJ.getClient().getId()+
				", position2 = "+positionJ+", cout = "+deltaCout+"]";
	}
	
}
