package fr.unice.miage.vroomaniacs.persistance;

import java.util.ArrayList;

public class CareTaker
{
	/** Memento List */
	private ArrayList<Memento> lstMemento;
	/** CareTaker Singleton */
	private static final CareTaker gardien = new CareTaker();
	/** CareTaker Constructor */
	private CareTaker()
	{
		lstMemento = new ArrayList<Memento>();
	}
	/** Add p_memento to lstMemento
	 * @param p_memento memento a ajouter */
	public void addMemento(Memento p_memento)
	{
		getInstance().lstMemento.add(p_memento);
	}
	/** CareTaker instance getter 
	 * @return l'instance unique du CareTaker*/
	public static CareTaker getInstance()
	{
		return gardien;
	}
}
