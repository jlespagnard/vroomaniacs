package fr.unice.miage.vroomaniacs.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import fr.unice.miage.vroomaniacs.circuit.Circuit;
import fr.unice.miage.vroomaniacs.circuit.CircuitPanel;
import fr.unice.miage.vroomaniacs.circuit.gui.MenuEditeurCircuit;
import fr.unice.miage.vroomaniacs.partie.Joueur;
import fr.unice.miage.vroomaniacs.persistance.Memento;
import fr.unice.miage.vroomaniacs_plugins.objetsAnimes.Deplacable;
import fr.unice.miage.vroomaniacs_plugins.objetsAnimes.Dessinable;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ComportementPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IElement;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IVroomaniacs;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;
import fr.unice.plugin.Plugin;
import fr.unice.plugin.PluginManager;

@SuppressWarnings("serial")
public class Vroomaniacs extends JFrame implements Runnable, IVroomaniacs {
	private final int TEMPS_ENTRE_IMAGES = 100;
	public static PluginManager pluginManager;
	
	private CircuitPanel m_circuitPanel = new CircuitPanel();
	private JScrollPane m_scrollPanelEst;
	private JPanel m_panelJoueurs;
	private JPanel m_panelComportements;
	private Vector<Joueur> m_joueurs = new Vector<Joueur>();
	private	Joueur m_joueurSelectionne = null;
	private Map<JCheckBox,ComportementPlugin> m_comportements;

	
	
	public Vroomaniacs() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setTitle("Vroomaniacs");
		this.setResizable(false);
		this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		pluginManager = PluginManager.getPluginManager();
		try {
			pluginManager.addJarURLsInDirectories(new URL[]{new URL("file:plugins")});
		} catch (MalformedURLException e) {
			e.printStackTrace();
			this.dispose();
		}
		pluginManager.loadPlugins();
		
