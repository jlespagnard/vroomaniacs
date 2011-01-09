package fr.unice.miage.vroomaniacs_plugins.comportements;

import java.awt.Point;
import java.util.List;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ComportementPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;

/**
 * @author Anthony
 * Le comportement suit le chemin normal du circuit.
 */public class ComportementSuitChemin extends ComportementAvance implements ComportementPlugin{

	    public List<Point> chemin = null;
	    public int indicePointCourant = 0;
	    // On considere qu'un objet est pass� au point de passage si la distance
	    // de l'objet a� ce point est < une valeur donnee
	    public int distanceValidationPassage = 10;
	    boolean circuitSet = false;
	    
	    public ComportementSuitChemin(){
	    	super();
	    }
	    public ComportementSuitChemin(List<Point> chemin) {
	    	super();
	        this.chemin = chemin;
	    }

	    public void deplace(ObjetAnimePlugin o) {
	    	if(!this.circuitSet){
	    		this.chemin = o.getJeu().getChemin();
	    		this.chemin = Utils.multiplierPoints(this.chemin);
	    		this.chemin = Utils.multiplierPoints(this.chemin);
	    		this.circuitSet = true;
	    		//On trouve le point le plus proche au cas o� le joueur ne serait pas sur la ligne de d�part
	    		double distance = Utils.distance(o.getXPos(), o.getYPos(), chemin.get(0).x, chemin.get(0).y) ;
	    		for(int i = 1; i < chemin.size(); i++){
	    			double tmp = Utils.distance(o.getXPos(), o.getYPos(), chemin.get(i).x, chemin.get(i).y);
	    			if(distance > tmp){
	    				distance = tmp;
	    				indicePointCourant = i;
	    			}
	    		}
	    	}
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
	        o.setDirection(o.getDirection()+o.getAccelerationAngulaire());
	        o.setXPos(o.getXPos()+(o.getVitesse()*Math.cos(o.getDirection())));
	        o.setYPos(o.getYPos()+(o.getVitesse()*Math.sin(o.getDirection())));
	        // On garde la direction entre 0 et 2*PI
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
			return ComportementSuitChemin.class;
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
