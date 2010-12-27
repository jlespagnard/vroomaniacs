package fr.unice.miage.vroomaniacs.circuit.element;

import java.io.Serializable;

import fr.unice.miage.vroomaniacs.circuit.builder.Builder;
import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
@SuppressWarnings("serial")
public class RouteH extends Element implements IRoute, Serializable {
	/**
	 * Constructeur.
	 * 
	 * @param p_editeur	l'&eacute;diteur de cricuit
	 * @param p_builder	le builder utilis&eacute; pour construire l'&eacute;l&eacute;ment
	 * @param p_id		l'ID de l'&eacute;l&eacute;ment
	 */
	public RouteH(EditeurCircuit p_editeur, Builder p_builder, String p_id) {
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