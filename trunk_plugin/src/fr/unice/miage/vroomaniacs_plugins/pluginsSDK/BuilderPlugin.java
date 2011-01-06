package fr.unice.miage.vroomaniacs_plugins.pluginsSDK;

import javax.swing.ImageIcon;

import fr.unice.plugin.Plugin;

public interface BuilderPlugin extends Plugin {
	/**
	 * @return	l'&eacute;l&eacute;ment, <code>null</code> si l'&eacute;'&eacute;ment n'a pas &eacute;t&eacute; construit
	 */
	public IElement getElement();
	/**
	 * @return	l'image repr&eacute;sentant l'&eacute;l&eacute;ment &agrave; construire
	 */
	public ImageIcon getImage();
	/**
	 * @return	l'URL de l'image repr&eacute;sentant l'&eacute;l&eacute;ment &agrave; construire
	 */
	public String getUrlImage();
	/**
	 * Cr&eacute;&eacute;e l'&eacute;l&eacute;ment
	 * 
	 * @param p_editeur	l'&eacute;diteur de cricuit dans lequel l'&eacute;l&eacute;ment sera cr&eacute;&eacute;
	 * @param p_id	l'ID de l'&eacute;l&eacute;ment
	 */
	public void creerElement(IEditeurCircuit p_editeurCircuit,String p_idElement);
}