package lof;

import java.util.ArrayList;

import org.javatuples.Pair;

import kmeans.Data;
import kmeans.Kmeans;

public class App {

	private static double datasBrutes[][] = { { 8.0, 6 }, { 10.0, 8 }, { 12.0, 10 }, { 16, 12 }, { 17, 14 }, { 16, 16 },
			{ 14, 18 }, { 12, 20 }, { 10, 22 }, { 9, 0 }, { 8, 2 }, { 7, 4 }, { 300, 12 } };

	public static void main(String[] args) {
		Kmeans kmeans = new Kmeans();
		LOF lof = new LOF();

		ArrayList<Data> datas = new ArrayList<Data>();
		
		datas = kmeans.transformerDatas(datasBrutes);
		datas = kmeans.normaliserValeursBrutes(datas);
		datas = kmeans.calculerPositions(datas);
		
		ArrayList<Pair<Data, Double>> distancesEuclidiennes = lof.recupererDistancesEuclidiennes(datas);
		ArrayList<Pair<Data, Integer>> distancesManhattan = lof.recupererDistancesManhattan(datas);
		
		lof.afficherDistancesEuclidiennes(distancesEuclidiennes);	
		lof.afficherDistancesManhattan(distancesManhattan);
	}

}
