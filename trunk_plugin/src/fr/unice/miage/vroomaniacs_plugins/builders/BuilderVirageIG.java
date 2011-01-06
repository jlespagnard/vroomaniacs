package fr.unice.miage.vroomaniacs_plugins.builders;

import fr.unice.miage.vroomaniacs_plugins.builders.element.VirageIG;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderVirageIG extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderVirageIG() {
		super("imagesElements/virage-inferieur-gauche.png");
	}
	
	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un élément de type virage est-nord";
	}

	@Override
	public String getName() {
		return "Virage est-nord";
	}

	@Override
	public Class getType() {
		return BuilderVirageIG.class;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void creerElement(IEditeurCircuit p_editeurCircuit,
			String p_idElement) {
		this.m_element = new VirageIG(p_editeurCircuit,this,p_idElement);
	}
}