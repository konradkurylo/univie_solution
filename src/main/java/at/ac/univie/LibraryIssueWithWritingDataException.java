package at.ac.univie;


/**
 * unified exception thrown from the library when any error with writing data occurred
 */
public class LibraryIssueWithWritingDataException extends RuntimeException {

    LibraryIssueWithWritingDataException(String message, Throwable cause){
        super(message, cause);
    }
}
