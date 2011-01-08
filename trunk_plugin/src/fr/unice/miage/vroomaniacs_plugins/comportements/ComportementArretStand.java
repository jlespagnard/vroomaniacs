package fr.unice.miage.vroomaniacs_plugins.comportements;

import java.awt.Point;
import java.util.List;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ComportementPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;

public class ComportementArretStand extends ComportementSuitChemin
{
	private long tempStand;
	private long tempPrec;
    
	public ComportementArretStand() {
		super();
	}
	
    public ComportementArretStand(List<Point> chemin)
    {
    	super(chemin);
    	tempStand = 0;
    	tempPrec = 0;
    }
    
	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Comportement Arret Stand, pour qu'une voiture s'arrete au stand";
	}

	@Override
	public String getName() {
		return "Comportement Stand";
	}

	@Override
	public Class getType() {
		return ComportementArretStand.class;
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
	public void deplace(ObjetAnimePlugin o) {
		Point stand = o.getJeu().getStand();
		if(!o.getJeu().getStand().equals(null) && (Utils.distance(o.getXPos(), o.getYPos(), stand.x,stand.y)<Utils.getLargeurRoute()*2))
		{
				o.setDirection(Math.atan2(stand.y, stand.x));
				if((o.getXPos() == stand.x) && (o.getYPos() == stand.y))
				{
					long time = System.currentTimeMillis();
					tempStand += time-tempPrec;
					tempPrec = time;
					if(tempStand > 5000)
					{
						//to do remise sur le circuit
						
					}
				}
		}
		else
		{
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
	}
}