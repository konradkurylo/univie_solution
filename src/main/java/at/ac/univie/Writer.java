package at.ac.univie;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * hidden class that do writing
 */
class Writer {
    /**
     * we need to be dependent on some json mapping library, I have picked com.fasterxml.jackson as a choice
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * a logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Writer.class);

    private Writer() {}

    public static void writeResultToJson(Result result, Path targetPath){
        try{
            String outputString = MAPPER.writeValueAsString(result);
            Files.writeString(targetPath, outputString);
        } catch (Exception e){
            LOGGER.error(String.format("Issue while writing to file: %s", targetPath.toString()), e);
            throw new LibraryIssueWithWritingDataException(String.format("Issue while writing to file: %s", targetPath), e);
        }
    }
}
