package fr.unice.miage.vroomaniacs.circuit;

import java.awt.Point;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IElement;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IRoute;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;

/**
 * @author Julien Lespagnard
 * @version 1.0
 */
public class Circuit implements Iterable<IElement>, Serializable {
	private static final long serialVersionUID = 10L;
	
	/** Liste des &eacute;l&eacute;ments composant le circuit.<br />
	 *  Les &eacute;l&eacute;ments sont rang&eacute;s par ligne puis par colonne.
	 */
	private Map<String, IElement> m_elements;
	/** ID de l'&eacute;l&eacute;ment de d&eacute;part du cricuit. */
	private  String m_idElementDepart = null;
	/**	Liste des points formant le chemin du circuit. */
	private List<Point> m_chemin;
	
	/**
	 * Constructeur.
	 */
	private Circuit() {
		this.m_elements = new LinkedHashMap<String, IElement>();
		this.m_chemin = new LinkedList<Point>();
	}
	
	/**
	 * Holder de la classe circuit charg&eacute; au preemier appel de celui-ci.
	 */
	private static class CircuitHolder {
		private static final Circuit m_instance = new Circuit();
	}
	
	/**
	 * @return	l'instance unique de la classe <code>Circuit</code>
	 */
	public static Circuit getInstance() {
		return CircuitHolder.m_instance;
	}
	
	/**
	 * Retourne l'&eacute;l&eacute;ment du circuit plac&eacute; 
	 * &agrave; la ligne <code>p_ligne</code> et colonne <code>p_colonne</code>.
	 * 
	 * @param p_id	l'ID de l'&eacute;l&eacute;ment &agrave r&eacute;cup&eacute;rer
	 * @return	<code>IElement</code> : l'&eacute;l&eacute;ment du circuit à la ligne <code>p_ligne</code> et colonne <code>p_colonne</code>
	 */
	public IElement getElement(String p_id) {
		return this.m_elements.get(p_id);
	}
	
	/**
	 * @return	le chemin du circuit d&eacute;fini par la liste de points renvoy&eacute;s
	 */
	public List<Point> getChemin() {
		return this.m_chemin;
	}
	
	/**
	 * @return	l'&eacute;l&eacute;ment de d&eacute;part du circuit
	 */
	public IElement getElementDepart() {
		return (this.m_idElementDepart == null) ? 
				null : this.m_elements.get(this.m_idElementDepart);
	}
	
	/**
	 * Ajoute un &eacute;l&eacute;ment au cricuit.<br />
	 * 
	 * @param p_element	l'&eacute;l&eacute;ment &agrave; ajouter au circuit
	 */
	public void addElement(IElement p_element) {
		if(p_element instanceof IRoute) {
			if(((IRoute)p_element).estDepart()) {
				if(this.m_idElementDepart != null && !this.m_idElementDepart.equalsIgnoreCase(p_element.getId())) {
					return;
				}
				this.m_idElementDepart = p_element.getId();
			}
			else if(p_element.getId().equalsIgnoreCase(this.m_idElementDepart)) {
				this.m_idElementDepart = null;
			}
		}
		else if(p_element.getId().equalsIgnoreCase(this.m_idElementDepart)) {
			this.m_idElementDepart = null;
		}
		
		this.m_elements.put(p_element.getId(), p_element);
		
		List<String> ids = new LinkedList<String>(this.m_elements.keySet());
		Collections.sort(ids,new Comparator<String>() {
			@Override
			public int compare(String id1, String id2) {
				int x1 = Integer.parseInt(id1.split("_")[0]);
				int y1 = Integer.parseInt(id1.split("_")[1]);
				int x2 = Integer.parseInt(id2.split("_")[0]);
				int y2 = Integer.parseInt(id2.split("_")[1]);
				
				if(x1 != x2)
					return (x1 > x2) ? 1 : -1;
				if(y1 != y2)
					return (y1 > y2) ? 1 : -1;
				
				return 0;
			}
		});
		
		LinkedHashMap<String, IElement> newElements = new LinkedHashMap<String, IElement>(this.m_elements);
		this.m_elements.clear();
		for(String id : ids) {
			this.m_elements.put(id, newElements.get(id));
		}
		System.out.println(this.m_elements.keySet().toString());
	}
	
