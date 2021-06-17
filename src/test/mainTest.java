package test;

import instance.Instance;
import io.Import;
import io.Export;
import io.exception.ReaderException;
import solution.Solution;
import solveur.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class mainTest {
    public static void main(String[] args) {
        try {
            Import reader = new Import("C:\\Users\\othma\\OneDrive\\Bureau\\instances\\VSC2019_ORTEC_early_01_easy.txt");
            Instance i = reader.readInstance();
            System.out.println("Instance lue avec success !");
            System.out.println("Num request = " + i.getRequests().size());
            System.out.println("Num clients = " + i.getNbClients());
            System.out.println("Num tech = " + i.getTechs().size());

            System.out.println(i);

            Solveur solvA = new Ajout();
            Solveur solvT = new Trivial();
            Solveur solvPPV = new InsertionPPV();
            Solveur solvRecheLocale = new RechercheLocale();

            Solution solu;

            solu = solvT.solve(i);
            System.out.println("Trivial " + solu.getCoutTotal());

            solu = solvA.solve(i);
            System.out.println("Ajout " + solu.getCoutTotal());

            solu = solvPPV.solve(i);
            System.out.println("PPV " + solu.getCoutTotal());

            solu = solvRecheLocale.solve(i);
            System.out.println("RechercheLocale " + solu.getCoutTotal());

            Export exp = new Export(solu);
            exp.ExporterSolution("solution");


        } catch (
        ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}