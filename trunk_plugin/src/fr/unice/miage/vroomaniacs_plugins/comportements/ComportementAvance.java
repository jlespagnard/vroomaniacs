package fr.unice.miage.vroomaniacs_plugins.comportements;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ComportementPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnime;

public class ComportementAvance implements ComportementPlugin
{

	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Comportement avance, comportement par default";
	}

	@Override
	public String getName() {
		return "ComportementAvance";
	}

	@Override
	public Class getType() {
		return ComportementAvance.class;
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
	public void deplace(ObjetAnime o) {
		 // le comportement de base, fait avancer l'objet dans sa direction,
        // en fonction de sa vitesse linéaire. Il fait aussi varier sa vitesse linéaire
        // et angulaire en fonction des accélérations linéaires et angulaires
        o.xPos += o.vitesse * Math.cos(o.direction);
        o.yPos += o.vitesse * Math.sin(o.direction);
        //o.vitesse = o.vitesse + o.accelerationLineaire;
        o.direction = o.direction + o.accelerationAngulaire;
        // On garde la direction entre 0 et 2*PI
        o.normaliseDirection();
	}

}
