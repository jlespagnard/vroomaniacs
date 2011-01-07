package fr.unice.miage.vroomaniacs_plugins.pluginsSDK;

import fr.unice.plugin.Plugin;

public interface ObjetAnimePlugin extends Plugin {
	public double getXPos();
	public void setXPos(double p_xPos);
	public double getYPos();
	public void setYPos(double p_yPos);
	public int getLargeur();
	public int getHauteur();
	public double getDirection();
	public void setDirection(double p_direction);
	public double getVitesse();
	public void setVitesse(double p_vitesse);
	public double getAcceleration();
	public void setAcceleration(double p_acceleration);
	public double getAccelerationAngulaire();
	public IVroomaniacs getJeu();
	public void setJeu(IVroomaniacs p_jeu);
	public void normaliseDirection();
	public void ajouterComportement(ComportementPlugin p_comportement);
	public void supprimerComportement(ComportementPlugin p_comportement);
}
