package fr.unice.miage.vroomaniacs_plugins.objetsAnimes;

public class Audi extends ObjetAnime {
	public Audi() {
		super("/imagesObjetsAnimes/A3.png",1);
	}

	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un objet animé de type Audi a3.";
	}

	@Override
	public String getName() {
		return "Audi";
	}

	@Override
	public Class getType() {
		return Audi.class;
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

