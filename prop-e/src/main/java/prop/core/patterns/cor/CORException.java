package prop.core.patterns.cor;

public class CORException extends Exception {

	private static final long serialVersionUID = -8998200541916115997L;

	public CORException() {
	}

	public CORException(String message) {
		super(message);
	}

	public CORException(Throwable cause) {
		super(cause);
	}

	public CORException(String message, Throwable cause) {
		super(message, cause);
	}

	public CORException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
