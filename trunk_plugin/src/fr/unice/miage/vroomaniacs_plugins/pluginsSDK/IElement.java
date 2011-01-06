package fr.unice.miage.vroomaniacs_plugins.pluginsSDK;

import java.io.Serializable;

import javax.swing.JFrame;

public interface IElement extends Serializable{
	/**
	 * @return	l'ID de l'&eacute;l&eacute;ment
	 */
	public String getId();
	/**
	 * @param p_editeurCircuit	l'&eacute;diteur de circuit sur lequel est plac&eacute; l'&eacute;l&eacute;ment
	 */
	public void setEditeurCircuit(JFrame p_editeurCircuit);
}