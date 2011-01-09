package fr.unice.miage.vroomaniacs_plugins.builders;

import javax.swing.ImageIcon;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.BuilderPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IElement;

public abstract class Builder implements BuilderPlugin {
	/** L'&eacute;l&eacute;ment &agrave; construire. */
	protected IElement m_element = null;
	/** L'image repr&eacute;sentant l'&eacute;l&eacute;ment &agrave; construire. */
	protected ImageIcon m_image = null;

	/**
	 * Constructeur.
	 * 
	 * @param p_urlImage	l'URL de l'image repr&eacute;sentant l'&eacute;l&eacute;ment &agrave; construire
	 */
	protected Builder(String p_urlImage) {
		this.m_image = new ImageIcon(this.getClass().getResource(p_urlImage));
	}
	
	@Override
	public boolean matches(Class type, String name, Object object) {
		return true;
	}

	@Override
	public IElement getElement() {
		return this.m_element;
	}

	@Override
	public ImageIcon getImage() {
		return this.m_image;
	}
}