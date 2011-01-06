package fr.unice.miage.vroomaniacs.circuit.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MenuEditeurCircuit extends JFrame {

	public MenuEditeurCircuit() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(1,5));
		
		this.add(new JLabel("Lignes "));
		final JTextField txtLigne = new JTextField(4);
		txtLigne.setText("10");
		this.add(txtLigne);
		
		this.add(new JLabel("Colonnes "));
		final JTextField txtColonne = new JTextField(4);
		txtColonne.setText("10");
		this.add(txtColonne);
		
		final JButton btnOk = new JButton("OK");
		this.add(btnOk);
		
		final JFrame errorFrame = new JFrame("Erreur");
		final JLabel lblError = new JLabel();
		errorFrame.add(lblError, BorderLayout.CENTER);
		final JButton btnErrorFrame = new JButton("OK");
		errorFrame.add(btnErrorFrame, BorderLayout.EAST);
		errorFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
		btnErrorFrame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnErrorFrame) {
					errorFrame.dispose();
				}
			}
		});
			
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnOk) {
					int nbLignes, nbColonnes;
					
					try {
						nbLignes = Integer.parseInt(txtLigne.getText());
						if(nbLignes <= 0) {
							throw new NumberFormatException();
						}
					}
					catch(NumberFormatException ex) {
						lblError.setText("Vous devez saisir un nombre entier positif pour le nombre de lignes.");
						errorFrame.pack();
						errorFrame.setVisible(true);
						return;
					}
					
					try {
						nbColonnes = Integer.parseInt(txtColonne.getText());
						if(nbColonnes <= 0) {
							throw new NumberFormatException();
						}
					}
					catch(NumberFormatException ex) {
						lblError.setText("Vous devez saisir un nombre entier positif pour le nombre de colonnes.");
						errorFrame.pack();
						errorFrame.setVisible(true);
						return;
					}
					
					creerEditionCircuitUI(nbLignes,nbColonnes);
				}
			}
		});
		
		this.pack();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width-this.getSize().width)/2,(screenSize.height-this.getSize().height)/2);
		
		this.setVisible(true);
	}

	private void creerEditionCircuitUI(int p_nbLignes, int p_nbColonnes) {
		this.dispose();
		new EditeurCircuit(p_nbLignes, p_nbColonnes);
	}
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				new MenuEditeurCircuit();
			}
		}).start();
	}
}