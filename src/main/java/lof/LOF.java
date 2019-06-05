package lof;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import org.javatuples.Pair;

import manhattan.Manhattan;

public class LOF {

	private int k;
	private double seuil;
	private double distanceAtteignabilite;
	private double distanceAtteignabiliteDensity;
	private Manhattan manhattan;

	public LOF() {
		this.manhattan = new Manhattan();
	}

	public LOF(int k, int seuil) {
		this.manhattan = new Manhattan();
		this.k = k;
		this.seuil = seuil;
	}
	
	/**
	 * Permet de récupérer les anomalies potentielles à partir des 
	 * facteurs locaux aberrants calculés précédemment.
	 * 
	 * @param facteursLocauxAberrants
	 * @return anomaliesPotentielles
	 */
	public ArrayList<Point> recupererAnomaliesPotentielles(ArrayList<Pair<Point, Double>> facteursLocauxAberrants){
		ArrayList<Point> anomaliesPotentielles = new ArrayList<Point>();
		for(Pair<Point, Double> anomaliePotentielle : facteursLocauxAberrants) {
			if (anomaliePotentielle.getValue1() >= seuil) {
				anomaliesPotentielles.add(anomaliePotentielle.getValue0());
			}
		}
		return anomaliesPotentielles;
	}
	
	/**
	 * Permet de récupérer les facteursLocauxAberrants (Local Outlier Factor) de l'ensemble des points.
	 * 
	 * @param points
	 * @return facteursLocauxAberrants
	 */
	public ArrayList<Pair<Point, Double>> recupererFacteursLocauxAberrants(ArrayList<Point> points){
		ArrayList<Pair<Point, Double>> facteursLocauxAberrants = new ArrayList<Pair<Point, Double>>();
		for(Point point : points) {
			System.out.println("Point : " + point + "\n");
			ArrayList<Pair<Point, Integer>> distancesManhattan = recupererDistancesManhattan(point, points);
			point.setDistancesManhattan(distancesManhattan);
			
			System.out.println("Distance de Manhattan par rapport aux autres points : ");
			for(Pair<Point, Integer> distanceManhattan : distancesManhattan) {
				System.out.println(distanceManhattan.getValue0() + " " + distanceManhattan.getValue1());
			}
			
			Pair<Point, Integer> plusProcheVoisin = recupererPlusProcheVoisin(distancesManhattan);
			System.out.println("Le k(" + getK() + ") plus proche est : " + plusProcheVoisin.getValue0() + " avec une distance de " + plusProcheVoisin.getValue1());
			point.setPlusProcheVoisin(plusProcheVoisin);
			
			ArrayList<Pair<Point, Integer>> kPlusProchesVoisins = recupererKPLusProchesVoisins(distancesManhattan);
			System.out.println("Les k(" + getK() + ") plus proches voisins : ");
			point.setkPlusProchesVoisins(kPlusProchesVoisins);
			
			for(Pair<Point, Integer> kPlusProcheVoisin : kPlusProchesVoisins) {
				System.out.println(kPlusProcheVoisin.getValue0());
			}
			
			ArrayList<Integer> distancesAtteignabilites = new ArrayList<Integer>();
			// refactorisation
			ArrayList<Pair<Point, Integer>> distancesAtteignabilites2 = new ArrayList<Pair<Point, Integer>>();
			
			for(Pair<Point, Integer> kPlusProcheVoisin : kPlusProchesVoisins) {
				Pair<Point, Integer> distanceDuPlusProcheVoisin = recupererDistanceDuPlusProcheVoisin(kPlusProcheVoisin.getValue0(), points);
				int distanceAtteignabilite = calculerDistanceAtteignabilite(distanceDuPlusProcheVoisin.getValue1().intValue(), manhattan.calculerDistanceManhattan(point.getX(), point.getY(), kPlusProcheVoisin.getValue0().getX(), kPlusProcheVoisin.getValue0().getY()));
				distancesAtteignabilites.add(distanceAtteignabilite);
				distancesAtteignabilites2.add(Pair.with(kPlusProcheVoisin.getValue0(), distanceAtteignabilite));
				System.out.println("Distance d'atteignabilité (Reach Distance) du point " + point + " vers " + kPlusProcheVoisin.getValue0() + " est : " + distanceAtteignabilite);
			}
			
			// refactorisation
			point.setDistancesAtteignabilites(distancesAtteignabilites2);
			
			double densiteAtteignabiliteLocale = calculerDensiteAtteignabiliteLocale(distancesAtteignabilites);
			System.out.println("La densité d'atteignabilité locale (Local Reachability Density) du point " + point + " est : " + densiteAtteignabiliteLocale + "\n");
			point.setDensiteAtteignabiliteLocale(densiteAtteignabiliteLocale);
		}
		
		for(Point point : points) {
			double facteurLocalAberrant = calculerFacteurLocalAberrant(point.getkPlusProchesVoisins(), point.getDistancesAtteignabilites());
			facteursLocauxAberrants.add(Pair.with(point, facteurLocalAberrant));
			point.setFacteurLocalAberrant(facteurLocalAberrant);
		}
		
		return facteursLocauxAberrants;
	}

