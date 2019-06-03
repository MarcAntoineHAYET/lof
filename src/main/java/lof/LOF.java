package lof;

import java.util.ArrayList;

import org.javatuples.Pair;

import euclide.Euclide;
import kmeans.Data;
import kmeans.Position;

public class LOF {
	
	private int k;
	private double distanceAtteignabilite;
	private double distanceAtteignabiliteDensity;
	private ArrayList<Data> datas;
	private Euclide euclide;
	
	public LOF() {
		this.euclide = new Euclide();
	}
	
	public LOF(int k) {
		this.k = k;
	}

	public void lancer() {
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

	public ArrayList<Data> getDatas() {
		return datas;
	}

	public void setDatas(ArrayList<Data> datas) {
		this.datas = datas;
	}
}
