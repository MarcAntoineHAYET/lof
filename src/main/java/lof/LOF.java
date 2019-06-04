package lof;

import java.util.ArrayList;

import org.javatuples.Pair;

import manhattan.Manhattan;

public class LOF {
	
	private int k;
	private double distanceAtteignabilite;
	private double distanceAtteignabiliteDensity;
	private Manhattan manhattan;
	
	public LOF() {
		this.manhattan = new Manhattan();
	}
	
	public LOF(int k) {
		this.manhattan = new Manhattan();
		this.k = k;
	}
	
	
	/**
	 * Permet de récupérer les k plus proches voisins.
	 * 
	 * @param distances
	 * @return kPlusProchesVoisins
	 */
	public ArrayList<Pair<Point, Integer>> recupererKPLusProchesVoisins(ArrayList<Pair<Point, Integer>> distances){
		ArrayList<Pair<Point, Integer>> kPlusProchesVoisins = new ArrayList<Pair<Point, Integer>>();
		for(int x = 0; x < k; x++) {
			kPlusProchesVoisins.add(distances.get(x));
		}
		return kPlusProchesVoisins;
	}
	
	/**
	 * Permet de récupérer la distance de Manhattan du plus proche voisin du point passé en paramètre.
	 * @param data
	 * @return plusProcheVoisin
	 */
	public Pair<Point, Integer> recupererDistanceDuPlusProcheVoisin(Point point, ArrayList<Point> datas) {
		ArrayList<Pair<Point, Integer>> distancesManhattan = recupererDistancesManhattan(point, datas);
		Pair<Point, Integer> plusProcheVoisin = recupererPlusProcheVoisin(distancesManhattan);
		return plusProcheVoisin;
	}
	
	/**
	 * Permet de récupérer le plus proche voisin.
	 * 
	 * @param distancesManhattan
	 * @return plusProcheVoisin
	 */
	public Pair<Point, Integer> recupererPlusProcheVoisin(ArrayList<Pair<Point, Integer>> distancesManhattan){
		return distancesManhattan.get(k - 1);
	}
	
	/**
	 * Permet de récupérer les distances de Manhattan.
	 * 
	 * @param data
	 * @param datas
	 * @return distancesManhattan
	 */
	public ArrayList<Pair<Point, Integer>> recupererDistancesManhattan(Point point, ArrayList<Point> points){
		ArrayList<Pair<Point, Integer>> distancesManhattan = new ArrayList<Pair<Point, Integer>>();
		for(Point p : points) {
			if (point != p) {
				int distanceManhattan = manhattan.calculerDistanceManhattan(point.getX(), point.getY(), p.getX(), p.getY());
				Pair<Point, Integer> calcul = Pair.with(p, distanceManhattan);
				distancesManhattan.add(calcul);
			}
		}
		return distancesManhattan;
	}
	
	/**
	 * Permet de calculer les distances de Manhattan de chacun des points.
	 *  
	 * @param points
	 * @return distancesManhattan
	 */
	public ArrayList<Pair<Point, Integer>> recupererDistancesManhattan(ArrayList<Point> points){
		ArrayList<Pair<Point, Integer>> distancesManhattan = new ArrayList<Pair<Point, Integer>>();
		int distanceManhattan;
		for(int x = 0; x < points.size(); x++) {
			for(int y = x + 1; y < points.size(); y++) {
				Point a = points.get(x);
				Point b = points.get(y);
				distanceManhattan = manhattan.calculerDistanceManhattan(a.getX(), a.getY(), b.getX(), b.getY());
				Pair<Point, Integer> calcul = Pair.with(points.get(x), distanceManhattan);
				distancesManhattan.add(calcul);
			}
		}
		return distancesManhattan;
	}
	
	/**
	 * Permet de calculer la densité d'accessibilité locale.
	 * 
	 * @param k
	 * @param distancesAtteignabilites
	 * @return densiteAccessibiliteLocale
	 */
	public double calculerDensiteAtteignabiliteLocale(int k, ArrayList<Integer> distancesAtteignabilites) {
		int atteignabiliteLocale = 0;
		for(int distanceAtteignabilite : distancesAtteignabilites) {
			atteignabiliteLocale = atteignabiliteLocale + distanceAtteignabilite;
		}
		return (double) k / atteignabiliteLocale;
	}
	
	/**
	 * Permet de calculer la distance d'atteignabilité d'un point.
	 * 
	 * @param distanceDuPlusProcheVoisinK
	 * @param distanceManhattan
	 * @return distanceAtteignabilite
	 */
	public int calculerDistanceAtteignabilite(int distanceDuPlusProcheVoisinK, int distanceManhattan) {
		int distanceAtteignabilite;
		if (distanceDuPlusProcheVoisinK > distanceManhattan) {
			distanceAtteignabilite = distanceDuPlusProcheVoisinK;
		} else {
			distanceAtteignabilite = distanceManhattan;
		}
		return distanceAtteignabilite;
	}
	
	/**
	 * Permet d'afficher les distances de Manhattan calculées pour chacun des points.
	 * 
	 * @param distancesManhattan
	 */
	public void afficherDistancesManhattan(ArrayList<Pair<Point, Integer>> distancesManhattan) {
		for(Pair<Point, Integer> distanceManhattan : distancesManhattan) {
			System.out.println(distanceManhattan.getValue0().getX() + " " +  distanceManhattan.getValue0().getY() + "  " + distanceManhattan.getValue1());
		}
	}
	
	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public double getDistanceAtteignabilite() {
		return distanceAtteignabilite;
	}

	public void setDistanceAtteignabilite(double distanceAtteignabilite) {
		this.distanceAtteignabilite = distanceAtteignabilite;
	}

	public double getDistanceAtteignabiliteDensity() {
		return distanceAtteignabiliteDensity;
	}

	public void setDistanceAtteignabiliteDensity(double distanceAtteignabiliteDensity) {
		this.distanceAtteignabiliteDensity = distanceAtteignabiliteDensity;
	}
}