	/**
	 * Permet de récupérer les k plus proches voisins.
	 * 
	 * @param distances
	 * @return kPlusProchesVoisins
	 */
	public ArrayList<Pair<Point, Integer>> recupererKPLusProchesVoisins(ArrayList<Pair<Point, Integer>> distances) {
		ArrayList<Pair<Point, Integer>> kPlusProchesVoisins = new ArrayList<Pair<Point, Integer>>();
		for (int x = 0; x < k; x++) {
			kPlusProchesVoisins.add(distances.get(x));
		}
		return kPlusProchesVoisins;
	}
	
	/**
	 * Permet d'afficher dans la console les points avec les facteurs locaux abérrants calculés.
	 * 
	 * @param facteursLocauxAberrants
	 */
	public void afficherFacteursLocauxAberrants(ArrayList<Pair<Point, Double>> facteursLocauxAberrants) {
		for(Pair<Point, Double> facteurLocalAberrant : facteursLocauxAberrants) {
			System.out.println("Le Local Outlier Factor du point " + facteurLocalAberrant.getValue0() + " est : " + facteurLocalAberrant.getValue1());
		}
	}
	
	/**
	 * Permet de calculer le facteur local abérrant (Local Outlier Factor)
	 * @param kPlusProchesVoisins
	 * @param distancesAtteignabilites
	 * @return facteurLocalAberrant
	 */
	public double calculerFacteurLocalAberrant(ArrayList<Pair<Point, Integer>> kPlusProchesVoisins, ArrayList<Pair<Point, Integer>> distancesAtteignabilites) {
		double facteurLocalAberrant = 0;
		double sommeDesDensitesAtteignabilitesLocales = 0;
		int sommeDesdistancesAtteignabilites = 0;
		for(Pair<Point, Integer> kPlusProcheVoisin : kPlusProchesVoisins) {
			sommeDesDensitesAtteignabilitesLocales = sommeDesDensitesAtteignabilitesLocales + kPlusProcheVoisin.getValue0().getDensiteAtteignabiliteLocale();
		}
		for(Pair<Point, Integer> distanceAtteignabilite : distancesAtteignabilites) {
			sommeDesdistancesAtteignabilites = sommeDesdistancesAtteignabilites + distanceAtteignabilite.getValue1();
		}
		facteurLocalAberrant = (sommeDesDensitesAtteignabilitesLocales * sommeDesdistancesAtteignabilites) / (k * k);
		return facteurLocalAberrant;
	}

