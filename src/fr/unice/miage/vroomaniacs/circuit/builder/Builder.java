package fr.unice.miage.vroomaniacs.circuit.builder;

import java.awt.Image;
import java.awt.Toolkit;

import fr.unice.miage.vroomaniacs.circuit.element.IElement;
import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public abstract class Builder {
	/** L'&eacute;l&eacute;ment &agrave; construire. */
	protected IElement m_element = null;
	/** L'image repr&eacute;sentant l'&eacute;l&eacute;ment &agrave; construire. */
	protected Image m_image = null;
	/** L'URL de l'image repr&eacute;sentant l'&eacute;l&eacute;ment &agrave; construire */
	protected String m_urlImage = null;
	
	/**
	 * Constructeur.
	 * 
	 * @param p_urlImage	l'URL de l'image repr&eacute;sentant l'&eacute;l&eacute;ment &agrave; construire
	 */
	protected Builder(String p_urlImage) {
		this.m_urlImage = p_urlImage;
		this.m_image = Toolkit.getDefaultToolkit().getImage(p_urlImage);
	}
	
	/**
	 * @return	l'&eacute;l&eacute;ment, <code>null</code> si l'&eacute;'&eacute;ment n'a pas &eacute;t&eacute; construit
	 */
	public IElement getElement() {
		return this.m_element;
	}
	
	/**
	 * @return	l'image repr&eacute;sentant l'&eacute;l&eacute;ment &agrave; construire
	 */
	public Image getImage() {
		return this.m_image;
	}
	
	/**
	 * @return	l'URL de l'image repr&eacute;sentant l'&eacute;l&eacute;ment &agrave; construire
	 */
	public String getUrlImage() {
		return this.m_urlImage;
	}
	
	/**
	 * Cr&eacute;&eacute;e l'&eacute;l&eacute;ment
	 * 
	 * @param p_editeur	l'&eacute;diteur de cricuit dans lequel l'&eacute;l&eacute;ment sera cr&eacute;&eacute;
	 * @param p_id	l'ID de l'&eacute;l&eacute;ment
	 */
	public abstract void creerElement(EditeurCircuit p_editeur, String p_id);
}