// Exception personnalisée pour quand le trouble n'existe pas
package application;

@SuppressWarnings("serial")
class TroubleInexistantException extends Exception {
	
	public TroubleInexistantException(String message) {
        super(message);
    }
}
