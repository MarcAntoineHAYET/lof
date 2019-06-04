package lof;

import java.util.ArrayList;

import org.javatuples.Pair;

import kmeans.Data;
import kmeans.Kmeans;
import manhattan.Manhattan;

public class App {

	private static double datasBrutes[][] = { { 8.0, 6 }, { 10.0, 8 }, { 12.0, 10 }, { 16, 12 }, { 17, 14 }, { 16, 16 },
			{ 14, 18 }, { 12, 20 }, { 10, 22 }, { 9, 0 }, { 8, 2 }, { 7, 4 }, { 300, 12 } };

	public static void main(String[] args) {
		Kmeans kmeans = new Kmeans();
		LOF lof = new LOF();
		lof.setK(2);
		
		Manhattan manhattan = new Manhattan();

		ArrayList<Data> datas = new ArrayList<Data>();
		
		datas = kmeans.transformerDatas(datasBrutes);
		datas = kmeans.normaliserValeursBrutes(datas);
		datas = kmeans.calculerPositions(datas);
		
		/*
		ArrayList<Pair<Data, Double>> distancesEuclidiennes = lof.recupererDistancesEuclidiennes(datas);
		ArrayList<Pair<Data, Integer>> distancesManhattan = lof.recupererDistancesManhattan(datas);
		
		lof.afficherDistancesEuclidiennes(distancesEuclidiennes);	
		lof.afficherDistancesManhattan(distancesManhattan);
		
		*/
		for(Data data : datas) {
			System.out.println("Point : " + data.getPosition());
			ArrayList<Pair<Data, Integer>> distancesManhattan = lof.recupererDistancesManhattan(data, datas);
			
			for(Pair<Data, Integer> distanceManhattan : distancesManhattan) {
				System.out.println(distanceManhattan.getValue0().getPosition() + " " + distanceManhattan.getValue1());
			}
			
			Pair<Data, Integer> plusProcheVoisin = lof.recupererPlusProcheVoisin(distancesManhattan);
			System.out.println("Le k plus proche est : " + plusProcheVoisin.getValue0().getPosition() + " avec une distance de " + plusProcheVoisin.getValue1());
			
			ArrayList<Pair<Data, Integer>> kPlusProchesVoisins = lof.recupererKPLusProchesVoisins(distancesManhattan);
			System.out.println("Les k (" + lof.getK() + ") plus proches voisins : " + kPlusProchesVoisins);
			
			for(Pair<Data, Integer> kPlusProcheVoisin : kPlusProchesVoisins) {
				System.out.println(kPlusProcheVoisin.getValue0().getPosition());
			}
			
			ArrayList<Integer> distancesAtteignabilites = new ArrayList<Integer>();
			
			for(Pair<Data, Integer> kPlusProcheVoisin : kPlusProchesVoisins) {
				Pair<Data, Integer> distanceDuPlusProcheVoisin = lof.recupererDistanceDuPlusProcheVoisin(kPlusProcheVoisin.getValue0(), datas);
				int distanceAtteignabilite = lof.calculerDistanceAtteignabilite(distanceDuPlusProcheVoisin.getValue1().intValue(), manhattan.calculerDistanceManhattan(data.getPosition().getX(), data.getPosition().getY(), kPlusProcheVoisin.getValue0().getPosition().getX(), kPlusProcheVoisin.getValue0().getPosition().getY()));
				distancesAtteignabilites.add(distanceAtteignabilite);
			}
			
			System.out.println("La LDR du point " + data.getPosition() + " : " + lof.calculerDensiteAtteignabiliteLocale(data, 2, distancesAtteignabilites));
		}
	}
}
