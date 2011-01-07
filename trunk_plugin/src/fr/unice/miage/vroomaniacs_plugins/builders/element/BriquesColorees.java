package fr.unice.miage.vroomaniacs_plugins.builders.element;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.BuilderPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IDecor;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BriquesColorees extends Element implements IDecor {
	private static final long serialVersionUID = 10L;
	
	public BriquesColorees(IEditeurCircuit p_editeur,
			BuilderPlugin p_builder, String p_id) {
		super(p_editeur, p_builder, p_id);
	}

}
