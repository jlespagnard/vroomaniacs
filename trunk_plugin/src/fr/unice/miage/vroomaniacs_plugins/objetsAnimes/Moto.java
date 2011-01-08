package fr.unice.miage.vroomaniacs_plugins.objetsAnimes;

public class Moto extends ObjetAnime {
	public Moto() {
		super("imagesObjetsAnimes/moto.png",1.2);
	}

	@Override
	public boolean canProcess(Object arg0) {
		return true;
	}

	@Override
	public String getDescription() {
		return "Un objet animé de type moto.";
	}

	@Override
	public String getName() {
		return "Moto";
	}

	@Override
	public Class getType() {
		return Moto.class;
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

