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
	/** Le builder */
	private static Builder builder = new BuilderElement();
	/** Le panel contenant les &eacute;l&eacute;ments du circuit */
	private JPanel m_panelGrid;
	
	public EditeurCircuit(int p_nbLignes, int p_nbColonnes) {
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu menuFichier = new JMenu("Fichier");
		menuBar.add(menuFichier);
		
		JMenuItem itemSauvegarder = new JMenuItem("Sauvegarder", new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/disquette.png")));
		menuFichier.add(itemSauvegarder);
		itemSauvegarder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(EditeurCircuit.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					System.out.println("Sauvegarde dans le fichier " + file.getName() + "...");
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
		
		JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.add(panelNorth, BorderLayout.NORTH);
		
		JPanel panelCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.add(panelCenter, BorderLayout.CENTER);
		
		GridLayout gridLabels = new GridLayout(1,8);
		gridLabels.setHgap(10);
		JPanel panelLabels = new JPanel(gridLabels);
		panelNorth.add(panelLabels);
		
		this.m_panelGrid = new JPanel(new GridLayout(p_nbLignes, p_nbColonnes));
		panelCenter.add(this.m_panelGrid);
		
		JLabel lblRh = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/route-horizontale.png")));
		panelLabels.add(lblRh);
		lblRh.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				builder = new BuilderRouteH();
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
		
		JLabel lblRv = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/route-verticale.png")));
		panelLabels.add(lblRv);
		lblRv.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				builder = new BuilderRouteV();
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
		
		JLabel lblVid = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/virage-inferieur-droit.png")));
		panelLabels.add(lblVid);
		lblVid.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				builder = new BuilderVirageID();
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
		
		JLabel lblVig = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/virage-inferieur-gauche.png")));
		panelLabels.add(lblVig);
		lblVig.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				builder = new BuilderVirageIG();
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
		
		JLabel lblVsd = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/virage-superieur-droit.png")));
		panelLabels.add(lblVsd);
		lblVsd.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				builder = new BuilderVirageSD();
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
		
		JLabel lblVsg = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/virage-superieur-gauche.png")));
		panelLabels.add(lblVsg);
		lblVsg.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				builder = new BuilderVirageSG();
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
		
		JLabel lblRd = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/route-depart.png")));
		panelLabels.add(lblRd);
		lblRd.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				builder = new BuilderRouteDepart();
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
		
		JLabel lblGomme = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("./images/gomme.png")));
		panelLabels.add(lblGomme);
		lblGomme.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				builder = new BuilderElement();
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
		
		for(int x=0;x<p_nbLignes;x++) {
			for(int y=0;y<p_nbColonnes;y++) {
				builder.creerElement(this,x + "_" + y);
				this.m_panelGrid.add(builder.getElement());
				
				Circuit.getInstance().addElement(builder.getElement());
			}
		}
		
		this.pack();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width-this.getSize().width)/2,(screenSize.height-this.getSize().height)/2);
		
		this.setVisible(true);
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