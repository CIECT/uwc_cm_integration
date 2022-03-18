package coza.opencollab.sakai.cm;

public class SISException extends RuntimeException {
	private static final long serialVersionUID = -8732193703853644400L;

	public SISException() {
		super();
	}

	public SISException(String message) {
		super(message);
	}

	public SISException(String message, Throwable cause) {
		super(message, cause);
	}

	public SISException(Throwable cause) {
		super(cause);
	}
}
