package fr.unice.miage.vroomaniacs.partie;

import java.awt.Point;

import fr.unice.miage.vroomaniacs.circuit.Circuit;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;

public class Joueur {
	private int id_joueur;
	private String m_nom;
	private ObjetAnimePlugin m_objetAnime;
	
	public Joueur(int p_id,String p_nom,ObjetAnimePlugin p_objetAnime) {
		this.id_joueur=p_id;
		this.m_nom = p_nom;
		this.m_objetAnime = p_objetAnime;
		Point p = Utils.getPointCentre(Circuit.getInstance().getElementDepart());
		this.m_objetAnime.setXPos(p.getX());
		this.m_objetAnime.setYPos(p.getY());
	}
	
	public int getId() {
		return this.id_joueur;
	}
	
	public String getNom() {
		return this.m_nom;
	}
	
	public ObjetAnimePlugin getObjetAnime() {
		return this.m_objetAnime;
	}
}