	private void construireChemin() {
		this.m_chemin = new LinkedList<Point>();
		
		IRoute elem = (IRoute)this.getElementDepart();
		this.m_chemin.add(Utils.getPointCentre(elem));
		
		Point pointElem = Utils.getPointOuest(elem);
		IRoute elemSuivant;
		while((elemSuivant = testConnexion(elem, pointElem)) != null) {
			elem = elemSuivant;
			if(elem.getId().equals(this.m_idElementDepart)) {
				break;
			}
			
			this.m_chemin.add(Utils.getPointCentre(elem));
			if(pointElem.equals(Utils.getPointEst(elem))) {
				if(elem.aSud()) {
					pointElem = Utils.getPointSud(elem);
				}
				else if(elem.aOuest()) {
					pointElem = Utils.getPointOuest(elem);
				}
				else {
					pointElem = Utils.getPointNord(elem);
				}
			}
			else if(pointElem.equals(Utils.getPointSud(elem))) {
				if(elem.aEst()) {
					pointElem = Utils.getPointEst(elem);
				}
				else if(elem.aOuest()) {
					pointElem = Utils.getPointOuest(elem);
				}
				else {
					pointElem = Utils.getPointNord(elem);
				}
			}
			else if(pointElem.equals(Utils.getPointOuest(elem))) {
				if(elem.aSud()) {
					pointElem = Utils.getPointSud(elem);
				}
				else if(elem.aEst()) {
					pointElem = Utils.getPointEst(elem);
				}
				else {
					pointElem = Utils.getPointNord(elem);
				}
			}
			else if(pointElem.equals(Utils.getPointNord(elem))) {
				if(elem.aSud()) {
					pointElem = Utils.getPointSud(elem);
				}
				else if(elem.aOuest()) {
					pointElem = Utils.getPointOuest(elem);
				}
				else {
					pointElem = Utils.getPointEst(elem);
				}
			}
		}
	}
	
	/**
	 * Nettoie la liste des &eacute;l&eacute;ments du cricuit.
	 */
	public void clearElements() {
		this.m_elements.clear();
		this.m_chemin.clear();
	}
	
	/**
	 * @return	<code>true</code> si le circuit est valide (ferm&eacute; avec un d&eacute;part), <code>false</code> sinon
	 */
	public boolean estValide() {
		if(this.m_idElementDepart == null) {
			return false;
		}
		
		Point ouest,nord,est,sud;
		for(IElement elem : this) {
			if(elem instanceof IRoute) {
				ouest = Utils.getPointOuest(elem);
				if(ouest != null) {
					if(this.testConnexion(elem,ouest) == null)
						return false;
				}
				nord = Utils.getPointNord(elem);
				if(nord != null) {
					if(this.testConnexion(elem,nord) == null)
						return false;
				}
				est = Utils.getPointEst(elem);
				if(est != null) {
					if(this.testConnexion(elem,est) == null)
						return false;
				}
				sud = Utils.getPointSud(elem);
				if(sud != null) {
					if(this.testConnexion(elem,sud) == null)
						return false;
				}
			}
		}
		
		this.construireChemin();
		
		return true;
	}
	
	/**
	 * @param p_elem	l'&eacute;l&eacute;ment devant &ecirc;tre connect&eacute;
	 * @param p_point	le point devant &eacute;tablir la connexion de l'&eacute;l&eacute;ment 
	 * 					<code>p_elem</code> avec un des autres &eacute;l&eacute;ments
	 * @return	<code>IRoute</code> : l'&eacute;l&eacute;ment connect&eacute; avec <code>p_elem</code> 
	 * 			par le point <code>p_point</code>
	 */
	private IRoute testConnexion(IElement p_elem, Point p_point) {
		Iterator<IElement> itElements = this.iterator();
		Point[] pointsElem;
		IElement elem2;
		while(itElements.hasNext()) {
			elem2 = itElements.next();
			
			if((elem2 instanceof IRoute) && (elem2 != p_elem)) {
				pointsElem = new Point[]{Utils.getPointOuest(elem2),Utils.getPointNord(elem2),Utils.getPointEst(elem2),Utils.getPointSud(elem2)};
				
				if(this.estConnecte(p_point, pointsElem)) {
					return (IRoute)elem2;
				}
			}
		}
		return null;
	}
	
	/**
	 * @param p_point
	 * @param p_points
	 * @return	<code>true</code> si le point <code>p_point</code> a une connexion avec un des points pr&eacute;sents dans <code>p_points</code>
	 */
	private boolean estConnecte(Point p_point, Point[] p_points) {
		for(Point point : p_points) {
			if(p_point.equals(point)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Iterator<IElement> iterator() {
		return this.m_elements.values().iterator();
	}
}