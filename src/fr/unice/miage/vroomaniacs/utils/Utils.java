package fr.unice.miage.vroomaniacs.utils;

import java.awt.Dimension;
import java.awt.Point;

import fr.unice.miage.vroomaniacs.circuit.element.IElement;
import fr.unice.miage.vroomaniacs.circuit.element.IRoute;

public abstract class Utils {
	
	public static final Dimension ELEM_DIM = new Dimension(40,40);
	
	/**
	 * @param p_element
	 * @return	la position de l'&eacute;l&eacute;ment dans la grille
	 */
	public static Point getPosition(IElement p_element) {
		int x = Integer.parseInt(p_element.getId().split("_")[0]);
		int y = Integer.parseInt(p_element.getId().split("_")[1]);
		
		return new Point(x, y);
	}

	/**
	 * @param p_element
	 * @return	le point ouest de l'&eacute;l&eacute;ment par rapport &agrave; son emplacement dans la grille
	 */
	public static Point getPointOuest(IElement p_element) {
		if(p_element == null || p_element.getId() == null || p_element.getId().isEmpty())
			return null;

		if(p_element instanceof IRoute) {
			if(((IRoute)p_element).aOuest()) {
				Point position = getPosition(p_element);
				double coorX,coorY;
			
				coorX = position.getY()*Utils.ELEM_DIM.getWidth();
				coorY = (position.getX()*Utils.ELEM_DIM.getHeight())+(Utils.ELEM_DIM.getHeight()/2);
				
				Point p = new Point();
				p.setLocation(coorX, coorY);
				return p;
			}
		}
		return null;
	}
	
	/**
	 * @param p_element
	 * @return	le point nord de l'&eacute;l&eacute;ment par rapport &agrave; son emplacement dans la grille
	 */
	public static Point getPointNord(IElement p_element) {
		if(p_element == null || p_element.getId() == null || p_element.getId().isEmpty())
			return null;
		
		Point position = getPosition(p_element);
		double coorX,coorY;
		
		if(p_element instanceof IRoute) {
			if(((IRoute)p_element).aNord()) {
				coorX = (position.getY()*Utils.ELEM_DIM.getWidth())+(Utils.ELEM_DIM.getWidth()/2);
				coorY = position.getX()*Utils.ELEM_DIM.getHeight();
				
				Point p = new Point();
				p.setLocation(coorX, coorY);
				return p;
			}
		}
		return null;
	}
	
	/**
	 * @param p_element
	 * @return	le point est de l'&eacute;l&eacute;ment par rapport &agrave; son emplacement dans la grille
	 */
	public static Point getPointEst(IElement p_element) {
		if(p_element == null || p_element.getId() == null || p_element.getId().isEmpty())
			return null;
		
		Point position = getPosition(p_element);
		double coorX,coorY;
		
		if(p_element instanceof IRoute) {
			if(((IRoute)p_element).aEst()) {
				coorX = (position.getY()*Utils.ELEM_DIM.getWidth())+Utils.ELEM_DIM.getWidth();
				coorY = (position.getX()*Utils.ELEM_DIM.getHeight())+(Utils.ELEM_DIM.getHeight()/2);
				
				Point p = new Point();
				p.setLocation(coorX, coorY);
				return p;
			}
		}
		return null;
	}
	
	/**
	 * @param p_element
	 * @return	le point sud de l'&eacute;l&eacute;ment par rapport &agrave; son emplacement dans la grille
	 */
	public static Point getPointSud(IElement p_element) {
		if(p_element == null || p_element.getId() == null || p_element.getId().isEmpty())
			return null;
		
		Point position = getPosition(p_element);
		double coorX,coorY;
		
		if(p_element instanceof IRoute) {
			if(((IRoute)p_element).aSud()) {
				coorX = (position.getY()*Utils.ELEM_DIM.getWidth())+(Utils.ELEM_DIM.getWidth()/2);
				coorY = (position.getX()*Utils.ELEM_DIM.getHeight())+Utils.ELEM_DIM.getHeight();
				
				Point p = new Point();
				p.setLocation(coorX, coorY);
				return p;
			}
		}
		return null;
	}
	
	/**
	 * @param p_element
	 * @return	le point central de l'&eacute;l&eacute;ment par rapport &agrave; son emplacement dans la grille
	 */
	public static Point getPointCentre(IElement p_element) {
		if(p_element == null || p_element.getId() == null || p_element.getId().isEmpty())
			return null;
		
		Point position = getPosition(p_element);
		double coorX = (position.getY()*Utils.ELEM_DIM.getWidth())+(Utils.ELEM_DIM.getWidth()/2);
		double coorY = (position.getX()*Utils.ELEM_DIM.getHeight())+(Utils.ELEM_DIM.getHeight()/2);
		
		Point p = new Point();
		p.setLocation(coorX, coorY);
		return p;
	}
	
	/**
	 * @return	la largeur de la route calcul&eacute;e par rapport &agrave; 
	 * 			la taille de l'image des &eacute;l&eacute;ments
	 */
	public static double getLargeurRoute() {
		return (Utils.ELEM_DIM.getHeight()/2);
	}
}