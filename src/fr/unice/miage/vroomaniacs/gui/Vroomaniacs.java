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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

import fr.unice.miage.vroomaniacs.circuit.BufferedCircuitPanel;
import fr.unice.miage.vroomaniacs.circuit.Circuit;
import fr.unice.miage.vroomaniacs.circuit.gui.MenuEditeurCircuit;
import fr.unice.miage.vroomaniacs.partie.Joueur;
import fr.unice.miage.vroomaniacs.persistance.Memento;
import fr.unice.miage.vroomaniacs_plugins.builders.element.Element;
import fr.unice.miage.vroomaniacs_plugins.objetsAnimes.Dessinable;
import fr.unice.miage.vroomaniacs_plugins.objetsAnimes.ObjetAnime;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ComportementPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IElement;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IVroomaniacs;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;
import fr.unice.plugin.Plugin;
import fr.unice.plugin.PluginManager;

@SuppressWarnings("serial")
public class Vroomaniacs extends JFrame implements Runnable, IVroomaniacs {
	private final int TEMPS_ENTRE_IMAGES = 10;
	public final static PluginManager pluginManager = PluginManager.getPluginManager();;
	
	private JPanel m_circuitPanel = null;
	private JScrollPane m_scrollPanelEst = null;
	private JPanel m_panelJoueurs = null;
	private JPanel m_panelComportements = null;
	private Vector<Joueur> m_joueurs = new Vector<Joueur>();
	private	Joueur m_joueurSelectionne = null;
	private Map<JCheckBox,ComportementPlugin> m_comportements = null;
	private List<Dessinable> m_objetsAnimes = new LinkedList<Dessinable>();

	private Vector<Joueur> lstLoad;

