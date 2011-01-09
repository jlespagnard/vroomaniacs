package fr.unice.miage.vroomaniacs_plugins.objetsAnimes;

public class Vaisseau extends ObjetAnime {
	public Vaisseau() {
		super("imagesObjetsAnimes/vaisseau.png",3);
	}

	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un objet animé de type vaisseau de l'espace.";
	}

	@Override
	public String getName() {
		return "Vaisseau";
	}

	@Override
	public Class getType() {
		return Vaisseau.class;
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