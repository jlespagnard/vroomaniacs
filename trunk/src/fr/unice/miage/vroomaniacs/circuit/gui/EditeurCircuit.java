package fr.unice.miage.vroomaniacs.circuit.gui;

import java.awt.BorderLayout;
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
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import fr.unice.miage.vroomaniacs.circuit.builder.Builder;
import fr.unice.miage.vroomaniacs.circuit.builder.BuilderHerbe;
import fr.unice.miage.vroomaniacs.circuit.element.Element;
import fr.unice.miage.vroomaniacs.circuit.element.IElement;
import fr.unice.miage.vroomaniacs.persistance.Memento;

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
		
		File file = new File("./bin/fr/unice/miage/vroomaniacs/circuit/builder");
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String fileName) {
				Pattern pat = Pattern.compile(".*class");
				Matcher m = pat.matcher(fileName);
				return m.matches();
			}
		};
		String[] listFile = file.list(filter);
		
		GridLayout gridLabels = new GridLayout(1,listFile.length);
		gridLabels.setHgap(10);
		this.m_panelLabels = new JPanel(gridLabels);
		panelNorth.add(this.m_panelLabels);
		
		this.m_panelGrid = new JPanel(new GridLayout(this.m_nbLignes, this.m_nbColonnes));
		panelCenter.add(this.m_panelGrid);
		
		for(String fileName : listFile) {
			String className = fileName.substring(0, fileName.length()-6);
			className = "fr.unice.miage.vroomaniacs.circuit.builder." + className;
			try {
				System.out.println(className);
				Class cl = Class.forName(className);
				if((cl.getSuperclass() == Builder.class) && !cl.isInterface() && !Modifier.isAbstract(cl.getModifiers())) {
					Constructor construct = cl.getConstructor(new Class[]{});
					this.ajouterItemCircuit((Builder)construct.newInstance(new Object[]{}));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		
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
		builder = new BuilderHerbe();
		Circuit.getInstance().clearElements();
		
		for(int x=0;x<this.m_nbLignes;x++) {
			for(int y=0;y<this.m_nbColonnes;y++) {
				builder.creerElement(this,x + "_" + y);
				this.m_panelGrid.add((Element)builder.getElement());
				
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
		
		JMenuItem itemCharger = new JMenuItem("Charger", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/charger.png")));
		menuFichier.add(itemCharger);
		itemCharger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
		itemCharger.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(EditeurCircuit.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					//Michel : Deserialisation => DONE
					//loadedTrack est l'objet charge
					Circuit loadedTrack = (Circuit)Memento.objLoading(file);
					//System.out.println(loadedTrack.toString());
				}
			}
		});
		
		JMenuItem itemSauvegarder = new JMenuItem("Sauvegarder", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/disquette.png")));
		menuFichier.add(itemSauvegarder);
		itemSauvegarder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
		itemSauvegarder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Circuit.getInstance().estValide()) {
					JFileChooser fc = new JFileChooser();
					int returnVal = fc.showSaveDialog(EditeurCircuit.this);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						// Michel : Serialisation => DONE
						//System.out.println(Circuit.getInstance().toString());
						Memento memento = new Memento(Circuit.getInstance(),file);
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
		
		JMenuItem itemQuitter = new JMenuItem("Quitter", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/quitter.png")));
		menuFichier.add(itemQuitter);
		itemQuitter.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_DOWN_MASK));
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
		itemChangerTailleGrille.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_DOWN_MASK));
		itemChangerTailleGrille.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditeurCircuit.this.setVisible(false);
				new MenuEditeurCircuit();
			}
		});
		
		JMenuItem itemNettoyerGrille = new JMenuItem("Nettoyer la grille", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/clear.png")));
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
	private void ajouterItemCircuit(final Builder p_builder) {
		JLabel label = new JLabel(new ImageIcon(p_builder.getImage()));

		String name = p_builder.getUrlImage();
		name = name.split("/")[name.split("/").length-1];
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
			
			for(IElement elem : Circuit.getInstance()) {
				this.m_panelGrid.add((Element)elem);
			}
		}
		this.validate();
	}
}