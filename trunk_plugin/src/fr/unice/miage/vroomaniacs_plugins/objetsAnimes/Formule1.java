package fr.unice.miage.vroomaniacs_plugins.objetsAnimes;

import java.io.Serializable;

public class Formule1 extends ObjetAnime implements Serializable {
	public Formule1() {
		super("/imagesObjetsAnimes/formule1.png",1.5);
	}

	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un objet animé de type Formule 1.";
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
