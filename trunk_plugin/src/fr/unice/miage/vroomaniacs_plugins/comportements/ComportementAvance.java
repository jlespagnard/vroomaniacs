package fr.unice.miage.vroomaniacs_plugins.comportements;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ComportementPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;

public class ComportementAvance implements ComportementPlugin
{

	public ComportementAvance(){
	}
	
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
		return "Comportement Avance";
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
	public void deplace(ObjetAnimePlugin o) {
		 // le comportement de base, fait avancer l'objet dans sa direction,
        // en fonction de sa vitesse linéaire. Il fait aussi varier sa vitesse linéaire
        // et angulaire en fonction des accélérations linéaires et angulaires
        o.setXPos(o.getXPos()+(o.getVitesse()*Math.cos(o.getDirection())));
        o.setYPos(o.getYPos()+(o.getVitesse()*Math.sin(o.getDirection())));
        //o.vitesse = o.vitesse + o.accelerationLineaire;
        o.setDirection(o.getDirection()+o.getAccelerationAngulaire());
        // On garde la direction entre 0 et 2*PI
        o.normaliseDirection();
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}
