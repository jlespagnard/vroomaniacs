package fr.unice.miage.vroomaniacs_plugins.builders.element;

import java.io.Serializable;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.BuilderPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IDecor;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class Herbe extends Element implements IDecor, Serializable {
	private static final long serialVersionUID = 2L;
	
	public Herbe(IEditeurCircuit p_editeur, BuilderPlugin p_builder, String p_id) {
		super(p_editeur, p_builder, p_id);
	}
}