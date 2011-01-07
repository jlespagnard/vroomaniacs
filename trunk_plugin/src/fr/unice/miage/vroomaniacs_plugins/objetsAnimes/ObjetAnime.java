package fr.unice.miage.vroomaniacs_plugins.objetsAnimes;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ComportementPlugin;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IVroomaniacs;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.ObjetAnimePlugin;

public abstract class ObjetAnime implements Dessinable, Deplacable, ObjetAnimePlugin {
    private double m_xPos, m_yPos;
    private Rectangle m_boiteAnglobante;
    private double m_direction = 2 * Math.PI * Math.random();
    private double m_vitesse = 5 * Math.random() + 3;
    private double m_acceleration = 0;
    private double m_accelerationAngulaire = 0;
    private ArrayList<ComportementPlugin> m_listeDesComportements = new ArrayList<ComportementPlugin>();
    private IVroomaniacs m_leJeu;
    private ImageIcon m_image;

    public ObjetAnime(String p_urlImage) {
    	this.m_image = new ImageIcon(this.getClass().getResource(p_urlImage));
    	this.m_boiteAnglobante = new Rectangle((int)this.m_xPos, (int)this.m_yPos, this.m_image.getIconWidth(), this.m_image.getIconHeight());
	}
    
    @Override
    public void normaliseDirection() {
        if (this.m_direction < 0) {
        	this.m_direction += 2 * Math.PI;
        } else if (this.m_direction > 2 * Math.PI) {
        	this.m_direction -= 2 * Math.PI;
        }
    }
    
    @Override
    public double getXPos() {
    	return this.m_xPos;
    }
    
    @Override
	public void setXPos(double p_xPos) {
    	this.m_xPos = p_xPos;
    }
    
    @Override
	public double getYPos() {
    	return this.m_yPos;
    }
    
    @Override
	public void setYPos(double p_yPos) {
    	this.m_yPos = p_yPos;
    }
    
    @Override
	public int getLargeur() {
    	return this.m_image.getIconWidth();
    }
    
    @Override
	public int getHauteur() {
    	return this.m_image.getIconHeight();
	}
    
    @Override
	public double getDirection() {
    	return this.m_direction;
    }
    
    @Override
	public void setDirection(double p_direction) {
    	this.m_direction = p_direction;
    }
    
    @Override
	public double getVitesse() {
    	return this.m_vitesse;
    }
    
    @Override
    public void setVitesse(double p_vitesse) {
    	this.m_vitesse = p_vitesse;
    }
    
    @Override
	public double getAcceleration() {
    	return this.m_acceleration;
    }
    
    @Override
    public void setAcceleration(double p_acceleration) {
    	this.m_acceleration = p_acceleration;
    }
    
    @Override
	public double getAccelerationAngulaire() {
    	return this.m_accelerationAngulaire;
    }
    
    @Override
	public IVroomaniacs getJeu() {
    	return this.m_leJeu;
    }
    
    @Override
	public void setJeu(IVroomaniacs p_jeu) {
    	this.m_leJeu = p_jeu;
    }

    @Override
    public void ajouterComportement(ComportementPlugin p_comportement) {
    	this.m_listeDesComportements.add(p_comportement);
    }

    @Override
    public void supprimerComportement(ComportementPlugin p_comportement) {
    	 this.m_listeDesComportements.remove(p_comportement);
    }

    public void deplaceToi() {
        for (ComportementPlugin c : this.m_listeDesComportements) {
            c.deplace(this);
        }
    }
    
    public void dessineToi(Graphics g) {
        g.drawImage(this.m_image.getImage(), (int)this.m_xPos, (int)this.m_yPos, 
        		(int)(this.m_xPos+100*Math.cos(this.m_direction)), 
        		(int)(this.m_yPos+100*Math.sin(this.m_direction)), 
        		(int)this.m_boiteAnglobante.getX(), 
        		(int)this.m_boiteAnglobante.getY(), 
        		(int)(this.m_boiteAnglobante.getX()+100*Math.cos(this.m_direction)), 
        		(int)(this.m_boiteAnglobante.getY()+100*Math.sin(this.m_direction)),
        		null);
    }
}