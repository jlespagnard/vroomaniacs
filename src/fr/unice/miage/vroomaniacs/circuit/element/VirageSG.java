package fr.unice.miage.vroomaniacs.circuit.element;

import java.io.Serializable;

import javax.swing.JFrame;

import fr.unice.miage.vroomaniacs.circuit.builder.Builder;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class VirageSG extends Element implements IRoute, Serializable {
	private static final long serialVersionUID = 9L;
	
	/**
	 * Constructeur.
	 * 
	 * @param p_editeur	l'&eacute;diteur de cricuit
	 * @param p_builder	le builder utilis&eacute; pour construire l'&eacute;l&eacute;ment
	 * @param p_id		l'ID de l'&eacute;l&eacute;ment
	 */
	public VirageSG(JFrame p_editeur, Builder p_builder, String p_id) {
		super(p_editeur,p_builder,p_id);
	}
	
	@Override
	public boolean estDepart() {
		return false;
	}

	@Override
	public boolean aNord() {
		return false;
	}

	@Override
	public boolean aSud() {
		return true;
	}

	@Override
	public boolean aEst() {
		return true;
	}

	@Override
	public boolean aOuest() {
		return false;
	}
}