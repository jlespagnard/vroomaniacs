package fr.unice.miage.vroomaniacs.circuit.element;

import java.io.Serializable;

import fr.unice.miage.vroomaniacs.circuit.builder.Builder;
import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class RouteDepart extends Element implements IRoute, Serializable {
	private static final long serialVersionUID = 3L;
	
	/**
	 * Constructeur.
	 * 
	 * @param p_editeur	l'&eacute;diteur de cricuit
	 * @param p_builder	le builder utilis&eacute; pour construire l'&eacute;l&eacute;ment
	 * @param p_id		l'ID de l'&eacute;l&eacute;ment
	 */
	public RouteDepart(EditeurCircuit p_editeur, Builder p_builder, String p_id) {
		super(p_editeur,p_builder,p_id);
	}
	
	@Override
	public boolean estDepart() {
		return true;
	}

	@Override
	public boolean aNord() {
		return false;
	}

	@Override
	public boolean aSud() {
		return false;
	}

	@Override
	public boolean aEst() {
		return true;
	}

	@Override
	public boolean aOuest() {
		return true;
	}
}