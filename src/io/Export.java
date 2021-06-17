package io;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;

import instance.Instance;
import instance.Request;
import network.Depot;

import java.io.File;

import solution.Solution;
import solution.Tournee;
import solution.TourneeTech;

/** This class allows you to export a solution into a txt file **/
public class Export {
	public Solution solution;
	
	public Export(Solution solution){
		this.solution = solution;
	}
	
	public boolean ExporterSolution(String instanceName){
		File monFichier = new File("C:\\Users\\othma\\OneDrive\\Bureau\\solution.txt");
		try{
			if(monFichier.createNewFile())
				System.out.println("cr�ation du fichier solution.txt");
			else
				System.out.println("modification du fichier solution.txt");
			PrintWriter writer = new PrintWriter(monFichier);
			writer.println("DATASET = "+ solution.getInstance().getDataset());
			writer.println("NAME = "+ solution.getInstance().getNom());
			writer.println();
			writer.println("TRUCK_DISTANCE = " + solution.getTruckDistance());
			writer.println("NUMBER_OF_TRUCK_DAYS = " + solution.getNumberTruckDays());
			writer.println("NUMBER_OF_TRUCKS_USED = " + solution.getNumberTrucksUsed());
			writer.println("TECHNICIAN_DISTANCE = " + solution.getTechnicianDistance());
			writer.println("NUMBER_OF_TECHNICIAN_DAYS = " + solution.getNumberTechnicianDays());
			writer.println("NUMBER_OF_TECHNICIANS_USED = " + solution.getNumberTechniciansUsed());
			writer.println("IDLE_MACHINE_COSTS = "+solution.getMachineCost());
			writer.println("TOTAL_COST = "+solution.getCoutTotal());
			writer.println();
			Map<Integer,LinkedList<Tournee>> liste = solution.getListeTournees();
			for (int i = 1; i <= solution.getInstance().getDays(); i++) {
				writer.println("DAY = " + i);
				LinkedList<Tournee> tournees = liste.get(i);
				LinkedList<Tournee> truck = new LinkedList<>();
				int nbTruck = 0;
				LinkedList<Tournee> tech = new LinkedList<>();
				LinkedList<Integer> technician = new LinkedList<>();
				int nbTech = 0;
				if(tournees != null) {
					for (Tournee t : tournees) {
						if(!t.isEmpty()) {
							if (t instanceof TourneeTech) {
								tech.add(t);
								if (!technician.contains(((TourneeTech) t).getTechnician().getIdTechnician())) {
									technician.add(((TourneeTech) t).getTechnician().getIdTechnician());
									nbTech++;
								}
							} else {
								truck.add(t);
								nbTruck++;
							}
						}
					}
				}
				writer.println("NUMBER_OF_TRUCKS = " + nbTruck);
				int idTruck = 1;
				for (Tournee tournee : truck) {
					String chaine = "" + idTruck++;
					LinkedList<Request> requests = tournee.getListRequest();
					for (Request request : requests) {
						chaine += " " + request.getId();
					}
					writer.println(chaine);
				}
				writer.println("NUMBER_OF_TECHNICIANS = " + nbTech);
				for (Tournee tournee : tech) {
					LinkedList<Request> requests = tournee.getListRequest();
					String chaine = "" + ((TourneeTech) tournee).getTechnician().getIdTechnician();
					for (Request request : requests) {
						chaine += " " + request.getId();
					}
					writer.println(chaine);
				}
			}

			writer.close();
		}catch(Exception e){
			e.printStackTrace();
			System.err.println(e);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		try {
			Instance instance = new Instance("DATASET","NOM",500,10,new Depot(1,2,3),5,
			200,5,25,10,6,20);
			Solution sol = new Solution(instance);

			Export writer = new Export(sol);
			writer.ExporterSolution("solution");

			System.out.println("Solution ecrit avec success !");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}