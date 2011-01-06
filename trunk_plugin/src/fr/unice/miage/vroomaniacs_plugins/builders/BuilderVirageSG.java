package fr.unice.miage.vroomaniacs_plugins.builders;

import fr.unice.miage.vroomaniacs_plugins.builders.element.VirageSG;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderVirageSG extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderVirageSG() {
		super("imagesElements/virage-superieur-gauche.png");
	}
	
	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un élément de type virage est-sud";
	}

	@Override
	public String getName() {
		return "Virage est-sud";
	}

	@Override
	public Class getType() {
		return BuilderVirageSG.class;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void creerElement(IEditeurCircuit p_editeurCircuit,
			String p_idElement) {
		this.m_element = new VirageSG(p_editeurCircuit,this,p_idElement);
	}
}