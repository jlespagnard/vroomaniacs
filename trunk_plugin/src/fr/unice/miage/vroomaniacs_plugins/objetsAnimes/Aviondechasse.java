package fr.unice.miage.vroomaniacs_plugins.objetsAnimes;

public class Aviondechasse extends ObjetAnime {
	public Audi() {
		super("imagesObjetsAnimes/aviondechasse.png",1.8);
	}

	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un objet animés de type avion de chasse.";
	}

	@Override
	public String getName() {
		return "Avion de chasse";
	}

	@Override
	public Class getType() {
		return Aviondechasse.class;
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

