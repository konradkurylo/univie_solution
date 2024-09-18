package at.ac.univie;


/**
 * unified exception thrown from the library when any error with loading data occurred
 */
public class LibraryIssueWithLoadingDataException extends RuntimeException {

    LibraryIssueWithLoadingDataException(String message, Throwable cause){
        super(message, cause);
    }
}
