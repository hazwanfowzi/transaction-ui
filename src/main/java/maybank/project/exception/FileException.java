package maybank.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FileException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

    public FileException() {
        super("The requested resource was not found");
    }
	
    public FileException(String message) {
        super(message);
    }
	
	public FileException(String message, Throwable cause) {
        super(message, cause);
    }

}
