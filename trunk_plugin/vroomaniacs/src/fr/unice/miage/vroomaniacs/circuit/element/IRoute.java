package fr.unice.miage.vroomaniacs.circuit.element;

public interface IRoute extends IElement {
	/**
	 * @return	<code>true</code> si la route est un d&eacute;part, <code>false</code> sinon
	 */
	public boolean estDepart();
	/**
	 * @return	<code>true</code> si la route passe par le nord, <code>false</code> sinon
	 */
	public boolean aNord();
	/**
	 * @return	<code>true</code> si la route passe par le sud, <code>false</code> sinon
	 */
	public boolean aSud();
	/**
	 * @return	<code>true</code> si la route passe par l'est, <code>false</code> sinon
	 */
	public boolean aEst();

	/**
	 * @return	<code>true</code> si la route passe par l'ouest, <code>false</code> sinon
	 */
	public boolean aOuest();
}