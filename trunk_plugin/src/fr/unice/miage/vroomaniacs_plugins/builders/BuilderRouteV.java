package fr.unice.miage.vroomaniacs_plugins.builders;


import fr.unice.miage.vroomaniacs_plugins.builders.element.RouteV;
import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IEditeurCircuit;

/**
 * @author Julien Lespagnard
 * @version	1.0
 */
public class BuilderRouteV extends Builder {
	
	/**
	 * Constructeur.
	 */
	public BuilderRouteV() {
		super("imagesElements/route-verticale.png");
	}

	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un élément de type route verticale";
	}

	@Override
	public String getName() {
		return "Route verticale";
	}

	@Override
	public Class getType() {
		return BuilderRouteV.class;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void creerElement(IEditeurCircuit p_editeurCircuit,
			String p_idElement) {
		this.m_element = new RouteV(p_editeurCircuit,this,p_idElement);
	}
}