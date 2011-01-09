package fr.unice.miage.vroomaniacs_plugins.builders;

import fr.unice.miage.vroomaniacs_plugins.builders.element.Herbe;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderHerbe extends Builder {
	/**
	 * Constructeur.
	 * 
	 * @param p_urlImage	l'URL de l'image repr&eacute;sentant l'&eacute;l&eacute;ment &agrave; construire
	 */
	public BuilderHerbe() {
		super("/imagesElements/pelouse.png");
	}
	
	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un élément de décor neutre.";
	}

	@Override
	public String getName() {
		return "Sol pelouse";
	}

	@Override
	public Class getType() {
		return BuilderHerbe.class;
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