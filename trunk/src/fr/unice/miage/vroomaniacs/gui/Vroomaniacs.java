package fr.unice.miage.vroomaniacs.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.unice.miage.vroomaniacs.circuit.Circuit;
import fr.unice.miage.vroomaniacs.circuit.CircuitPanel;
import fr.unice.miage.vroomaniacs.circuit.element.IElement;
import fr.unice.miage.vroomaniacs.persistance.Memento;

@SuppressWarnings("serial")
public class Vroomaniacs extends JFrame {
	private CircuitPanel m_circuitPanel = new CircuitPanel();
	
	public Vroomaniacs() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setTitle("Vroomaniacs");
		this.setResizable(false);
		this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.construireMenu();
		
		this.add(this.m_circuitPanel, BorderLayout.CENTER);
		
		JPanel panelScore = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scrollPaneScore = new JScrollPane(panelScore);
		scrollPaneScore.setPreferredSize(new Dimension((int)(this.getPreferredSize().width/4),this.getPreferredSize().height));
		this.add(scrollPaneScore, BorderLayout.EAST);
		panelScore.add(new JLabel("DONNÉES DIVERSES"));
		
		this.pack();
		this.setVisible(true);
	}
	
	private void construireMenu() {
		JMenuBar menu = new JMenuBar();
		this.add(menu, BorderLayout.NORTH);
		
		JMenu menuFichier = new JMenu("Fichier");
		menu.add(menuFichier);
		
		JMenuItem itemNouvellePartie = new JMenuItem("Nouvelle partie");
		menuFichier.add(itemNouvellePartie);
		itemNouvellePartie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(Vroomaniacs.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					Circuit loadedTrack = (Circuit)Memento.objLoading(file);
					for(IElement elem : loadedTrack) {
						Circuit.getInstance().addElement(elem);
					}
					Circuit.getInstance().estValide();
					Vroomaniacs.this.remove(m_circuitPanel);
					m_circuitPanel = new CircuitPanel();
					Vroomaniacs.this.add(m_circuitPanel, BorderLayout.CENTER);
					Vroomaniacs.this.validate();
					Vroomaniacs.this.repaint();
				}
			}
		});
		
		menuFichier.addSeparator();
		
		JMenuItem itemQuitter = new JMenuItem("Quitter");
		menuFichier.add(itemQuitter);
		itemQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				new Vroomaniacs();
			}
		}).start();
	}
}
