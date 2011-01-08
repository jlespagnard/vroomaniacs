package fr.unice.miage.vroomaniacs_plugins.comportements;



import java.awt.Point;
import java.util.List;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IVroomaniacs;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;



public class ComportementArretStand extends ComportementSuitChemin{
	private List<Point> chem;
	private Point stand;
	private int lastPoint = 0;
	private long tempPrec;
	private long tempStand;
	private boolean firstTime = false;
	private boolean active = true;
	public ComportementArretStand(List<Point> che,Point p_stand) {
		super(che);
		chem = che;
		stand = p_stand;
	}
	public ComportementArretStand(IVroomaniacs p_vroomaniacs)
	{
		stand = p_vroomaniacs.getStand();
	}
	public void setActive(boolean bool)
	{
		active = bool;
	}
	public void suitChemin(ObjetAnimePlugin o)
	{
		if(lastPoint < chem.size())
		{
			double dx = chem.get(lastPoint).x - o.getXPos();
            double dy = chem.get(lastPoint).y - o.getYPos();
            o.setDirection(Math.atan2(dy, dx));
            o.normaliseDirection();
            o.setXPos(o.getXPos()+(o.getVitesse()*Math.cos(o.getDirection())));
	        o.setYPos(o.getYPos()+(o.getVitesse()*Math.sin(o.getDirection())));
            
			if(fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils.distance(o.getXPos(),o.getYPos(), chem.get(lastPoint).x, chem.get(lastPoint).y)< 20)
			{
				lastPoint ++;
			}
		}
		else
		{
			lastPoint = 0;
		}
	}
	public void suitStand(ObjetAnimePlugin o)
	{
		double xtg = stand.x  - o.getXPos();
		double ytg = stand.y - o.getYPos();
		o.setDirection(Math.atan2(ytg, xtg));
		o.setXPos(o.getXPos()+(o.getVitesse()*Math.cos(o.getDirection())));
	    o.setYPos(o.getYPos()+(o.getVitesse()*Math.sin(o.getDirection())));
        //o.vitesse = o.vitesse + o.accelerationLineaire;//en direction du stand
	}
	@Override
	public void deplace(ObjetAnimePlugin o)
	{
		if(fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils.distance(o.getXPos(), o.getYPos(), stand.x,stand.y)<30)
		{//si le stand est renseigné, que la distance est sufisante, et que le comportment doit se faire
			if(fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils.distance(o.getXPos(), o.getYPos(), stand.x,stand.y)<10 && active)
			{
				long time = System.currentTimeMillis();
				if(!firstTime)
				{
					tempPrec = time;
				}
				tempStand= time - tempPrec;
				firstTime = true;
				if(tempStand >= 5*1000)
				{
					System.out.println("reprise");
					suitChemin(o);
					active = false;
				}
			}
			else if(active)
			{
				suitStand(o);
			}
			else
			{
				suitChemin(o);
				firstTime = false;
				tempStand=0;
				tempPrec=0;
			}
		}
		else
		{
			suitChemin(o);
			active = true;
		}
	}
}
