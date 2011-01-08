package fr.unice.miage.vroomaniacs.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.unice.miage.vroomaniacs.partie.Joueur;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;
import fr.unice.plugin.Plugin;

@SuppressWarnings("serial")
public class NouvellePartie extends JFrame {
	private JPanel m_panelListeJoueurs = null;
	private List<Joueur> m_joueurs = new LinkedList<Joueur>();
	
	public void construireListeJoueurs() {
		if(this.m_panelListeJoueurs != null) {
			this.remove(this.m_panelListeJoueurs);
		}
		this.m_panelListeJoueurs = new JPanel(new GridLayout(this.m_joueurs.size()+1,1));
		this.add(this.m_panelListeJoueurs, BorderLayout.CENTER);
		
		JPanel panelNomJoueur;
		JPanel panelObjetAnimeJoueur;
		JPanel panelBoutonJoueur;
		for(final Joueur joueur : this.m_joueurs) {
			panelNomJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT));
			this.m_panelListeJoueurs.add(panelNomJoueur);
			panelNomJoueur.add(new JLabel("Nom : "));
			panelNomJoueur.add(new JLabel(joueur.getNom()));
			
			panelObjetAnimeJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT));
			this.m_panelListeJoueurs.add(panelObjetAnimeJoueur);
			panelObjetAnimeJoueur.add(new JLabel("Véhicule : "));
			panelObjetAnimeJoueur.add(new JLabel(joueur.getObjetAnime().getName()));
			
			panelBoutonJoueur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			this.m_panelListeJoueurs.add(panelBoutonJoueur);			
			JButton btnSupprimerJoueur = new JButton(" - ");
			panelBoutonJoueur.add(btnSupprimerJoueur);
			btnSupprimerJoueur.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					supprimerJoueur(joueur);
				}
			});
		}
		
		panelNomJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.m_panelListeJoueurs.add(panelNomJoueur);
		final JTextField txtNomNouveauJoueur = new JTextField(10);
		panelNomJoueur.add(new JLabel("Nom : "));
		panelNomJoueur.add(txtNomNouveauJoueur);
		
		panelObjetAnimeJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.m_panelListeJoueurs.add(panelObjetAnimeJoueur);
		panelObjetAnimeJoueur.add(new JLabel("Véhicule : "));
		
		List<ObjetAnimePlugin> objetsAnimes = new LinkedList<ObjetAnimePlugin>();
		for(Plugin plugin : Vroomaniacs.pluginManager.getPluginInstances()) {
			if(plugin instanceof ObjetAnimePlugin) {
				objetsAnimes.add((ObjetAnimePlugin)plugin);
			}
		}
		final JComboBox cmbObjetsAnimes = new JComboBox(objetsAnimes.toArray());
		panelObjetAnimeJoueur.add(cmbObjetsAnimes);
		
		panelBoutonJoueur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.m_panelListeJoueurs.add(panelBoutonJoueur);			
		JButton btnAjouterJoueur = new JButton(" + ");
		panelBoutonJoueur.add(btnAjouterJoueur);
		btnAjouterJoueur.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = null;
				if(txtNomNouveauJoueur.getText().isEmpty()) {
					message = "Le joueur doit avoir un nom.";
				}
				if(joueurExistant(txtNomNouveauJoueur.getText())) {
					message = "Le joueur " + txtNomNouveauJoueur.getText() + " existe déjà : veuillez choisir un autre nom de joueur.";
				}
				if(message != null) {
					JOptionPane.showMessageDialog(NouvellePartie.this,message,"Erreur d'ajout de joueur",JOptionPane.ERROR_MESSAGE);
				}
				else {
					
					Joueur joueur = null;
					try {
						ObjetAnimePlugin objetAnimeJoueur = ((ObjetAnimePlugin)cmbObjetsAnimes.getSelectedItem()).getClass().newInstance();
						joueur = new Joueur(txtNomNouveauJoueur.getText(),objetAnimeJoueur);
					} catch (InstantiationException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
					ajouterJoueur(joueur);
				}
			}
		});
	}
	
	public boolean joueurExistant(String p_nom) {
		for(Joueur joueur : this.m_joueurs) {
			if(joueur.getNom().equals(p_nom)) {
				return true;
			}
		}
		return false;
	}
	
	
	public void ajouterJoueur(Joueur p_joueur) {
		this.m_joueurs.add(p_joueur);
		this.construireListeJoueurs();
		this.pack();
		this.repaint();
	}
	
	public void supprimerJoueur(Joueur p_joueur) {
		int index = -1;
		for(Joueur joueur : this.m_joueurs) {
			if(joueur.getNom().equals(p_joueur.getNom())) {
				index = this.m_joueurs.indexOf(joueur);
			}
		}
		this.m_joueurs.remove(index);
		this.construireListeJoueurs();
		this.pack();
		this.repaint();
	}
	
	public NouvellePartie(final Vroomaniacs p_parent) {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		this.construireListeJoueurs();
		
		JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
		this.add(panelBoutons, BorderLayout.SOUTH);
		
		JButton btnCommencer = new JButton("Commencer");
		panelBoutons.add(btnCommencer);
		btnCommencer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(m_joueurs.isEmpty()) {
					JOptionPane.showMessageDialog(NouvellePartie.this,"J'ai jamais vu une course sans coureur.\nEn revanche un gars sans cerveau...","Erreur d'ajout de joueur",JOptionPane.ERROR_MESSAGE);
				}
				else {
					NouvellePartie.this.dispose();
					p_parent.debuterPartie(m_joueurs);
				}
			}
		});
		
		JButton btnAnnuler = new JButton("Annuler");
		panelBoutons.add(btnAnnuler);
		btnAnnuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NouvellePartie.this.dispose();
			}
		});
		
		this.pack();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width-this.getSize().width)/2,(screenSize.height-this.getSize().height)/2);
		
		this.setVisible(true);
	}
}