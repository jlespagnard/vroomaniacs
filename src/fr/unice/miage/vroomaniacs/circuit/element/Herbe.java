package fr.unice.miage.vroomaniacs.circuit.element;

import java.io.Serializable;

import fr.unice.miage.vroomaniacs.circuit.builder.Builder;
import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
@SuppressWarnings("serial")
public class Herbe extends Element implements IDecor, Serializable {
	public Herbe(EditeurCircuit p_editeur, Builder p_builder, String p_id) {
		super(p_editeur, p_builder, p_id);
	}
}