package fr.unice.miage.vroomaniacs_plugins.builders;

import fr.unice.miage.vroomaniacs_plugins.builders.element.RouteH;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderRouteH extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderRouteH() {
		super("/imagesElements/route-horizontale.png");
	}
	
	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un élément de type route horizontale";
	}

	@Override
	public String getName() {
		return "Route horizontale";
	}

	@Override
	public Class getType() {
		return BuilderRouteH.class;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void creerElement(IEditeurCircuit p_editeurCircuit,
			String p_idElement) {
		this.m_element = new RouteH(p_editeurCircuit,this,p_idElement);
	}
}