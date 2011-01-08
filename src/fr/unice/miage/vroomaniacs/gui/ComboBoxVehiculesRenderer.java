package fr.unice.miage.vroomaniacs.gui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;

@SuppressWarnings("serial")
public class ComboBoxVehiculesRenderer extends JLabel implements ListCellRenderer {
	private Font m_font;
	
	/**
	 * Constructeur
	 */
    public ComboBoxVehiculesRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }
    
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        ImageIcon icon = ((ObjetAnimePlugin)value).getImageIcon();
        String nom = ((ObjetAnimePlugin)value).getName();
        setIcon(icon);
        setVerticalAlignment(CENTER);
        setHorizontalAlignment(LEFT);
        if (icon != null) {
            setText(nom);
            setFont(list.getFont());
        } else {
            setTextWithFont(nom+" (pas d'image trouvée)",list.getFont());
        }

        return this;
	}
	
	private void setTextWithFont(String p_texte, Font p_normalFont) {
		if (this.m_font == null) {
			this.m_font = p_normalFont.deriveFont(Font.ITALIC);
        }
        setFont(this.m_font);
        setText(p_texte);
	}
}