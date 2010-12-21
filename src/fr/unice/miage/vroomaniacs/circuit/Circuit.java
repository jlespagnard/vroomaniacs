package fr.unice.miage.vroomaniacs.circuit;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.unice.miage.vroomaniacs.circuit.element.Element;
import fr.unice.miage.vroomaniacs.circuit.element.RouteDepart;

/**
 * @author Julien Lespagnard
 * @version 1.0
 */
public class Circuit implements Iterable<Element> {
	/** Liste des &eacute;l&eacute;ments composant le circuit.<br />
	 *  Les &eacute;l&eacute;ments sont rang&eacute;s par ligne puis par colonne.
	 */
	private Map<String, Element> m_elements;
	/** ID de l'&eacute;l&eacute;ment de d&eacute;part du cricuit. */
	private String m_idElementDepart = null;
	
	/**
	 * Constructeur.
	 */
	private Circuit() {
		this.m_elements = new LinkedHashMap<String, Element>();
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
	 * @return	<code>Element</code> : l'&eacute;l&eacute;ment du circuit à la ligne <code>p_ligne</code> et colonne <code>p_colonne</code>
	 */
	public Element getElement(String p_id) {
		return (this.m_elements == null) ? null : this.m_elements.get(p_id);
	}
	
	/**
	 * @return	l'&eacute;l&eacute;ment de d&eacute;part du circuit
	 */
	public Element getElementDepart() {
		return (this.m_idElementDepart == null) ? 
				null : this.m_elements.get(this.m_idElementDepart);
	}
	
	/**
	 * Ajoute un &eacute;l&eacute;ment au cricuit.<br />
	 * 
	 * @param p_element	l'&eacute;l&eacute;ment &agrave; ajouter au circuit
	 */
	public void addElement(Element p_element) {
		if(p_element instanceof RouteDepart) {
			if(this.m_idElementDepart != null && !this.m_idElementDepart.equalsIgnoreCase(p_element.getId())) {
				return;
			}
			this.m_idElementDepart = p_element.getId();
		}
		else if(p_element.getId().equalsIgnoreCase(this.m_idElementDepart)) {
			this.m_idElementDepart = null;
		}
		
		this.m_elements.put(p_element.getId(), p_element);
		
		List<String> ids = new LinkedList<String>(this.m_elements.keySet());
		Collections.sort(ids);
		
		LinkedHashMap<String, Element> newElements = new LinkedHashMap<String, Element>(this.m_elements);
		this.m_elements.clear();
		for(String id : ids) {
			this.m_elements.put(id, newElements.get(id));
		}
	}
	
	@Override
	public Iterator<Element> iterator() {
		return this.m_elements.values().iterator();
	}
}