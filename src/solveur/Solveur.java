package solveur;

import instance.Instance;
import solution.Solution;

public interface Solveur {
    /**
     * Fonction qui renvoie le nom de la méthode de résolution sous forme de chaîne de caractères
      * @return le nom de la méthode de résolution
     */
    String getNom();

    /**
     * Fonction qui renvoie une solution pour l'instance passée en paramètre
     * @param instance l'instance dont on souhaite connaître la solution
     * @return la solution renvoyée par le solveur
     */
    Solution solve(Instance instance);
}
