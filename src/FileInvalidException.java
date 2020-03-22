
public class FileInvalidException extends Exception{
	
	public FileInvalidException() {
		super("Problem detected with input file: ");
	}
	
	public FileInvalidException(String message) {
		super(message);
	}

}
