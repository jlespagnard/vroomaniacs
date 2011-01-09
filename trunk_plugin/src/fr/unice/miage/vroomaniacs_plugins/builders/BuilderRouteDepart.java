package fr.unice.miage.vroomaniacs_plugins.builders;

import fr.unice.miage.vroomaniacs_plugins.builders.element.RouteDepart;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderRouteDepart extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderRouteDepart() {
		super("/imagesElements/route-depart.png");
	}
	
	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un élément de type route de départ";
	}

	@Override
	public String getName() {
		return "Départ";
	}

	@Override
	public Class getType() {
		return BuilderRouteDepart.class;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void creerElement(IEditeurCircuit p_editeurCircuit,
			String p_idElement) {
		this.m_element = new RouteDepart(p_editeurCircuit,this,p_idElement);
	}
}