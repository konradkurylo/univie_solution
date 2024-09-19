package at.ac.univie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * "2" means heartbeat data set, that is what documentation said
     */
    private static final String HEART_BEAT_DATA_TYPE = "2";

    /**
     * coma is a delimiter for data, this is what documentation said
     */
    private static final String SAMPLES_DATA_DELIMITER = ",";

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
        Map<LapInput, List<IndexedSamplesDataInput>> splitSamplesDataIntoLaps = new HashMap<>();
        int processedSamplesDataIndex = 0;
        for (LapInput key: lapsInput) {
            LOGGER.debug("start processing lap with startTime {}, starting from sample data iterator {}", key.startTimeInSeconds(), processedSamplesDataIndex);
            List<IndexedSamplesDataInput> value = new ArrayList<>();

            for (int i = processedSamplesDataIndex; i < samplesDataInputs.size(); i++) {
                IndexedSamplesDataInput samplesDataInput = IndexedSamplesDataInput.from(samplesDataInputs.get(i), (long) i);
                if (value.isEmpty()){
                    LOGGER.debug("value list is empty for iterator {}", i);
                    value.add(samplesDataInput);
                } else if (Integer.parseInt(samplesDataInputs.get(i-1).sampleType()) <= Integer.parseInt(samplesDataInput.sampleType())){
                    LOGGER.debug("condition that previous type was lower / equal fulfilled for iterator {}", i);
                    value.add(samplesDataInput);
                } else {
                    LOGGER.debug("condition that previous type was lower / equal not fulfilled for iterator {}", i);
                    processedSamplesDataIndex = i;
                    break;
                }
            }
            splitSamplesDataIntoLaps.put(key, value);
        }
        return new Result(
                new Result.ActivityOverview(
                        summaryInput.userId(),
                        summaryInput.activityType(),
                        summaryInput.deviceName(),
                        summaryInput.maxHeartRateInBeatsPerMinute(),
                        summaryInput.durationInSeconds()
                ),
                splitSamplesDataIntoLaps.entrySet().stream()
                        .map(entry -> new Result.LapData(
                                entry.getKey().startTimeInSeconds(),
                                entry.getKey().totalDistanceInMeters(),
                                entry.getKey().timerDurationInSeconds(),
                                entry.getValue()
                                        .stream()
                                        .filter(data-> HEART_BEAT_DATA_TYPE.equals(data.sampleType())).flatMap(
                                                data -> Arrays.stream(data.data().split(SAMPLES_DATA_DELIMITER))
                                                        .filter(LibraryPublicApi::isANumber)
                                                        .map(singleRow -> new Result.HeartRate(data.index(), Long.parseLong(singleRow)))

                                        ).toList()))
                        .toList()
        );
    }

    /**
     * method to store result as json in desired directory
     * @param result result of {@link at.ac.univie.LibraryPublicApi#process(SummaryInput, List, List)} method
     * @param targetPath that contain target directory and target file name
     */
    public static void writeResultToJson(Result result, Path targetPath){
        try{
            String outputString = MAPPER.writeValueAsString(result);
            Files.writeString(targetPath, outputString);
        } catch (Exception e){
            LOGGER.error(String.format("Issue while writing to file: %s", targetPath.toString()), e);
            throw new LibraryIssueWithWritingDataException(String.format("Issue while writing to file: %s", targetPath), e);
        }
    }

    /**
     * i assumed that we only want to process numbers, no trash data
     * @param singleRow possible trash data
     * @return true if it is a number, false otherwise
     */
    private static boolean isANumber(String singleRow) {
        try{
            Long.parseLong(singleRow);
            return true;
        } catch (Exception e){
            return false;
        }
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
