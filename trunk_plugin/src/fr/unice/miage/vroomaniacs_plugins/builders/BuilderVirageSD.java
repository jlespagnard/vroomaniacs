package fr.unice.miage.vroomaniacs_plugins.builders;

import fr.unice.miage.vroomaniacs_plugins.builders.element.VirageSD;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderVirageSD extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderVirageSD() {
		super("imagesElements/virage-superieur-droit.png");
	}
	
	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un élément de type virage ouest-sud";
	}

	@Override
	public String getName() {
		return "Virage ouest-sud";
	}

	@Override
	public Class getType() {
		return BuilderVirageSD.class;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void creerElement(IEditeurCircuit p_editeurCircuit,
			String p_idElement) {
		this.m_element = new VirageSD(p_editeurCircuit,this,p_idElement);
	}
}