package fr.unice.miage.vroomaniacs.circuit.element;

import java.io.Serializable;

import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

public interface IElement extends Serializable{
	/**
	 * @return	l'ID de l'&eacute;l&eacute;ment
	 */
	public String getId();
	/**
	 * @param p_editeurCircuit	l'&eacute;diteur de circuit sur lequel est plac&eacute; l'&eacute;l&eacute;ment
	 */
	public void setEditeurCircuit(EditeurCircuit p_editeurCircuit);
}