package at.ac.univie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
     * type reference for list of laps
     */
    private static final TypeReference<List<LapInput>> LIST_LAP_TYPE_REFERENCE = new TypeReference<>() {};

    /**
     * type reference for list of samples data
     */
    private static final TypeReference<List<SamplesDataInput>> LIST_SAMPLES_DATA_TYPE_REFERENCE = new TypeReference<>() {};

    /**
     * A method to load summary, for now only support loading jsons,
     * @param source which is a path to json file
     * @return loaded Summary representation
     * which is needed for {@link at.ac.univie.LibraryPublicApi#process(SummaryInput, List, List)}
     */
    public static SummaryInput loadSummary(String source) {
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

    /**
     * A method to load laps, for now only support loading jsons,
     * @param source which is a path to json file
     * @return loaded List of LapInput representation
     * which is needed for {@link at.ac.univie.LibraryPublicApi#process(SummaryInput, List, List)}
     */
    public static List<LapInput> loadLaps(String source) {
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

    /**
     * A method to load samples data, for now only support loading jsons,
     * @param source which is a path to json file
     * @return loaded List of SamplesDataInput representation
     * which is needed for {@link at.ac.univie.LibraryPublicApi#process(SummaryInput, List, List)}
     */
    public static List<SamplesDataInput> loadSamplesData(String source) {
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
        return new Result(
                new Result.ActivityOverview(
                        summaryInput.userId(),
                        summaryInput.activityType(),
                        summaryInput.deviceName(),
                        summaryInput.maxHeartRateInBeatsPerMinute(),
                        summaryInput.durationInSeconds()
                ),
                lapsInput.stream()
                        .map(e -> new Result.LapData(
                                e.startTimeInSeconds(),
                                e.totalDistanceInMeters(),
                                null,
                                List.of()))
                        .toList()
        );
    }

    /**
     * method to load input stream from file based on path to file
     * @param source path to file
     * @return input stream of the file
     * @throws IOException when problem with creating input stream,
     * for example file not found
     */
    private static InputStream getFileInputStream(String source) throws IOException {
        return Files.newInputStream(Paths.get(source));
    }
}
