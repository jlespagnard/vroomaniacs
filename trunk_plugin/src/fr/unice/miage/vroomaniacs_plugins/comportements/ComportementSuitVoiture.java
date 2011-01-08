package fr.unice.miage.vroomaniacs_plugins.comportements;

import java.awt.Point;
import java.util.List;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ComportementPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;

/**
 * @author Anthony
 * Le comportement suit le chemin normal du circuit.
 */public class ComportementSuitVoiture implements ComportementPlugin {

	    public List<Point> chemin = null;
	    public int indicePointCourant = 0;
	    // On considere qu'un objet est pass� au point de passage si la distance
	    // de l'objet a� ce point est < une valeur donnee
	    public int distanceValidationPassage = 10;
	    public ComportementSuitVoiture(){
	    	super();
	    }

	    public ComportementSuitVoiture(List<Point> chemin) {
	    	super();
	        this.chemin = chemin;
	    }

	    public void deplace(ObjetAnimePlugin o) {
	        // On recupere les coordonnees du prochain point de passage
	        Point p = chemin.get(indicePointCourant);

	        // Si on est trop pres du point courant, on passe au point suivant
	        if (Utils.distance(o.getXPos(), o.getYPos(), p.x, p.y) < distanceValidationPassage) {
	            // On passe au point suivant
	            indicePointCourant++;
	            indicePointCourant %= chemin.size();
	        }

	        // Nouvelle direction a suivre
	        double dx = p.x - o.getXPos();
	        double dy = p.y - o.getYPos();
	        o.setDirection(Math.atan2(dy, dx));
	        o.normaliseDirection();
	    }

		@Override
		public boolean canProcess(Object arg0) {
			return true;
		}

		@Override
		public String getDescription() {
			return "Comportement Suit Voiture : La voiture suit le chemin de base ";
		}

		@Override
		public String getName() {
			return "Comportement Suit Voiture";
		}

		@Override
		public Class getType() {
			return ComportementSuitVoiture.class;
		}

		@Override
		public String getVersion() {
			return "1.0";
		}

		@Override
		public boolean matches(Class arg0, String arg1, Object arg2) {
			return true;
		}
		
		@Override
		public String toString() {
			return this.getName();
		}
	}