		this.m_comportements = new LinkedHashMap<JCheckBox,ComportementPlugin>();
		ComportementPlugin comportement;
		JCheckBox chk;
		for(Plugin plugin : pluginManager.getPluginInstances()) {
			if(plugin instanceof ComportementPlugin) {
				comportement = (ComportementPlugin)plugin;
				chk = new JCheckBox(comportement.toString());
				chk.setSelected(false);
				chk.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JCheckBox source = (JCheckBox)e.getSource();
						if(source.isSelected()) {
							System.out.println("Ajout de " + m_comportements.get(source).toString() + " à " + m_joueurSelectionne.getNom());
							m_joueurSelectionne.getObjetAnime().ajouterComportement(m_comportements.get(source));
						}
						else {
							System.out.println("Suppression de " + m_comportements.get(source).toString() + " à " + m_joueurSelectionne.getNom());
							m_joueurSelectionne.getObjetAnime().supprimerComportement(m_comportements.get(source));
						}
						construireCheckBoxComportements();
					}
				});
				this.m_comportements.put(chk,comportement);
			}
		}
		
		this.construireMenu();
		
		this.add(this.m_circuitPanel,BorderLayout.CENTER);
		
		JPanel panelEst = new JPanel(new BorderLayout());
		this.m_scrollPanelEst = new JScrollPane(panelEst);
		this.m_scrollPanelEst.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()/3,(int)this.getPreferredSize().getHeight()));
		this.add(this.m_scrollPanelEst,BorderLayout.EAST);
		
		this.m_panelJoueurs = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelEst.add(this.m_panelJoueurs,BorderLayout.NORTH);
		
		this.m_panelComportements = new JPanel(new GridLayout(this.m_comportements.size()+1,1));
		JPanel panelNorthEst = new JPanel(new BorderLayout());
		panelNorthEst.add(this.m_panelComportements,BorderLayout.NORTH);
		panelEst.add(panelNorthEst,BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
	}
	
	private void construireMenu() {
		JMenuBar menu = new JMenuBar();
		this.add(menu, BorderLayout.NORTH);
		
		JMenu menuFichier = new JMenu("Fichier");
		menu.add(menuFichier);
		
		JMenuItem itemNouvellePartie = new JMenuItem("Nouvelle partie", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/drapeau-damier.png")));
		menuFichier.add(itemNouvellePartie);
		itemNouvellePartie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_DOWN_MASK));
		itemNouvellePartie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Sélection du circuit");
				int returnVal = fc.showOpenDialog(Vroomaniacs.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					Circuit loadedTrack = (Circuit)Memento.objLoading(file);
					for(IElement elem : loadedTrack) {
						Circuit.getInstance().addElement(elem);
					}
					if(Circuit.getInstance().estValide()) {
						new NouvellePartie(Vroomaniacs.this);
					}
				}
			}
		});
		
		menuFichier.addSeparator();
		
		JMenuItem itemQuitter = new JMenuItem("Quitter", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/quitter.png")));
		itemQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_DOWN_MASK));
		menuFichier.add(itemQuitter);
		itemQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenu menuOutils = new JMenu("Outils");
		menu.add(menuOutils);
		
		JMenuItem itemEditerCircuit = new JMenuItem("Editer un circuit", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/editer.png")));
		menuOutils.add(itemEditerCircuit);
		itemEditerCircuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,KeyEvent.CTRL_DOWN_MASK));
		itemEditerCircuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MenuEditeurCircuit();
			}
		});
	}
	
	public void refreshCircuit() {
		Vroomaniacs.this.remove(m_circuitPanel);
		m_circuitPanel = new CircuitPanel();
		Vroomaniacs.this.add(m_circuitPanel, BorderLayout.CENTER);
		Vroomaniacs.this.validate();
		Vroomaniacs.this.repaint();
	}

	public void debuterPartie(List<Joueur> p_joueurs) {
		this.m_joueurs = new Vector<Joueur>(p_joueurs);
		
		this.m_panelJoueurs.removeAll();

		this.construireCheckBoxComportements();
		
		final JComboBox cmbJoueurs = new JComboBox(this.m_joueurs);
		this.m_panelJoueurs.add(cmbJoueurs);
		this.m_joueurSelectionne = this.m_joueurs.get(0);
		cmbJoueurs.setRenderer(new ComboBoxJoueursRenderer());
		cmbJoueurs.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					m_joueurSelectionne = (Joueur)e.getItem();
					JCheckBox chk;
					ComportementPlugin comportement;
					for(Map.Entry<JCheckBox, ComportementPlugin> entry : m_comportements.entrySet()) {
						chk = entry.getKey();
						comportement = entry.getValue();
						
						if(m_joueurSelectionne.getObjetAnime().getComportements().contains(comportement)) {
							chk.setSelected(true);
						}
						else {
							chk.setSelected(false);
						}
					}
				}
				construireCheckBoxComportements();
			}
		});
		
		this.construireCheckBoxComportements();
		
		this.m_scrollPanelEst.repaint();
		this.m_scrollPanelEst.validate();
		
		this.refreshCircuit();
		
		this.repaint();
	}
	
	private void construireCheckBoxComportements() {
		this.m_panelComportements.removeAll();
		for(JCheckBox chkBox : m_comportements.keySet()) {
			this.m_panelComportements.add(chkBox);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics g2 = this.m_circuitPanel.getGraphics();
		// Il faut dessiner les voitures sur le circuit avec g2
		// car le circuit est dans le panel m_circuitPanel
		for(Dessinable o : getListeObjetDessinable()){
			o.dessineToi(g2);
			if(o instanceof Deplacable){
				Deplacable od = (Deplacable)o;
				od.deplaceToi();
			}
		}
		this.validate();
	}
	
	@Override
	public void run() {
		long tempsPrecedent, tempsPrisPourImagePrecedente, tempsAttente;

        tempsPrecedent = System.currentTimeMillis();

        while (true) {
            repaint();

            tempsPrisPourImagePrecedente = System.currentTimeMillis() - tempsPrecedent;
            tempsAttente = TEMPS_ENTRE_IMAGES - tempsPrisPourImagePrecedente;

            if(tempsAttente < 0) {
                tempsAttente = 2;
            }
            try {
                Thread.sleep(tempsAttente);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tempsPrecedent = System.currentTimeMillis();
        }
	}

	@Override
	public List<Dessinable> getListeObjetDessinable() {
		List<Dessinable> objetsADessiner = new ArrayList<Dessinable>();
		for(Joueur j : m_joueurs){
			objetsADessiner.add((Dessinable)j.getObjetAnime());
		}
		return objetsADessiner;
	}
	
	public static void main(String[] args) {
		new Thread(new Vroomaniacs()).start();
	}

	@Override
	public void ajouteObjet(Dessinable arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public Point getStand() {
		//Par defaut le Stand se trouve à coté de la ligne de départ
		double x = Utils.getPointCentre(Circuit.getInstance().getElementDepart()).getX();
		double y = Utils.getPointCentre(Circuit.getInstance().getElementDepart()).getY();
		double largeurRoute = Utils.getLargeurRoute();
		Point stand = new Point((int)(x+largeurRoute), (int)y);
		return stand;
	}

	@Override
	public List<Point> getChemin() {
		return Circuit.getInstance().getChemin();
	}	
}