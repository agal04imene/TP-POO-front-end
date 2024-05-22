// Exception personnalisée pour quand l'objectif n'existe pas
package application;

@SuppressWarnings("serial")
class ObjectifInexistantException extends Exception {

	public ObjectifInexistantException(String message) {
        super(message);
    }
}