package exception;

public class NoSupportFileException extends Exception {

	public NoSupportFileException() {
		
	}

	public NoSupportFileException(String message) {
		super(message);
		
	}

	public NoSupportFileException(Throwable cause) {
		super(cause);
		
	}

	public NoSupportFileException(String message, Throwable cause) {
		super(message, cause);
	}

}
