package lof;

import java.util.ArrayList;

import org.javatuples.Pair;

import euclide.Euclide;
import kmeans.Data;
import kmeans.Position;
import manhattan.Manhattan;

public class LOF {
	
	private int k;
	private double distanceAtteignabilite;
	private double distanceAtteignabiliteDensity;
	private Euclide euclide;
	private Manhattan manhattan;
	
	public LOF() {
		this.euclide = new Euclide();
		this.manhattan = new Manhattan();
	}
	
	public LOF(int k) {
		this.euclide = new Euclide();
		this.manhattan = new Manhattan();
		this.k = k;
	}
	
	/**
	 * Permet de calculer les distances euclidiennes de chacun des points.
	 * 
	 * @param datas
	 * @return distancesEuclidiennes
	 */
	public ArrayList<Pair<Data, Double>> recupererDistancesEuclidiennes(ArrayList<Data> datas){
		ArrayList<Pair<Data, Double>> distancesEuclidiennes = new ArrayList<Pair<Data, Double>>();
		double distanceEuclidienne;
		for(int x = 0; x < datas.size(); x++) {
			for(int y = x + 1; y < datas.size(); y++) {
				Position a = datas.get(x).getPosition();
				Position b = datas.get(y).getPosition();
				distanceEuclidienne = euclide.calculerDistanceEuclidienne(a.getX(), a.getY(), b.getX(), b.getY());
				Pair<Data, Double> calcul = Pair.with(datas.get(x), distanceEuclidienne);
				distancesEuclidiennes.add(calcul);
			}
		}
		return distancesEuclidiennes;
	}
	
	/**
	 * Permet de calculer les distances de Manhattan de chacun des points.
	 * 
	 * @param datas
	 * @return distancesManhattan
	 */
	public ArrayList<Pair<Data, Integer>> recupererDistancesManhattan(ArrayList<Data> datas){
		ArrayList<Pair<Data, Integer>> distancesManhattan = new ArrayList<Pair<Data, Integer>>();
		int distanceManhattan;
		for(int x = 0; x < datas.size(); x++) {
			for(int y = x + 1; y < datas.size(); y++) {
				Position a = datas.get(x).getPosition();
				Position b = datas.get(y).getPosition();
				distanceManhattan = manhattan.calculerDistanceManhattan(a.getX(), a.getY(), b.getX(), b.getY());
				Pair<Data, Integer> calcul = Pair.with(datas.get(x), distanceManhattan);
				distancesManhattan.add(calcul);
			}
		}
		return distancesManhattan;
	}
	
	/**
	 * Permet d'afficher les distances euclidiennes.
	 * 
	 * @param distancesEuclidiennes
	 */
	public void afficherDistancesEuclidiennes(ArrayList<Pair<Data, Double>> distancesEuclidiennes) {
		for(Pair<Data, Double> distanceEuclidienne : distancesEuclidiennes) {
			System.out.println(distanceEuclidienne.getValue0().getPosition() + " " + distanceEuclidienne.getValue1());
		}
	}
	
	/**
	 * Permet d'afficher les distances de Manhattan.
	 * 
	 * @param distancesManhattan
	 */
	public void afficherDistancesManhattan(ArrayList<Pair<Data, Integer>> distancesManhattan) {
		for(Pair<Data, Integer> distanceManhattan : distancesManhattan) {
			System.out.println(distanceManhattan.getValue0().getPosition() + " " + distanceManhattan.getValue1());
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
