package fr.unice.miage.vroomaniacs_plugins.pluginsSDK;

import java.io.Serializable;

import javax.swing.border.Border;

public interface IElement extends Serializable {
	/**
	 * @return	l'ID de l'&eacute;l&eacute;ment
	 */
	public String getId();
	/**
	 * @param p_editeurCircuit	l'&eacute;diteur de circuit sur lequel est plac&eacute; l'&eacute;l&eacute;ment
	 */
	public void setEditeurCircuit(IEditeurCircuit p_editeurCircuit);
	/**
	 * @param p_border	la nouvelle bordure de l'&eacute;l&eacute;ment
	 */
	public void setBorderElement(Border p_border);
}