/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.miage.vroomaniacs_plugins.pluginsSDK;

import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

import fr.unice.miage.vroomaniacs_plugins.objetsAnimes.Deplacable;
import fr.unice.miage.vroomaniacs_plugins.objetsAnimes.Dessinable;

/**
 * Classe pruncipale qui définit le JEU lui-meme. Ici on va conserver des variables
 * publiques qui seront accessibles par les objets, etc. Parmis les variables :
 * la taille de l'aire de jeu, la liste des objets à dessiner, des variables de classe
 * indiquant la dernière position de la souris, si elle a son bouton enfoncé, etc
 * @author Chouchou
 */
public class Jeu extends JPanel implements Runnable, MouseMotionListener, MouseListener {

    public static ArrayList<Dessinable> listeObjetsGraphiques = new ArrayList<Dessinable>();
    public static ArrayList<Dessinable> listeObjetsGraphiquesASupprimer = new ArrayList<Dessinable>();
    public static int mouseX;
    public static int mouseY;
    public static boolean mousePressed;
    private Thread t;
    // Temps en millisecondes entre chaque image. On veut ce temps CONSTANT,
    // voir méthode run()
    private final int TEMPS_ENTRE_IMAGES = 20;

    public Jeu() {


        setSize(640, 480);
        // Pour que le JPAnel reçoive les evenements de touches clavier
        setFocusable(true);


        // Le Jeu est un JPanel mais aussi un écouteur de souris et de clavier
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(new EcouteurTouchesAdapter());


        // démarre le thread d'animation
        t = new Thread(this);
        t.start();
    }

    /**
     * Supprime un objet de la liste des objets à dessiner
     * On ne le fait pas directement car dans la méthode paintComponent
     * on passe son temps à itérer sur cette liste. On ne peut pas supprimer
     * un objet pendant qu'on itère mais seulement après avoir fini d'itérer
     * la suppression se fait donc à la fin de paintComponent. L'astuce consiste
     * à gérer une liste des objets à supprimer.
     * @param o
     */
    public static void supprimeObjet(Dessinable o) {
        listeObjetsGraphiquesASupprimer.add(o);
    }

    public static void ajouteObjet(Dessinable o) {
        listeObjetsGraphiques.add(o);
    }

    public void run() {
        long tempsPrecedent, tempsPrisPourImagePrecedente, tempsAttente;

        tempsPrecedent = System.currentTimeMillis();

        while (true) {

            // Rappel : repaint() indique que l'on veut appeler paintComponent
            // de manière asynchrone. Cela provoque en fait l'ajout d'un
            // évenement de redessin dans la file des événements AWT
            repaint();

            tempsPrisPourImagePrecedente = System.currentTimeMillis() - tempsPrecedent;

            // Le cycle dessin/animation peut prendre une durée variable, pour
            // avoir une animation régulière indépendamment de la puissance
            // de la machine, on calcule le temps d'attente en fonction du temps
            // réel passé pour calculer le cycle précédent
            tempsAttente = TEMPS_ENTRE_IMAGES - tempsPrisPourImagePrecedente;

            if (tempsAttente < 0) {
                tempsAttente = 2;
            }
            try {
                Thread.sleep(tempsAttente);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            tempsPrecedent = System.currentTimeMillis();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(640, 680);
    }

    @Override
    public void paintComponent(Graphics g) {
        if ((g != null) && (getWidth() != 0)) {
            // On efface l'écran en appelant super.paintComponent()...
            super.paintComponent(g);

            // On remet à zéro la liste des objets à supprimer
            listeObjetsGraphiquesASupprimer.clear();

            // On dessine tous les objets du jeu
            for (Dessinable o : listeObjetsGraphiques) {
                o.dessineToi(g);

                // Si ils sont déplaçables on les déplace
                if (o instanceof Deplacable) {
                    Deplacable od = (Deplacable) o;
                    od.deplaceToi();
                }
            }

            // Si on a des objets à supprimer on le fait maintenant. Par ex des
            // objets qui sont sortis de l'écran, des objets touchés par des missiles, etc
            listeObjetsGraphiques.removeAll(listeObjetsGraphiquesASupprimer);
            //System.out.println("Jeu a supprimé " + listeObjetsGraphiquesASupprimer.size() + " objets");


        }
    }

    // Ci-dessous les divers écouteurs pour souris et clavier
    public void mouseDragged(MouseEvent e) {
        mousePressed = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        mousePressed = true;

        // Marrant, on fait tirer l'objet qui peut tirer !
        //vaisseau.tire();
    }

    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    private class EcouteurTouchesAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("keyreleased");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("keypressed");
        }
    }
}
