package fr.unice.miage.vroomaniacs.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.unice.miage.vroomaniacs.circuit.Circuit;
import fr.unice.miage.vroomaniacs.partie.Joueur;
import fr.unice.miage.vroomaniacs.persistance.Memento;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IElement;

@SuppressWarnings("serial")
public class NouvellePartie extends JFrame {
	
	private JPanel m_panelListeJoueurs = null;
	
	public void construireListeJoueurs() {
		if(this.m_panelListeJoueurs != null) {
			this.remove(this.m_panelListeJoueurs);
		}
		this.m_panelListeJoueurs = new JPanel(new GridLayout(Vroomaniacs.m_joueurs.size()+1, 1));
		this.add(this.m_panelListeJoueurs, BorderLayout.CENTER);
		
		JPanel panelNomJoueur;
		JPanel panelComportementJoueur;
		JPanel panelBoutonJoueur;
		for(final Joueur joueur : Vroomaniacs.m_joueurs) {
			panelNomJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT));
			this.m_panelListeJoueurs.add(panelNomJoueur);
			panelNomJoueur.add(new JLabel("Nom : "));
			panelNomJoueur.add(new JLabel(joueur.getNom()));
			
			panelComportementJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT));
			this.m_panelListeJoueurs.add(panelComportementJoueur);
			panelComportementJoueur.add(new JLabel("Comportement : "));
			panelComportementJoueur.add(new JLabel(joueur.getComportement()));
			
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
		
		panelComportementJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.m_panelListeJoueurs.add(panelComportementJoueur);
		panelComportementJoueur.add(new JLabel("Comportement : "));
		final JComboBox comportements = new JComboBox(new String[]{"Ivre","As du volant","Professionnel"});
		panelComportementJoueur.add(comportements);
		
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
					
					Joueur joueur = new Joueur(Vroomaniacs.new_id, txtNomNouveauJoueur.getText(),(String)comportements.getSelectedItem());
					ajouterJoueur(joueur);
					Vroomaniacs.new_id++;
				}
			}
		});
	}
	
	public boolean joueurExistant(String p_nom) {
		for(Joueur joueur : Vroomaniacs.m_joueurs) {
			if(joueur.getNom().equals(p_nom)) {
				return true;
			}
		}
		return false;
	}
	
	
	public void ajouterJoueur(Joueur p_joueur) {
		Vroomaniacs.m_joueurs.add(p_joueur);
		this.construireListeJoueurs();
		this.pack();
		this.repaint();
	}
	
	public void supprimerJoueur(Joueur p_joueur) {
		int index = -1;
		for(Joueur joueur : Vroomaniacs.m_joueurs) {
			if(joueur.getNom().equals(p_joueur.getNom())) {
				index = Vroomaniacs.m_joueurs.indexOf(joueur);
			}
		}
		Vroomaniacs.m_joueurs.remove(index);
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
		
		JButton btnSelectionCircuit = new JButton("Sélection du circuit");
		panelBoutons.add(btnSelectionCircuit);
		btnSelectionCircuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(NouvellePartie.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					Circuit loadedTrack = (Circuit)Memento.objLoading(file);
					for(IElement elem : loadedTrack) {
						Circuit.getInstance().addElement(elem);
					}
					Circuit.getInstance().estValide();
					p_parent.refreshCircuit();
				}
			}
		});
		
		JButton btnCommencer = new JButton("Commencer");
		panelBoutons.add(btnCommencer);
		btnCommencer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Vroomaniacs.m_joueurs.isEmpty()) {
					JOptionPane.showMessageDialog(NouvellePartie.this,"J'ai jamais vu une course sans coureur.\nEn revanche un gars sans cerveau...","Erreur d'ajout de joueur",JOptionPane.ERROR_MESSAGE);
				}
				else if(!Circuit.getInstance().estValide()) {
					JOptionPane.showMessageDialog(NouvellePartie.this,"Sur quoi tu veux rouler ? Faut un circuit boulet !","Erreur d'ajout de joueur",JOptionPane.ERROR_MESSAGE);
				}
				else {
					NouvellePartie.this.dispose();
					p_parent.debuterPartie();
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