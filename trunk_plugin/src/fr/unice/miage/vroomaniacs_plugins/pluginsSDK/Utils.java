package fr.unice.miage.vroomaniacs_plugins.pluginsSDK;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IElement;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IRoute;

public abstract class Utils {
	
public static final Dimension ELEM_DIM = new Dimension(80,80);
	
	/**
	 * @param p_element
	 * @return	la position de l'&eacute;l&eacute;ment dans la grille
	 */
	public static Point getPosition(IElement p_element) {
		int x = Integer.parseInt(p_element.getId().split("_")[0]);
		int y = Integer.parseInt(p_element.getId().split("_")[1]);
		
		return new Point(x, y);
	}
	
	public static List<Point> multiplierPoints(List<Point> oldList){
		List<Point> newList = new ArrayList<Point>();
		Point point1 = oldList.get(0);
		Point point2, tmp;
		for(int i = 1; i < oldList.size()+1; i++){
			newList.add(point1);
			if(!(i == oldList.size())){
				point2 = oldList.get(i);
				tmp = new Point((int)(point1.getX()+point2.getX())/2, (int)(point1.getY()+point2.getY())/2);
				newList.add(tmp);
				point1 = point2;
			}
		}		
		return newList;
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
	

    public static double deg2rad(double deg) {
        return deg * Math.PI / 180.0;
    }
    
    public static double distance(double x1, double y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * Teste si deux objets sont en collision. Le test fait appel aux bounding boxes
     * @param d1
     * @param d2
     * @return {@link Boolean}
     */
    public static boolean collision(ObjetAnimePlugin d1, ObjetAnimePlugin d2) {
        // PREMIERE METHODE LENTE A CAUSE d'un NEW !
        // Mais intéressante car on utilisait la méthode toute faite
        // intersect() de la classe Rectangle de AWT
        //
        // remarque : cette méthode marche bien mais oblige à un new
        // qui est effectué pour construire le Rectangle dans la methode
        // getBoiteEnglobante() de ObjetAnime.java. Comme le
        // test de collision est effectué très souvent, j'ai préféré faire tout
        // "à la main" !
        // 
        // return d1.getBoiteEnglobante().intersects(d2.getBoiteEnglobante());


        // SECONDE METHODE PLUS RAPIDE : on fait tout à la main ! Et pas de NEW
        return RectsOverlap((int)d1.getXPos(), (int)d1.getYPos(), d1.getLargeur(), d1.getHauteur(),
                (int)d2.getYPos(), (int)d2.getYPos(), d2.getLargeur(), d2.getHauteur());
    }

    /**
     * Renvoie vrai si deux rectangles s'overlappent, c'est à dire s'il y a collision
     */
    public static boolean RectsOverlap(int x0, int y0, int w0, int h0, int x2, int y2, int w2, int h2) {
        if ((x0 > (x2 + w2)) || ((x0 + w0) < x2)) {
            return false;
        }
        if ((y0 > (y2 + h2)) || ((y0 + h0) < y2)) {
            return false;
        }
        return true;
    }
}