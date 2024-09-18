package at.ac.univie;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * library entry point, a bunch of static stateless methods
 */
public class LibraryPublicApi {

    /**
     * why even need to initialize it, just a bunch of static library methods
     */
    private LibraryPublicApi(){}

    /**
     * we need to be dependent on some json mapping library, I have picked com.fasterxml.jackson as a choice
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * a logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryPublicApi.class);

    /**
     * A method to load summary, for now only support loading jsons,
     * @param source which is a path to json file
     * @return loaded Summary representation
     * which is needed for {@link at.ac.univie.LibraryPublicApi#process(SummaryInput, List, List)}
     */
    public static SummaryInput loadSummary(String source) {
        try {
            Path filePath = Paths.get(source);
            InputStream fileInputStream = Files.newInputStream(filePath);
            SummaryInput summaryInput = MAPPER.readValue(fileInputStream, SummaryInput.class);
            LOGGER.debug("Summary gets loaded from source: {}", source);
            return summaryInput;
        } catch (Exception e){
            LOGGER.error(String.format("Issue while loading summary from source: %s", source), e);
            throw new LibraryIssueWithLoadingDataException(String.format("Issue while loading summary from source: %s", source), e);
        }

    }

    public static List<LapInput> loadLaps(String source) {
        return List.of();
    }

    public static List<SamplesDataInput> loadSamplesData(String source) {
        return List.of();
    }

    /**
     * I had a mixed feeling between creating process method that takes inputs from previous supplier methods
     * and creating a stateful library, since I treat stateful as greater evil, first options has been chosen
     *
     * @param summaryInput taken from output of {@link at.ac.univie.LibraryPublicApi#loadSamplesData(String)}
     * @param lapsInput taken from output of {@link at.ac.univie.LibraryPublicApi#loadSamplesData(String)}
     * @param samplesDataInputs taken from output of {@link at.ac.univie.LibraryPublicApi#loadSamplesData(String)}
     *
     * @return result described in software-engineer-task.md
     */

    public static Result process(SummaryInput summaryInput, List<LapInput> lapsInput, List<SamplesDataInput> samplesDataInputs) {
        return null;
    }
}
