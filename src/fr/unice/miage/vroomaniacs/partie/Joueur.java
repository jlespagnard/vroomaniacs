package fr.unice.miage.vroomaniacs.partie;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;

public class Joueur {
	private int id_joueur;
	private String m_nom;
	private ObjetAnimePlugin m_objetAnime;
	
	public Joueur(int p_id,String p_nom,ObjetAnimePlugin p_objetAnime) {
		this.id_joueur=p_id;
		this.m_nom = p_nom;
		this.m_objetAnime = p_objetAnime;
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