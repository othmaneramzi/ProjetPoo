/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import instance.Instance;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import solution.Solution;
import solveur.Ajout;
import solveur.InsertionPPV;
import solveur.RechercheLocale;
import solveur.Solveur;
import solveur.Trivial;


/**
 *
 * @author othma
 */
public class SolutionCompare extends JFrame {
    
    
    
    public SolutionCompare(Instance i){
        
        remplirChart(i);
    }
    
    public void remplirChart(Instance i){
        SolutionCompare chart = new SolutionCompare("Comparaison des couts", 
         "Quelle est la solution optimale?",i);
      chart.pack( );        
         
      chart.setVisible( true ); 
    }
    public SolutionCompare( String applicationTitle , String chartTitle , Instance i){
         super( applicationTitle );        
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "",            
         "Cout Total",            
         createDataset(i),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
      setContentPane( chartPanel ); 
    }
    
     private CategoryDataset createDataset(Instance i) {
     final String trivial = "trivial";
     final String ajout = "ajout";
     final String ppv = "Plus proche voisin"  ;
     final String rechercheLocale = "Recherche Locale";
     final String solution = "Axe des solutions";
     
      Solveur solvT = new Trivial();
      Solveur solvA = new Ajout();
      Solveur solvPPV = new InsertionPPV();
      Solveur solvRL = new RechercheLocale();
     Solution s1 = solvT.solve(i);
     Solution s2 = solvA.solve(i);
     Solution s3 = solvPPV.solve(i);
     Solution s4 = solvRL.solve(i);
     final DefaultCategoryDataset dataset =new DefaultCategoryDataset( );  
     
     dataset.addValue(s1.getCoutTotal(),trivial,solution);
     dataset.addValue(s2.getCoutTotal(),ajout,solution);
     dataset.addValue(s3.getCoutTotal(),ppv,solution);
     dataset.addValue(s4.getCoutTotal(),rechercheLocale,solution);
                 

      return dataset; 
   }
    
}
