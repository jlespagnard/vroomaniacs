package fr.unice.miage.vroomaniacs.partie;

public class Joueur {
	private int id_joueur;
	private String m_nom;
	private String m_comportement;
	
	public Joueur(int p_id,String p_nom,String p_comportement) {
		this.id_joueur=p_id;
		this.m_nom = p_nom;
		this.m_comportement = p_comportement;
	}
	
	public int getId() {
		return this.id_joueur;
	}
	
	public String getNom() {
		return this.m_nom;
	}
	
	public String getComportement() {
		return this.m_comportement;
	}
}