package lof;

import java.util.ArrayList;

import org.javatuples.Pair;

public class App {

	public static void main(String[] args) {
		
		LOF lof = new LOF();
		lof.setK(2);
		
		ArrayList<Point> points = new ArrayList<Point>();
		
		points.add(new Point(0, 0));
		points.add(new Point(0, 1));
		points.add(new Point(1, 1));
		points.add(new Point(3, 0));
		
		ArrayList<Pair<Point, Double>> facteursLocauxAberrants = lof.recupererFacteursLocauxAberrants(points);
		
		for(Pair<Point, Double> facteurLocalAberrant : facteursLocauxAberrants) {
			System.out.println("Le Local Outlier Factor du point " + facteurLocalAberrant.getValue0() + " est : " + facteurLocalAberrant.getValue1());
		}
	}
}
