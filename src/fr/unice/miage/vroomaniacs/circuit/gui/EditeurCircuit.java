package fr.unice.miage.vroomaniacs.circuit.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import fr.unice.miage.vroomaniacs.circuit.Circuit;
import fr.unice.miage.vroomaniacs.gui.Vroomaniacs;
import fr.unice.miage.vroomaniacs.persistance.Memento;
import fr.unice.miage.vroomaniacs_plugins.builders.BuilderHerbe;
import fr.unice.miage.vroomaniacs_plugins.builders.element.Element;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.BuilderPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IElement;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;
import fr.unice.plugin.Plugin;

@SuppressWarnings("serial")
public class EditeurCircuit extends JFrame implements IEditeurCircuit {
	/** Le nombre de lignes et de colonnes de la grille */
	private int m_nbLignes, m_nbColonnes;
	/** Le builder utiliser pour construire les &eacute;l&eacute;ments du circuit */
	private BuilderPlugin m_builder;
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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.construireMenuBar();
		
		this.construireMenuElementsCircuit();
		
		JPanel panelCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JScrollPane scrollPaneCenter = new JScrollPane(panelCenter);
		scrollPaneCenter.setPreferredSize(new Dimension(420, 420));
		this.add(scrollPaneCenter, BorderLayout.CENTER);
		
		this.m_panelGrid = new JPanel(new GridLayout(this.m_nbLignes, this.m_nbColonnes));
		panelCenter.add(this.m_panelGrid);
		
		this.construireGrilleParDefaut();
		
		this.pack();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width-this.getSize().width)/2,(screenSize.height-this.getSize().height)/2);
		
		this.setVisible(true);
	}
	
	private void construireMenuElementsCircuit() {
		List<BuilderPlugin> builderPlugins = this.getBuilderPlugins();
		
		JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JScrollPane scrollPaneNorth = new JScrollPane(panelNorth);
		scrollPaneNorth.setPreferredSize(new Dimension(420,(int)(Utils.ELEM_DIM.getHeight()+20)));
		this.add(scrollPaneNorth, BorderLayout.NORTH);
		
		if(builderPlugins != null && builderPlugins.size() > 0) {
			GridLayout gridLabels = new GridLayout(1,builderPlugins.size());
			gridLabels.setHgap(10);
			this.m_panelLabels = new JPanel(gridLabels);
			panelNorth.add(this.m_panelLabels);
			
			for(BuilderPlugin builder : builderPlugins) {
				if(!Modifier.isAbstract(builder.getClass().getModifiers())) {
					this.ajouterItemCircuit(builder);
				}
			}
		}
	}

	private List<BuilderPlugin> getBuilderPlugins() {
		List<BuilderPlugin> objetsAnimes = new LinkedList<BuilderPlugin>();
		for(Plugin plugin : Vroomaniacs.pluginManager.getPluginInstances()) {
			if(plugin instanceof BuilderPlugin) {
				objetsAnimes.add((BuilderPlugin)plugin);
			}
		}
		return objetsAnimes;
	}
	
	/**
	 * Construit la grille par d&eacute;faut avec des &eacute;l&eacute;ments neutres.
	 */
	private void construireGrilleParDefaut() {
		this.m_builder = new BuilderHerbe();
		Circuit.getInstance().clearElements();
		
		for(int x=0;x<this.m_nbLignes;x++) {
			for(int y=0;y<this.m_nbColonnes;y++) {
				this.m_builder.creerElement(this,x+"_"+y);
				this.m_builder.getElement().setBorderElement(new PointillerBorder(Color.GRAY,1));
				this.m_panelGrid.add((Element)this.m_builder.getElement());
				
				Circuit.getInstance().addElement(this.m_builder.getElement());
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
		
		JMenuItem itemCharger = new JMenuItem("Charger", new ImageIcon(this.getClass().getResource("/images/charger.png")));
		menuFichier.add(itemCharger);
		itemCharger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
		itemCharger.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Sélectionnez le circuit à charger");
				int returnVal = fc.showOpenDialog(EditeurCircuit.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					Circuit loadedTrack = (Circuit)Memento.objLoading(file);
					for(IElement elem : loadedTrack) {
						elem.setEditeurCircuit(EditeurCircuit.this);
						Circuit.getInstance().addElement(elem);
					}
					Circuit.getInstance().estValide();
					EditeurCircuit.this.paint(EditeurCircuit.this.getGraphics());
				}
			}
		});
		
		JMenuItem itemSauvegarder = new JMenuItem("Sauvegarder", new ImageIcon(this.getClass().getResource("/images/disquette.png")));
		menuFichier.add(itemSauvegarder);
		itemSauvegarder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
		itemSauvegarder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Circuit.getInstance().estValide()) {
					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Sélectionnez le fichier de destination");
					int returnVal = fc.showSaveDialog(EditeurCircuit.this);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						int returnConfirm = JFileChooser.APPROVE_OPTION;
						if(file.exists()) {
							String message = "Le fichier "+file.getName()+" existe déjà : voulez-vous le remplacer ?";
							returnConfirm = JOptionPane.showConfirmDialog(EditeurCircuit.this, message, "Fichier existant", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						}
						if(returnConfirm == JFileChooser.APPROVE_OPTION) {
							new Memento(Circuit.getInstance(),file);
						}
					}
				}
				else {
					String messageErreur;
					if(Circuit.getInstance().getElementDepart() == null) {
						messageErreur = "Le circuit doit comporter un élément de départ.";
					}
					else {
						messageErreur = "Le circuit doit être fermé.";
					}
					JOptionPane.showMessageDialog(EditeurCircuit.this,messageErreur,"Erreur de sauvegarde",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		menuFichier.addSeparator();
		
		JMenuItem itemQuitter = new JMenuItem("Quitter", new ImageIcon(this.getClass().getResource("/images/quitter.png")));
		menuFichier.add(itemQuitter);
		itemQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_DOWN_MASK));
		itemQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditeurCircuit.this.dispose();
			}
		});
		
		JMenu menuEdition = new JMenu("Edition");
		menuBar.add(menuEdition);
		
		JMenuItem itemChangerTailleGrille = new JMenuItem("Changer la taille de la grille", new ImageIcon(this.getClass().getResource("/images/resize.png")));
		menuEdition.add(itemChangerTailleGrille);
		itemChangerTailleGrille.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_DOWN_MASK));
		itemChangerTailleGrille.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditeurCircuit.this.dispose();
				new MenuEditeurCircuit();
			}
		});
		
		JMenuItem itemNettoyerGrille = new JMenuItem("Nettoyer la grille", new ImageIcon(this.getClass().getResource("/images/clear.png")));
		menuEdition.add(itemNettoyerGrille);
		itemNettoyerGrille.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_DOWN_MASK));
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
	private void ajouterItemCircuit(final BuilderPlugin p_builder) {
		JLabel label = new JLabel(p_builder.getImage());
		
		label.setToolTipText(p_builder.getName());
		this.m_panelLabels.add(label);
		
		label.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				m_builder = p_builder;
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
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(this.m_panelGrid != null) {
			this.m_panelGrid.removeAll();
			
			for(IElement elem : Circuit.getInstance()) {
				this.m_panelGrid.add((Element)elem);
			}
		}
		this.validate();
	}

	@Override
	public void ajouterElementCircuit(String p_idElement) {
		this.m_builder.creerElement(this,p_idElement);
		this.m_builder.getElement().setBorderElement(new PointillerBorder(Color.GRAY,1));
		Circuit.getInstance().addElement(this.m_builder.getElement());
		this.repaint();
	}
}