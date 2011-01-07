package fr.unice.miage.vroomaniacs_plugins.objetsAnimes;

import fr.unice.miage.vroomaniacs_plugins.pluginsSDK.IVroomaniacs;

public class Formule1 extends ObjetAnime {
	public Formule1(IVroomaniacs p_jeu, String p_nom, double p_x, double p_y) {
		super(p_jeu, "imagesObjetsAnimes/formule1.png", p_nom, p_x, p_y);
	}

	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un objet animés de type Formule 1.";
	}

	@Override
	public String getName() {
		return "Formule 1";
	}

	@Override
	public Class getType() {
		return Formule1.class;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public boolean matches(Class arg0, String arg1, Object arg2) {
		return true;
	}
}
