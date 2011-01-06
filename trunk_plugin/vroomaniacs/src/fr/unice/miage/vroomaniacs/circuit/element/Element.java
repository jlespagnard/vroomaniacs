package fr.unice.miage.vroomaniacs.circuit.element;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.unice.miage.vroomaniacs.circuit.Circuit;
import fr.unice.miage.vroomaniacs.circuit.builder.Builder;
import fr.unice.miage.vroomaniacs.circuit.gui.EditeurCircuit;
import fr.unice.miage.vroomaniacs.circuit.gui.PointilleBorder;
import fr.unice.miage.vroomaniacs.utils.Utils;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public abstract class Element extends JLabel implements IElement, Serializable, MouseListener {
	private static final long serialVersionUID = 1L;

	/** L'ID de l'&eacute;l&eacute;ment */
	private String m_id;
	/** L'&eacute;diteur de cricuit sur lequel se trouve l'&eacute;l&eacute;ment */
	private transient JFrame m_editeur;
	
	/**
	 * Constructeur.
	 * 
	 * @param p_id
	 */
	protected Element(JFrame p_editeur, Builder p_builder, String p_id) {
		this.m_editeur = p_editeur;
		this.m_id = p_id;
		
		this.setMinimumSize(Utils.ELEM_DIM);
		this.setMaximumSize(Utils.ELEM_DIM);
		this.setPreferredSize(Utils.ELEM_DIM);
		this.setBorder(new PointilleBorder(Color.GRAY, 1));
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
		this.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		this.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		this.setIcon(new ImageIcon(p_builder.getImage()));
		this.addMouseListener(this);
	}
	
	@Override
	public String getId() {
		return this.m_id;
	}
	
	@Override
	public void setEditeurCircuit(EditeurCircuit p_editeurCircuit) {
		this.m_editeur = p_editeurCircuit;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(this.m_editeur != null) {
			Builder builder = EditeurCircuit.getBuilder();
			builder.creerElement(this.m_editeur, this.m_id);
			
			Circuit.getInstance().addElement(builder.getElement());
			
			this.m_editeur.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}