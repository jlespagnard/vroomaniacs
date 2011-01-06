package fr.unice.miage.vroomaniacs.partie;

public class Joueur {
	private String m_nom;
	private String m_comportement;
	
	public Joueur(String p_nom, String p_comportement) {
		this.m_nom = p_nom;
		this.m_comportement = p_comportement;
	}
	
	public String getNom() {
		return this.m_nom;
	}
	
	public String getComportement() {
		return this.m_comportement;
	}
}