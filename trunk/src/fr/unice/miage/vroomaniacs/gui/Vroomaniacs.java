package fr.unice.miage.vroomaniacs.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import fr.unice.miage.vroomaniacs.circuit.CircuitPanel;
import fr.unice.miage.vroomaniacs.circuit.gui.MenuEditeurCircuit;
import fr.unice.miage.vroomaniacs.partie.Joueur;
import fr.unice.miage.vroomaniacs_plugins.objetsAnimes.Dessinable;

@SuppressWarnings("serial")
public class Vroomaniacs extends JFrame implements Runnable, IVroomaniacs {
	private final int TEMPS_ENTRE_IMAGES = 20;
	
	private CircuitPanel m_circuitPanel = new CircuitPanel();
	private JPanel m_panelScore;
	private JTable m_tableScore;
	public static List<Joueur> m_joueurs = new LinkedList<Joueur>();
	public static int new_id=1;
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
		
		int ligne = 0;
		Object[][] donnees = new Object[this.m_joueurs.size()][2];
		for (Joueur joueur:m_joueurs){
			donnees[ligne] = new Object[]{joueur.getNom(),joueur.getComportement()};
			ligne++;
		}
		String[] titreColonnes = { "joueur", "Comportement"}; 
		
		this.m_tableScore = new JTable(donnees, titreColonnes);
		this.m_panelScore = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scrollPaneScore = new JScrollPane(this.m_panelScore);
		scrollPaneScore.setPreferredSize(new Dimension((int)(this.getPreferredSize().width/4),this.getPreferredSize().height));
		this.add(scrollPaneScore, BorderLayout.EAST);
		this.m_panelScore.add(this.m_tableScore);
		
		
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
				new NouvellePartie(Vroomaniacs.this);
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

	public void debuterPartie() {
		this.m_panelScore.remove(this.m_tableScore);
		
		int ligne = 0;
		Object[][] donnees = new Object[this.m_joueurs.size()][2];
		for (Joueur joueur:m_joueurs){
			donnees[ligne] = new Object[]{joueur.getNom(),joueur.getComportement()};
			ligne++;
		}
		String[] titreColonnes = { "joueur", "Comportement"}; 
		
		this.m_tableScore = new JTable(donnees, titreColonnes);
		this.m_panelScore.add(this.m_tableScore);
		this.m_panelScore.repaint();
		this.validate();
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics g2 = this.m_circuitPanel.getGraphics();
		// Il faut dessiner les voitures sur le circuit avec g2
		// car le circuit est dans le panel m_circuitPanel
//		for(Dessinable o : getListeObjetDessinable()){
//			o.dessineToi(g2);
//			if(o instanceof Deplacable){
//				Deplacable od = (Deplacable)o;
//				od.deplaceToi();
//			}
//		}
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
//			objetsADessiner.add(j.getObjetAnime());
		}
		return objetsADessiner;
	}
	
	public static void main(String[] args) {
		new Thread(new Vroomaniacs()).start();
	}
	
}
