package fr.unice.miage.vroomaniacs_plugins.comportements;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ComportementPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;

public class ComportementBoost extends ComportementSuitChemin implements ComportementPlugin{
	private boolean boostDone = false;
	private double oldVitesse;
	
	public ComportementBoost(){
	}
	
	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Comportement Boost: prodigue à la voiture ciblée un boost de +-2secondes";
	}

	@Override
	public String getName() {
		return "Comportement Boost";
	}

	@Override
	public Class getType() {
		return ComportementBoost.class;
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
		if(!this.boostDone){
			o.setVitesse(o.getVitesse()*1.5);
			this.boostDone = true;
		}
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}