	/**
	 * Permet de récupérer la distance de Manhattan du plus proche voisin du point
	 * passé en paramètre.
	 * 
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
	public Pair<Point, Integer> recupererPlusProcheVoisin(ArrayList<Pair<Point, Integer>> distancesManhattan) {
		return distancesManhattan.get(k - 1);
	}

	/**
	 * Permet de récupérer les distances de Manhattan.
	 * 
	 * @param data
	 * @param datas
	 * @return distancesManhattan
	 */
	public ArrayList<Pair<Point, Integer>> recupererDistancesManhattan(Point point, ArrayList<Point> points) {
		ArrayList<Pair<Point, Integer>> distancesManhattan = new ArrayList<Pair<Point, Integer>>();
		for (Point p : points) {
			if (point != p) {
				int distanceManhattan = manhattan.calculerDistanceManhattan(point.getX(), point.getY(), p.getX(), p.getY());
				Pair<Point, Integer> calcul = Pair.with(p, distanceManhattan);
				distancesManhattan.add(calcul);
			}
		}
		distancesManhattan.sort(new Comparator<Pair<Point, Integer>>() {
			@Override
			public int compare(Pair<Point, Integer> point1, Pair<Point, Integer> point2) {
				if (point1.getValue1() > point2.getValue1()) {
					return 1;
				} else if (point1.getValue1().equals(point2.getValue1())) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		return distancesManhattan;
	}

	/**
	 * Permet de calculer les distances de Manhattan de chacun des points.
	 * 
	 * @param points
	 * @return distancesManhattan
	 */
	public ArrayList<Pair<Point, Integer>> recupererDistancesManhattan(ArrayList<Point> points) {
		ArrayList<Pair<Point, Integer>> distancesManhattan = new ArrayList<Pair<Point, Integer>>();
		int distanceManhattan;
		for (int x = 0; x < points.size(); x++) {
			for (int y = x + 1; y < points.size(); y++) {
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
	 * @param distancesAtteignabilites
	 * @return densiteAccessibiliteLocale
	 */
	public double calculerDensiteAtteignabiliteLocale(ArrayList<Integer> distancesAtteignabilites) {
		int atteignabiliteLocale = 0;
		for (int distanceAtteignabilite : distancesAtteignabilites) {
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
	 * Permet de générer un point aléatoire selon les informations passées.
	 * 
	 * @param valeurMinimaleX
	 * @param valeurMaximaleX
	 * @param valeurMinimaleY
	 * @param valeurMaximaleY
	 * @return point
	 */
	public Point genererPointAleatoire(int valeurMinimaleX, int valeurMaximaleX, int valeurMinimaleY, int valeurMaximaleY){
		Random random = new Random();
		Point point = new Point(valeurMinimaleX + random.nextInt(valeurMaximaleX - valeurMinimaleX), valeurMinimaleY + random.nextInt(valeurMaximaleY - valeurMinimaleY));
		return point;
	}
	
	/**
	 * Permet de générer des points aléatoires selon les informations passées en paramètres.
	 * 
	 * @param valeurMinimaleX
	 * @param valeurMaximaleX
	 * @param valeurMinimaleY
	 * @param valeurMaximaleY
	 * @param nombre
	 * @return points
	 */
	public ArrayList<Point> genererPointsAleatoires(int valeurMinimaleX, int valeurMaximaleX, int valeurMinimaleY, int valeurMaximaleY, int nombre){
		ArrayList<Point> points = new ArrayList<Point>();
		for(int i = 0; i < nombre; i++) {
			Point point = genererPointAleatoire(valeurMinimaleX, valeurMaximaleX, valeurMinimaleY, valeurMaximaleY);
			points.add(point);
		}
		return points;
	}
	
	/**
	 * Permet d'afficher les distances de Manhattan calculées pour chacun des
	 * points.
	 * 
	 * @param distancesManhattan
	 */
	public void afficherDistancesManhattan(ArrayList<Pair<Point, Integer>> distancesManhattan) {
		for (Pair<Point, Integer> distanceManhattan : distancesManhattan) {
			System.out.println(distanceManhattan.getValue0().getX() + " " + distanceManhattan.getValue0().getY() + "  "
					+ distanceManhattan.getValue1());
		}
	}
	
	public double getSeuil() {
		return seuil;
	}

	public void setSeuil(double seuil) {
		this.seuil = seuil;
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
