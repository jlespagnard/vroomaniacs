package fr.unice.miage.vroomaniacs_plugins.objetsAnimes;

public class Cobra extends ObjetAnime {
	public Cobra() {
		super("/imagesObjetsAnimes/cobra.png",1);
	}

	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un objet animé de type cobra.";
	}

	@Override
	public String getName() {
		return "Cobra";
	}

	@Override
	public Class getType() {
		return Cobra.class;
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

