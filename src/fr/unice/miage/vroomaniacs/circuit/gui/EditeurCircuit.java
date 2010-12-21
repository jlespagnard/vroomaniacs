package fr.unice.miage.vroomaniacs.circuit.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.unice.miage.vroomaniacs.circuit.Circuit;
import fr.unice.miage.vroomaniacs.circuit.builder.Builder;
import fr.unice.miage.vroomaniacs.circuit.builder.BuilderElement;
import fr.unice.miage.vroomaniacs.circuit.builder.BuilderRouteDepart;
import fr.unice.miage.vroomaniacs.circuit.builder.BuilderRouteH;
import fr.unice.miage.vroomaniacs.circuit.builder.BuilderRouteV;
import fr.unice.miage.vroomaniacs.circuit.builder.BuilderVirageID;
import fr.unice.miage.vroomaniacs.circuit.builder.BuilderVirageIG;
import fr.unice.miage.vroomaniacs.circuit.builder.BuilderVirageSD;
import fr.unice.miage.vroomaniacs.circuit.builder.BuilderVirageSG;
import fr.unice.miage.vroomaniacs.circuit.element.Element;

@SuppressWarnings("serial")
public class EditeurCircuit extends JFrame {
	/** Le nombre de lignes et de colonnes de la grille */
	private int m_nbLignes, m_nbColonnes;
	/** Le builder utiliser pour construire les &eacute;l&eacute;ments du circuit */
	private static Builder builder;
	/** Le panel contenant les items pour l'&eacute;dition du cricuit. */
	private JPanel m_panelLabels;
	/** Le panel contenant les &eacute;l&eacute;ments du circuit */
	private JPanel m_panelGrid;
	
	/**
	 * Constructeur.
	 * 
	 * @param p_nbLignes	le nombre de lignes de la grille
	 * @param p_nbColonnes	le nombre de colonnes de la grille
	 */
	public EditeurCircuit(int p_nbLignes, int p_nbColonnes) {
		this.m_nbLignes = p_nbLignes;
		this.m_nbColonnes = p_nbColonnes;
		
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.construireMenuBar();
		
		JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.add(panelNorth, BorderLayout.NORTH);
		
		JPanel panelCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JScrollPane scrollPaneCenter = new JScrollPane(panelCenter);
		scrollPaneCenter.setPreferredSize(new Dimension(420, 420));
		this.add(scrollPaneCenter, BorderLayout.CENTER);
		
		GridLayout gridLabels = new GridLayout(1,8);
		gridLabels.setHgap(10);
		this.m_panelLabels = new JPanel(gridLabels);
		panelNorth.add(this.m_panelLabels);
		
		this.m_panelGrid = new JPanel(new GridLayout(this.m_nbLignes, this.m_nbColonnes));
		panelCenter.add(this.m_panelGrid);
		
		this.ajouterItemCircuit("./images/route-horizontale.png", new BuilderRouteH());
		this.ajouterItemCircuit("./images/route-verticale.png", new BuilderRouteV());
		this.ajouterItemCircuit("./images/virage-inferieur-droit.png", new BuilderVirageID());
		this.ajouterItemCircuit("./images/virage-inferieur-gauche.png", new BuilderVirageIG());
		this.ajouterItemCircuit("./images/virage-superieur-droit.png", new BuilderVirageSD());
		this.ajouterItemCircuit("./images/virage-superieur-gauche.png", new BuilderVirageSG());
		this.ajouterItemCircuit("./images/route-depart.png", new BuilderRouteDepart());
		this.ajouterItemCircuit("./images/gomme.png", new BuilderElement());
		
		this.construireGrilleParDefaut();
		
		this.pack();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width-this.getSize().width)/2,(screenSize.height-this.getSize().height)/2);
		
		this.setVisible(true);
	}
	
	/**
	 * Construit la grille par d&eacute;faut avec des &eacute;l&eacute;ments neutres.
	 */
	private void construireGrilleParDefaut() {
		builder = new BuilderElement();
		Circuit.getInstance().clearElements();
		
		for(int x=0;x<this.m_nbLignes;x++) {
			for(int y=0;y<this.m_nbColonnes;y++) {
				builder.creerElement(this,x + "_" + y);
				this.m_panelGrid.add(builder.getElement());
				
				Circuit.getInstance().addElement(builder.getElement());
			}
		}
	}

	/**
	 * Construit la barre de menu de l'&eacute;diteur de circuit.
	 */
	private void construireMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu menuFichier = new JMenu("Fichier");
		menuBar.add(menuFichier);
		
		JMenuItem itemSauvegarder = new JMenuItem("Sauvegarder", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/disquette.png")));
		menuFichier.add(itemSauvegarder);
		itemSauvegarder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Circuit.getInstance().estValide()) {
					System.out.println("Circuit valide.");
					JFileChooser fc = new JFileChooser();
					int returnVal = fc.showSaveDialog(EditeurCircuit.this);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						System.out.println("Sauvegarde dans le fichier " + file.getName() + "...");
					}
				}
				else {
					System.out.println("Circuit non valide.");
				}
			}
		});
		
		menuFichier.addSeparator();
		
		JMenuItem itemQuitter = new JMenuItem("Quitter", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/quitter.png")));
		menuFichier.add(itemQuitter);
		itemQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenu menuEdition = new JMenu("Edition");
		menuBar.add(menuEdition);
		
		JMenuItem itemChangerTailleGrille = new JMenuItem("Changer la taille de la grille", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/resize.png")));
		menuEdition.add(itemChangerTailleGrille);
		itemChangerTailleGrille.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditeurCircuit.this.setVisible(false);
				new MenuEditeurCircuit();
			}
		});
		
		JMenuItem itemNettoyerGrille = new JMenuItem("Nettoyer la grille", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/clear.png")));
		menuEdition.add(itemNettoyerGrille);
		itemNettoyerGrille.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditeurCircuit.this.construireGrilleParDefaut();
				EditeurCircuit.this.repaint();
			}
		});
	}
	
	/**
	 * Ajoute un item pour l'&eacute;dition de circuit.
	 * 
	 * @param p_urlImage	l'URL de l'image &agrave; utiliser pour repr&eacute;senter l'item
	 * @param p_builder		le builder &agrave; utiliser pour cr&eacute; l'&eacute;l&eacute;ment dans le circuit
	 */
	private void ajouterItemCircuit(String p_urlImage, final Builder p_builder) {
		JLabel label = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(p_urlImage)));
		String name = p_urlImage.split("/")[p_urlImage.split("/").length-1];
		name = name.replaceAll("-"," ");
		name = name.substring(0, name.length()-4);
		label.setToolTipText(name.toUpperCase());
		this.m_panelLabels.add(label);
		label.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				builder = p_builder;
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
	}
	
	/**
	 * @return	le builder en cours d'utilisation
	 */
	public static Builder getBuilder() {
		return builder;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(this.m_panelGrid != null) {
			this.m_panelGrid.removeAll();
			
			for(Element elem : Circuit.getInstance()) {
				this.m_panelGrid.add(elem);
			}
		}
		this.validate();
	}
}