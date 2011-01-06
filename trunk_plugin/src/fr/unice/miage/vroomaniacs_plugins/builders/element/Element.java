package fr.unice.miage.vroomaniacs_plugins.builders.element;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.border.Border;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.BuilderPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IElement;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public abstract class Element extends JLabel implements IElement, Serializable, MouseListener {
	private static final long serialVersionUID = 1L;

	/** L'ID de l'&eacute;l&eacute;ment */
	private String m_id;
	/** L'&eacute;diteur de cricuit sur lequel se trouve l'&eacute;l&eacute;ment */
	private transient IEditeurCircuit m_editeur;
	
	/**
	 * Constructeur.
	 * 
	 * @param p_id
	 */
	protected Element(IEditeurCircuit p_editeur, BuilderPlugin p_builder, String p_id) {
		this.m_editeur = p_editeur;
		this.m_id = p_id;
		
		this.setMinimumSize(Utils.ELEM_DIM);
		this.setMaximumSize(Utils.ELEM_DIM);
		this.setPreferredSize(Utils.ELEM_DIM);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setVerticalAlignment(JLabel.CENTER);
		this.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		this.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		this.setIcon(p_builder.getImage());
		this.addMouseListener(this);
	}
	
	@Override
	public String getId() {
		return this.m_id;
	}
	
	@Override
	public void setEditeurCircuit(IEditeurCircuit p_editeurCircuit) {
		this.m_editeur = p_editeurCircuit;
	}
	
	@Override
	public void setBorderElement(Border p_border) {
		this.setBorder(p_border);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(this.m_editeur != null) {
			this.m_editeur.ajouterElementCircuit(this.m_id);
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