package fr.unice.miage.vroomaniacs_plugins.pluginsSDK;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.Utils;

abstract public class ObjetAnime implements Dessinable, Deplacable {

    // Chaque objet a une position et une largeur et une hauteur...
    public double xPos, yPos;
    // taille de l'objet
    public int largeur, hauteur;
    // La boite englobante. SOus forme de rectangle car peut servir aux tests
    // de collision avec la méthode intersect() de Rectangle
    Rectangle boiteAnglobante;
    // Une direction de déplacement (un angle), en gros, indique de quel côté "regarde" l'objet
    public double direction = 2 * Math.PI * Math.random();
    // Angle du champs de vision, en radians. Par défait 90 degrés
    public double angleDeVision = Utils.deg2rad(90);
    double demiAngleDeVision = angleDeVision / 2.0;
    // Une vitesse en pixels/seconde
    public double vitesse = 5 * Math.random() + 3;
    // Une accélération (incrément ou décrément de la vitesse) en pixels/ seconde au carré.
    public double acceleration = 0;
    public double accelerationAngulaire = 0;
    // Et une description (un message qui donne la valeur de ses caractèristiques
    public String description;
    public String nom;
    public Color color = Color.BLACK;
    // Une liste de comportements
    public ArrayList<ComportementPlugin> listeDesComportements = new ArrayList<ComportementPlugin>();
    public Jeu leJeu;
    // La mÃ©thode qui porte le nom de la classe s'appelle un constructeur, il sert Ã  crÃ©er un Cercle

    public ObjetAnime(String nom, double x, double y, int largeur, int hauteur, Jeu leJeu) {
        xPos = x;
        yPos = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.nom = nom;
        this.leJeu = leJeu;

    }

    public void deplaceToi() {
        // On demande à chaque déplacement de déplacer l'objet en appliquant
        // tous les comportements dans l'ordre. Par defaut tous les objets ont
        // au moins le comportement qui les fait avancer dans leur direction,
        // avec leur vitesse et les accelérations
        for (ComportementPlugin c : listeDesComportements) {
            c.deplace(this);

        }
    }

    public Rectangle getBoiteEnglobante() {
        return new Rectangle((int) xPos, (int) yPos, largeur, hauteur);
    }

    /**
     * Pour conserver la direction entre 0 et 2*PI
     */
    public void normaliseDirection() {
        if (direction < 0) {
            direction += 2 * Math.PI;
        } else if (direction > 2 * Math.PI) {
            direction -= 2 * Math.PI;
        }
    }

    public void ajouteComportement(ComportementPlugin c) {
        listeDesComportements.add(c);
    }

     public void supprimeComportement(ComportementPlugin c) {
        listeDesComportements.remove(c);
    }

    public void dessineToi(Graphics g) {
        // Par defaut on dessine la bounding box de l'objet, soit un rectangle
        // centré en xPos, yPos, de taille largeur et hauteur
        // On dessine aussi son centre et sa direction, aussi son angle de vision

        // On dessine le centre de l'objet
        dessineCercleCentreSurLobjet(g, 2);

        dessineDirectionCourante(g);

        //text(nom, xPos, yPos);
        dessineChampsDeVision(g);

        dessineBoundingBox(g);
    }

    /**
     * Affiche une ligne droite de 100 pixels dans la direction de l'objet, à partir de son centre
     * @param g
     */
    public void dessineDirectionCourante(Graphics g) {
        g.drawLine((int) xPos, (int) yPos, (int) (xPos + 100 * Math.cos(direction)), (int) (yPos + 100 * Math.sin(direction)));
    }

    /**
     * Dessine un cercle centré en . Par defaut lorsqu'on dessine un cercle en java,
     * xPos et yPos sont le coin en haut à gauche du rectangle englobant le cercle
     * Pour que le cercle soit centré, il faut donc rajouter le rayon avant d'appeler
     * la méthode drawArc de AWT
     * @param g
     * @param x
     * @param y
     * @param rayon
     */
    public void dessineCercleCentreSurLobjet(Graphics g, double rayon) {
        g.drawArc((int) (xPos - rayon), (int) (yPos - rayon), (int) (2 * rayon), (int) (2 * rayon), 0, 360);
    }

    public void dessineBoundingBox(Graphics g) {
        g.drawRect((int) (xPos - largeur / 2.0), (int) (yPos - hauteur / 2.0), largeur, hauteur);
    }

    void dessineChampsDeVision(Graphics g) {
        g.drawLine((int) xPos, (int) yPos, (int) (xPos + 100 * Math.cos(direction - demiAngleDeVision)),
                (int) (yPos + 100 * Math.sin(direction - demiAngleDeVision)));
        g.drawLine((int) xPos, (int) yPos, (int) (xPos + 100 * Math.cos(direction + demiAngleDeVision)),
                (int) (yPos + 100 * Math.sin(direction + demiAngleDeVision)));

    }
}
