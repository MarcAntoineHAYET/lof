package lof;

import java.util.ArrayList;

import org.javatuples.Pair;

import manhattan.Manhattan;

public class App {

	public static void main(String[] args) {
		LOF lof = new LOF();
		lof.setK(2);
		
		ArrayList<Point> points = new ArrayList<Point>();
		
		points.add(new Point(0, 0));
		points.add(new Point(0, 1));
		points.add(new Point(1, 1));
		points.add(new Point(3, 0));
		
		Manhattan manhattan = new Manhattan();
		
		for(Point point : points) 
		{
			System.out.println("Point : " + point + "\n");
			ArrayList<Pair<Point, Integer>> distancesManhattan = lof.recupererDistancesManhattan(point, points);
			
			System.out.println("Distance de Manhattan par rapport aux autres points : ");
			for(Pair<Point, Integer> distanceManhattan : distancesManhattan) {
				System.out.println(distanceManhattan.getValue0() + " " + distanceManhattan.getValue1());
			}
			
			Pair<Point, Integer> plusProcheVoisin = lof.recupererPlusProcheVoisin(distancesManhattan);
			System.out.println("Le k(" + lof.getK() + ") plus proche est : " + plusProcheVoisin.getValue0() + " avec une distance de " + plusProcheVoisin.getValue1());
			
			ArrayList<Pair<Point, Integer>> kPlusProchesVoisins = lof.recupererKPLusProchesVoisins(distancesManhattan);
			System.out.println("Les k(" + lof.getK() + ") plus proches voisins : ");
			
			for(Pair<Point, Integer> kPlusProcheVoisin : kPlusProchesVoisins) {
				System.out.println(kPlusProcheVoisin.getValue0());
			}
			
			ArrayList<Integer> distancesAtteignabilites = new ArrayList<Integer>();
			
			for(Pair<Point, Integer> kPlusProcheVoisin : kPlusProchesVoisins) {
				Pair<Point, Integer> distanceDuPlusProcheVoisin = lof.recupererDistanceDuPlusProcheVoisin(kPlusProcheVoisin.getValue0(), points);
				int distanceAtteignabilite = lof.calculerDistanceAtteignabilite(distanceDuPlusProcheVoisin.getValue1().intValue(), manhattan.calculerDistanceManhattan(point.getX(), point.getY(), kPlusProcheVoisin.getValue0().getX(), kPlusProcheVoisin.getValue0().getY()));
				distancesAtteignabilites.add(distanceAtteignabilite);
			}
			
			System.out.println("La densité d'atteignabilité locale (Local Reachability Density) du point " + point + " est : " + lof.calculerDensiteAtteignabiliteLocale(2, distancesAtteignabilites) + "\n");
		}
	}
}
