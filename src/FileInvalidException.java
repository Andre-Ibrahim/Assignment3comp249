
public class FileInvalidException extends Exception{
	
	public FileInvalidException() {
		super("This file is not valid");
	}
	
	public FileInvalidException(String message) {
		super(message);
	}

}
