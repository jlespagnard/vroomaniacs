package fr.unice.miage.vroomaniacs_plugins.pluginsSDK;

import java.io.Serializable;

import fr.unice.plugin.Plugin;

/**
 *
 * @author Anthony
 */
public interface ComportementPlugin extends Plugin, Serializable {
    public void deplace(ObjetAnimePlugin o);
}
