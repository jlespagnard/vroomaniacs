package fr.unice.miage.vroomaniacs_plugins.comportements;

import java.awt.Point;
import java.util.List;

import fr.unice.miage.vroomaniacs_plugins.objetsAnimes.ObjetAnime;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ComportementPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;

/**
 * @author Anthony
 * Le comportement suit le chemin normal du circuit.
 */public class ComportementSuitVoiture extends ComportementSuitChemin implements ComportementPlugin {

	    public ObjetAnime m_voitureASuivre;
	    public int distanceValidationPassage = 10;
	    private boolean m_voitureSet = false;
	    private boolean m_vitesseSet = false;
	    private double m_vitesseRattrapage = 1.6;
	    
	    public ComportementSuitVoiture(){
	    	super();
	    }

	    public ComportementSuitVoiture(ObjetAnime voitureASuivre) {
	    	super();
	        this.m_voitureASuivre = voitureASuivre;
	    }

	    public void deplace(ObjetAnimePlugin o) {
	    	if(o.getJeu().getListeObjetDessinable().size()==1){
	    		super.deplace(o);
	    	} else {
	    		if(!m_voitureSet){
	    			int index = o.getJeu().getListeObjetDessinable().indexOf(o);
	    			//	On suit soit le joueur entré avant nous ou le dernier arrivé si on est le premier arrivé
	    			this.m_voitureASuivre = index==0?(ObjetAnime)o.getJeu().getListeObjetDessinable().get(o.getJeu().getListeObjetDessinable().size()-1):(ObjetAnime)o.getJeu().getListeObjetDessinable().get(index-1);
	    			m_voitureSet = true;
	    			System.out.println(m_voitureASuivre.getName());
	    		} else {
	    			//Si on est proche de la voiture à suivre, on se calle sur sa vitesse et son chemin
	    			if (Utils.distance(o.getXPos(), o.getYPos(), (int)m_voitureASuivre.getXPos(),(int)m_voitureASuivre.getYPos()) < distanceValidationPassage) {
	    				//Nouvelle direction a suivre
	    				double dx = m_voitureASuivre.getXPos() - o.getXPos() - distanceValidationPassage;
	    				double dy = m_voitureASuivre.getYPos() - o.getYPos() - distanceValidationPassage;
	    				o.setDirection(Math.atan2(dy, dx));
	    				o.normaliseDirection();
	    				o.setVitesse(m_voitureASuivre.getVitesse());
	    			} else {
	    				//Sinon on suit le circuit à une vitesse superieur à celle du joueur suivi 
	    				if(!m_vitesseSet){
	    					o.setVitesse(m_voitureASuivre.getVitesse() * m_vitesseRattrapage);
	    					m_vitesseSet = true;
	    				}
	    				super.deplace(o);
	    			}
	    		}
	    	}
	    }

		@Override
		public boolean canProcess(Object arg0) {
			return true;
		}

		@Override
		public String getDescription() {
			return "Comportement Suit Voiture : La voiture suit une des voitures du jeu si il y en a une autre ";
		}

		@Override
		public String getName() {
			return "Comportement Suit Voiture";
		}

		@Override
		public Class getType() {
			return ComportementSuitVoiture.class;
		}

		@Override
		public String getVersion() {
			return "1.0";
		}

		@Override
		public boolean matches(Class arg0, String arg1, Object arg2) {
			return true;
		}
		
		@Override
		public String toString() {
			return this.getName();
		}
	}