	private JLabel nbTours = new JLabel();

	
	public Vroomaniacs() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setTitle("Vroomaniacs");
		this.setResizable(false);
		this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		try {			
			URL urlPlugins = new URL("file:plugins");
			try {
				urlPlugins.openStream();
			} catch (IOException e) {
				urlPlugins = new URL("file:ressources/plugins");
			}
			pluginManager.addJarURLsInDirectories(new URL[]{urlPlugins});
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
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
							ComportementPlugin tmp = m_comportements.get(source);
							Class newComportement = tmp.getClass();
							try{
								m_joueurSelectionne.getObjetAnime().ajouterComportement((ComportementPlugin)newComportement.newInstance());
							}catch(Exception exc){
							}
						}
						else {
							try{
								for (ComportementPlugin p : m_joueurSelectionne.getObjetAnime().getComportements()){
									if(p.getClass() == m_comportements.get(source).getClass())
										m_joueurSelectionne.getObjetAnime().supprimerComportement(p);
								}
							}catch(Exception exc){
							}
						}
						construireCheckBoxComportements();
					}
				});
				this.m_comportements.put(chk,comportement);
			}
		}
		
		this.construireMenu();
		
		this.m_circuitPanel = new BufferedCircuitPanel(this);
		JPanel englobeCircuit = new JPanel(new FlowLayout(FlowLayout.LEFT));
		englobeCircuit.add(this.m_circuitPanel);
		this.add(englobeCircuit,BorderLayout.CENTER);
		
		JPanel panelEst = new JPanel(new BorderLayout());
		this.m_scrollPanelEst = new JScrollPane(panelEst);
		this.m_scrollPanelEst.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()/3,(int)this.getPreferredSize().getHeight()));
		this.add(this.m_scrollPanelEst,BorderLayout.EAST);
		
		this.m_panelJoueurs = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelEst.add(this.m_panelJoueurs,BorderLayout.NORTH);
		JLabel jl = new JLabel("Nombre de tours parcourus");	
		this.m_panelComportements = new JPanel(new GridLayout(this.m_comportements.size()+1,1));
		JPanel panelNorthEst = new JPanel(new BorderLayout());
		panelNorthEst.add(this.m_panelComportements,BorderLayout.NORTH);
		panelNorthEst.add(jl,BorderLayout.SOUTH);
		JPanel afficheTour = new JPanel(new BorderLayout());
		afficheTour.add(jl,BorderLayout.WEST);
		afficheTour.add(nbTours, BorderLayout.EAST);
		panelEst.add(panelNorthEst,BorderLayout.CENTER);
		panelEst.add(afficheTour, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}
	
	private void construireMenu() {
		JMenuBar menu = new JMenuBar();
		this.add(menu, BorderLayout.NORTH);
		
		JMenu menuFichier = new JMenu("Fichier");
		menu.add(menuFichier);
		
		JMenuItem itemNouvellePartie = new JMenuItem("Nouvelle partie", new ImageIcon(this.getClass().getResource("/images/drapeau-damier.png")));
		menuFichier.add(itemNouvellePartie);
		itemNouvellePartie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_DOWN_MASK));
		itemNouvellePartie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("S�lection du circuit");
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
		
		JMenuItem itemQuitter = new JMenuItem("Quitter", new ImageIcon(this.getClass().getResource("/images/quitter.png")));
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
		
		JMenuItem itemEditerCircuit = new JMenuItem("Editer un circuit", new ImageIcon(this.getClass().getResource("/images/editer.png")));
		menuOutils.add(itemEditerCircuit);
		itemEditerCircuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,KeyEvent.CTRL_DOWN_MASK));
		itemEditerCircuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MenuEditeurCircuit();
			}
		});
		
		JMenu menuSave = new JMenu("Persistance");
		menu.add(menuSave);
		JMenuItem itemSave = new JMenuItem("Sauvegarder",new ImageIcon(this.getClass().getResource("/images/disquette.png")));
		menuSave.add(itemSave);
		itemSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("S�lectionnez le fichier de destination");
				int returnVal = fc.showSaveDialog(Vroomaniacs.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					int returnConfirm = JFileChooser.APPROVE_OPTION;
					if(file.exists()) {
						String message = "Le fichier "+file.getName()+" existe d�j� : voulez-vous le remplacer ?";
						returnConfirm = JOptionPane.showConfirmDialog(Vroomaniacs.this, message, "Fichier existant", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					}
					if(returnConfirm == JFileChooser.APPROVE_OPTION) {
						new Memento(m_joueurs,file);
					}
				}
			}
		});
		
		JMenuItem itemLoad = new JMenuItem("Charger",new ImageIcon(this.getClass().getResource("/images/charger.png")));
		menuSave.add(itemLoad);
		lstLoad = new Vector<Joueur>();
		itemLoad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("S�lectionnez le circuit � charger");
				int returnVal = fc.showOpenDialog(Vroomaniacs.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					lstLoad = new Vector<Joueur>();
					lstLoad = (Vector<Joueur>)Memento.objLoading(file);
					for(int i=0;i<lstLoad.size();i++)
					{
						lstLoad.get(i).getObjetAnime().setJeu(Vroomaniacs.this);
					}
					Vroomaniacs.this.debuterPartie(lstLoad);
				}
			}
		});
	}
	
	public void refreshCircuit() {
		this.m_circuitPanel.removeAll();
		
		List<IElement> elements = new LinkedList<IElement>();
		for(IElement elem : Circuit.getInstance()) {
			elem.setBorderElement(null);
			elements.add(elem);
			this.m_circuitPanel.add((Element)elem);
		}
		
		if(!elements.isEmpty()) {
			int x = Integer.parseInt(elements.get(elements.size()-1).getId().split("_")[0]);
			int y = Integer.parseInt(elements.get(elements.size()-1).getId().split("_")[1]);
			this.m_circuitPanel.setLayout(new GridLayout(x+1,y+1));
		}
		
		Vroomaniacs.this.validate();
		Vroomaniacs.this.repaint();
	}

	public void debuterPartie(List<Joueur> p_joueurs) {
		this.m_joueurs = new Vector<Joueur>(p_joueurs);
		
		this.m_objetsAnimes.clear();
		for(Joueur joueur : this.m_joueurs) {
			if(joueur.getObjetAnime() instanceof Dessinable) {
				this.m_objetsAnimes.add((Dessinable)joueur.getObjetAnime());
			}
		}
		
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
					nbTours.setText(String.valueOf(m_joueurSelectionne.m_nbTours>0?m_joueurSelectionne.m_nbTours:0));
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
	public void update(Graphics g) {
		this.paint(g);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.m_circuitPanel.repaint();
	}
	
	@Override
	public void run() {
		long tempsPrecedent, tempsPrisPourImagePrecedente, tempsAttente;

        tempsPrecedent = System.currentTimeMillis();

        while (true) {
            this.m_circuitPanel.repaint();

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
		return this.m_objetsAnimes;
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
		//Par defaut le Stand se trouve � cot� de la ligne de d�part
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

	@Override
	public void addTour(ObjetAnime arg0) {
		for(Joueur j : m_joueurs){
			if(j.getObjetAnime() == arg0){
				j.m_nbTours++;
				if(this.m_joueurSelectionne == j)
					this.nbTours.setText(String.valueOf(j.m_nbTours>0?m_joueurSelectionne.m_nbTours:0));
			}
		}
	}
	
	@Override
	public IElement getElementDepart() {
		return Circuit.getInstance().getElementDepart();

	}	
}