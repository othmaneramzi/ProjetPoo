/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import instance.Request;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import solution.Solution;
import solution.Tournee;
import solution.TourneeTech;
import solution.TourneeTruck;

/**
 *
 * @author othma
 */
public class GraphiqueVisuel extends JFrame {
    
    private static final long serialVersionUID = 1L;  
    public GraphiqueVisuel(Solution s){
        remplirGraphique(s);
    }
    
    public void remplirGraphique(Solution s){
         SwingUtilities.invokeLater(() -> {  
         GraphiqueVisuel example = new GraphiqueVisuel("Graphique", s);
         example.setExtendedState(JFrame.MAXIMIZED_BOTH);
         example.setLocationRelativeTo(null);
         example.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  
         example.setVisible(true);});
    }
    
     public GraphiqueVisuel(String title, Solution s) {  
      super(title);  
       IntervalCategoryDataset dataset = getCategoryDataset(s);  
      // Create dataset  
       JFreeChart chart = ChartFactory.createGanttChart(  
            "Graphique", // Chart title  
            "tournees", // X-Axis Label  
            "jours", // Y-Axis Label  
            dataset);  
       ChartPanel panel = new ChartPanel(chart);  
        setContentPane(panel);  
      
   }  
     
       private IntervalCategoryDataset getCategoryDataset(Solution s) { 
           
           TaskSeriesCollection tasks = new TaskSeriesCollection();
           Map<Integer,LinkedList<Tournee>> liste = s.getListeTournees();
           LocalDate ddT = LocalDate.of(2021, 01, 01);
           LocalDate dfT = ddT.plusDays(1);
          
           
        for (int i = 1; i <= s.getInstance().getDays(); i++) {
				 TaskSeries t1 = new TaskSeries("Tournee truck jour"+i);
                                 TaskSeries t2 = new TaskSeries("Tournee tech jour "+i);
				LinkedList<Tournee> tournees = liste.get(i);
				LinkedList<Tournee> truck = new LinkedList<>();
				int nbTruck = 0;
				LinkedList<Tournee> tech = new LinkedList<>();
				LinkedList<Integer> technician = new LinkedList<>();
				int nbTech = 0;
				if(tournees != null) {
					for (Tournee t : tournees) {
						if (t instanceof TourneeTech) {
							tech.add(t);
							if(!technician.contains(((TourneeTech) t).getTechnician().getIdTechnician())){
								technician.add(((TourneeTech) t).getTechnician().getIdTechnician());
								nbTech++;
							}
						} else {
							truck.add(t);
							nbTruck++;
						}
					}
				}
				
				int idTruck = 1;
				for (Tournee tournee : truck) {
                                         t1.add(new Task("tournee Truck:"+ idTruck, Date.from(ddT.atStartOfDay().toInstant(ZoneOffset.ofHours(00))), Date.from(dfT.atStartOfDay().toInstant(ZoneOffset.ofHours(00)))));
                                         idTruck++;
                                }
			
				for (Tournee tournee : tech) {
					LinkedList<Request> requests = tournee.getListRequest();
                                        t2.add(new Task("tournee Tech:"+ ((TourneeTech) tournee).getTechnician().getIdTechnician(), Date.from(ddT.atStartOfDay().toInstant(ZoneOffset.ofHours(00))), Date.from(dfT.atStartOfDay().toInstant(ZoneOffset.ofHours(00)))));
                                         
					for (Request request : requests) {
						
					}
					
				}
                               
                                         ddT = dfT;
                                         dfT=ddT.plusDays(1);
          tasks.add(t1);
          tasks.add(t2);
			}
          
          
           return tasks;
       }
}

