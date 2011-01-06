package fr.unice.miage.vroomaniacs_plugins.comportements;

import java.awt.Point;
import java.util.List;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ComportementPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnime;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;

/**
 * @author Anthony
 * Le comportement suit le chemin normal du circuit.
 */public class ComportementSuitVoiture implements ComportementPlugin{

	    public List<Point> chemin = null;
	    public int indicePointCourant = 0;
	    // On considere qu'un objet est passé au point de passage si la distance
	    // de l'objet a  ce point est < une valeur donnee
	    public int distanceValidationPassage = 10;
	    public ComportementSuitVoiture(List<Point> chemin) {
	        this.chemin = chemin;
	    }

	    public void deplace(ObjetAnime o) {
	        // On recupere les coordonnees du prochain point de passage
	        Point p = chemin.get(indicePointCourant);

	        // Si on est trop pres du point courant, on passe au point suivant
	        if (Utils.distance(o.xPos, o.yPos, p.x, p.y) < distanceValidationPassage) {
	            // On passe au point suivant
	            indicePointCourant++;
	            indicePointCourant %= chemin.size();
	        }

	        // Nouvelle direction a suivre
	        double dx = p.x - o.xPos;
	        double dy = p.y - o.yPos;
	        o.direction = Math.atan2(dy, dx);
	        o.normaliseDirection();
	    }

		@Override
		public boolean canProcess(Object arg0) {
			return true;
		}

		@Override
		public String getDescription() {
			return "Comportement Suit Chemin : La voiture suit le chemin de base ";
		}

		@Override
		public String getName() {
			return "Comportement Suit Chemin";
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
	}
