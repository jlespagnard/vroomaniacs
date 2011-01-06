package fr.unice.miage.vroomaniacs_plugins.builders.element;

import java.io.Serializable;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.BuilderPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IRoute;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class VirageSD extends Element implements IRoute, Serializable {
	private static final long serialVersionUID = 8L;
	
	/**
	 * Constructeur.
	 * 
	 * @param p_editeur	l'&eacute;diteur de cricuit
	 * @param p_builder	le builder utilis&eacute; pour construire l'&eacute;l&eacute;ment
	 * @param p_id		l'ID de l'&eacute;l&eacute;ment
	 */
	public VirageSD(IEditeurCircuit p_editeur, BuilderPlugin p_builder, String p_id) {
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
		return false;
	}

	@Override
	public boolean aOuest() {
		return true;
	}
}