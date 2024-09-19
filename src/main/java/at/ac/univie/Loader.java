package at.ac.univie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * hidden class that do loading
 */
class Loader {
    /**
     * a logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Loader.class);

    /**
     * we need to be dependent on some json mapping library, I have picked com.fasterxml.jackson as a choice
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * type reference for list of laps
     */
    private static final TypeReference<List<LapInput>> LIST_LAP_TYPE_REFERENCE = new TypeReference<>() {};

    /**
     * type reference for list of samples data
     */
    private static final TypeReference<List<SamplesDataInput>> LIST_SAMPLES_DATA_TYPE_REFERENCE = new TypeReference<>() {};

    private Loader(){}

    public static SummaryInput loadSummary(Path source) {
        try {
            InputStream fileInputStream = getFileInputStream(source);
            SummaryInput summaryInput = MAPPER.readValue(fileInputStream, SummaryInput.class);
            LOGGER.debug("Summary gets loaded from source: {}", source);
            return summaryInput;
        } catch (Exception e){
            LOGGER.error(String.format("Issue while loading summary from source: %s", source), e);
            throw new LibraryIssueWithLoadingDataException(String.format("Issue while loading summary from source: %s", source), e);
        }
    }

    public static List<LapInput> loadLaps(Path source) {
        try {
            InputStream fileInputStream = getFileInputStream(source);
            List<LapInput> lapsInput = MAPPER.readValue(fileInputStream, LIST_LAP_TYPE_REFERENCE);
            LOGGER.debug("Laps gets loaded from source: {}", source);
            return lapsInput;
        } catch (Exception e){
            LOGGER.error(String.format("Issue while loading laps from source: %s", source), e);
            throw new LibraryIssueWithLoadingDataException(String.format("Issue while loading laps from source: %s", source), e);
        }
    }

    public static List<SamplesDataInput> loadSamplesData(Path source) {
        try {
            InputStream fileInputStream = getFileInputStream(source);
            List<SamplesDataInput> lapsInput = MAPPER.readValue(fileInputStream, LIST_SAMPLES_DATA_TYPE_REFERENCE);
            LOGGER.debug("Samples data gets loaded from source: {}", source);
            return lapsInput;
        } catch (Exception e){
            LOGGER.error(String.format("Issue while loading laps from source: %s", source), e);
            throw new LibraryIssueWithLoadingDataException(String.format("Issue while loading samples data from source: %s", source), e);
        }
    }

    /**
     * method to load input stream from file based on path to file
     * @param source path to file
     * @return input stream of the file
     * @throws IOException when problem with creating input stream,
     * for example file not found
     */
    private static InputStream getFileInputStream(Path source) throws IOException {
        return Files.newInputStream(source);
    }
}
