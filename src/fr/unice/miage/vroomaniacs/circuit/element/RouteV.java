package fr.unice.miage.vroomaniacs.circuit.element;

import java.awt.Toolkit;
import java.io.Serializable;

import javax.swing.ImageIcon;

import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
@SuppressWarnings("serial")
public class RouteV extends Element implements Serializable {
	/**
	 * Constructeur.
	 * 
	 * @param p_id
	 */
	public RouteV(EditeurCircuit p_editeur, String p_id) {
		super(p_editeur,p_id);
		this.m_image = Toolkit.getDefaultToolkit().getImage("./images/route-verticale.png");
		this.setIcon(new ImageIcon(this.m_image));
	}
}