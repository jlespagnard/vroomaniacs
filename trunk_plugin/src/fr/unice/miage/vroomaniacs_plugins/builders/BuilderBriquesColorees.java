package fr.unice.miage.vroomaniacs_plugins.builders;

import fr.unice.miage.vroomaniacs_plugins.builders.element.Herbe;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;

public class BuilderBriquesColorees extends Builder {
	
	/**
	 * Constructeur
	 */
	public BuilderBriquesColorees() {
		super("imagesElements/briques-colorees.png");
	}
	
	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un �l�ment de d�cor briques color�es.";
	}

	@Override
	public String getName() {
		return "Briques color�es";
	}

	@Override
	public Class getType() {
		return BuilderBriquesColorees.class;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void creerElement(IEditeurCircuit p_editeurCircuit,
			String p_idElement) {
		this.m_element = new Herbe(p_editeurCircuit,this,p_idElement);
	}
}
