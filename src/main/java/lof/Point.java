package lof;

import java.util.ArrayList;

import org.javatuples.Pair;

public class Point {

	private int x;
	private int y;
	private ArrayList<Pair<Point, Integer>> distancesManhattan;
	private Pair<Point, Integer> plusProcheVoisin;
	private ArrayList<Pair<Point, Integer>> kPlusProchesVoisins;
	private double densiteAtteignabiliteLocale;
	private ArrayList<Pair<Point, Integer>> distancesAtteignabilites;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		this.distancesManhattan = new ArrayList<Pair<Point, Integer>>();
		this.kPlusProchesVoisins = new ArrayList<Pair<Point, Integer>>();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Pair<Point, Integer> getPlusProcheVoisin() {
		return plusProcheVoisin;
	}

	public void setPlusProcheVoisin(Pair<Point, Integer> plusProcheVoisin) {
		this.plusProcheVoisin = plusProcheVoisin;
	}

	public ArrayList<Pair<Point, Integer>> getDistancesManhattan() {
		return distancesManhattan;
	}

	public void setDistancesManhattan(ArrayList<Pair<Point, Integer>> distancesManhattan) {
		this.distancesManhattan = distancesManhattan;
	}

	public ArrayList<Pair<Point, Integer>> getkPlusProchesVoisins() {
		return kPlusProchesVoisins;
	}

	public void setkPlusProchesVoisins(ArrayList<Pair<Point, Integer>> kPlusProchesVoisins) {
		this.kPlusProchesVoisins = kPlusProchesVoisins;
	}

	public double getDensiteAtteignabiliteLocale() {
		return densiteAtteignabiliteLocale;
	}

	public void setDensiteAtteignabiliteLocale(double densiteAtteignabiliteLocale) {
		this.densiteAtteignabiliteLocale = densiteAtteignabiliteLocale;
	}

	public ArrayList<Pair<Point, Integer>> getDistancesAtteignabilites() {
		return distancesAtteignabilites;
	}

	public void setDistancesAtteignabilites(ArrayList<Pair<Point, Integer>> distancesAtteignabilites) {
		this.distancesAtteignabilites = distancesAtteignabilites;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
}
