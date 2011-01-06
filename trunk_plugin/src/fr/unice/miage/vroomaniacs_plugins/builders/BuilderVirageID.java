package fr.unice.miage.vroomaniacs_plugins.builders;

import fr.unice.miage.vroomaniacs_plugins.builders.element.VirageID;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderVirageID extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderVirageID() {
		super("imagesElements/virage-inferieur-droit.png");
	}
	
	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un élément de type virage ouest-nord";
	}

	@Override
	public String getName() {
		return "Virage ouest-nord";
	}

	@Override
	public Class getType() {
		return BuilderVirageID.class;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void creerElement(IEditeurCircuit p_editeurCircuit,
			String p_idElement) {
		this.m_element = new VirageID(p_editeurCircuit,this,p_idElement);
	}
}