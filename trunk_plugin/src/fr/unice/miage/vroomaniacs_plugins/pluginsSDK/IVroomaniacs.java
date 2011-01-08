package fr.unice.miage.vroomaniacs_plugins.pluginsSDK;

import java.awt.Point;
import java.io.Serializable;
import java.util.*;


import fr.unice.miage.vroomaniacs_plugins.objetsAnimes.Dessinable;


public interface IVroomaniacs extends Serializable {
	public void ajouteObjet(Dessinable o);
	public List<Dessinable> getListeObjetDessinable();
	public Point getStand();
	public List<Point> getChemin();
}
