// Exception de la note d'un objectif
package application;

@SuppressWarnings("serial")
public class noteObjInacceptableException extends Exception {

	public noteObjInacceptableException(String message) {
		super(message);
	}
}