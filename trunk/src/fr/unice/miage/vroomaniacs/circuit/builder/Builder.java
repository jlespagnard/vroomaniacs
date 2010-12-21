package fr.unice.miage.vroomaniacs.circuit.builder;

import fr.unice.miage.vroomaniacs.circuit.element.Element;
import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public abstract class Builder {
	/** L'&eacute;l&eacute;ment &agrave; construire. */
	protected Element m_element = null;
	
	/**
	 * @return	l'&eacute;l&eacute;ment, <code>null</code> si l'&eacute;'&eacute;ment n'a pas &eacute;t&eacute; construit
	 */
	public Element getElement() {
		return this.m_element;
	}
	
	/**
	 * Cr&eacute;&eacute;e l'&eacute;l&eacute;ment
	 * 
	 * @param p_editeur	l'&eacute;diteur de cricuit dans lequel l'&eacute;l&eacute;ment sera cr&eacute;&eacute;
	 * @param p_id	l'ID de l'&eacute;l&eacute;ment
	 */
	public abstract void creerElement(EditeurCircuit p_editeur, String p_id);
}