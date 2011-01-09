package fr.unice.miage.vroomaniacs.partie;

import java.awt.Point;

import fr.unice.miage.vroomaniacs.circuit.Circuit;
import fr.unice.miage.vroomaniacs.gui.Vroomaniacs;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;

public class Joueur {
	private String m_nom;
	private ObjetAnimePlugin m_objetAnime;
	public int m_nbTours = -1;
	
	public Joueur(String p_nom,ObjetAnimePlugin p_objetAnime, Vroomaniacs jeu) {
		this.m_nom = p_nom;
		this.m_objetAnime = p_objetAnime;
		Point p = Utils.getPointCentre(Circuit.getInstance().getElementDepart());
		this.m_objetAnime.setXPos(p.getX());
		this.m_objetAnime.setYPos(p.getY());
		this.m_objetAnime.setJeu(jeu);
		double dx = Circuit.getInstance().getChemin().get(1).getX() - p.x;
        double dy = Circuit.getInstance().getChemin().get(1).getY() - p.y;
        this.m_objetAnime.setDirection(Math.atan2(dy, dx));
	}

	public String getNom() {
		return this.m_nom;
	}
	
	public ObjetAnimePlugin getObjetAnime() {
		return this.m_objetAnime;
	}
	
	@Override
	public String toString() {
		return this.m_nom;
	}
}