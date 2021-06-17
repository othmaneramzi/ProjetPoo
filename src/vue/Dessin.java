/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import instance.Request;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import solution.Solution;
import solution.Tournee;
import solution.TourneeTech;
import vue.Solutions;

/**
 *
 * @author othma
 */
public class Dessin extends JPanel {
Solution solution;

    public Dessin (Solution s ){
        solution = s;
        Graphics2D gr;
    }

    @Override
    public void paintComponent(Graphics g) {
         Graphics2D gr = (Graphics2D) g;
        gr.setColor(Color.white);
      
        System.out.println("gg");
        Map<Integer,LinkedList<Tournee>> liste = solution.getListeTournees();
			for (int i = 1; i <= solution.getInstance().getDays(); i++) {
			
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
				
				int idTruck = 1;
				for (Tournee tournee : truck) {
					String chaine = "" + idTruck++;
					LinkedList<Request> requests = tournee.getListRequest();
					for (Request request : requests) {
						chaine += " " + request.getId();
                                                gr.setColor(Color.GREEN);
                                                gr.fillOval(request.getClient().getX(), request.getClient().getY(), 5, 5);
					}
					
				}
				
				for (Tournee tournee : tech) {
					LinkedList<Request> requests = tournee.getListRequest();
					String chaine = "" + ((TourneeTech) tournee).getTechnician().getIdTechnician();
					for (Request request : requests) {
						chaine += " " + request.getId();
                                                 gr.setColor(Color.red);
                                                 gr.fillOval(request.getClient().getX(), request.getClient().getY(), 5, 5);
					}
					
				}
			}
    }

    
   
   
     
   
    
}
