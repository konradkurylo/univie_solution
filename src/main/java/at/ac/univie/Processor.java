package at.ac.univie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * hidden class that do processing
 */
class Processor {

    /**
     * a logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    /**
     * "2" means heartbeat data set, that is what documentation said
     */
    private static final String HEART_BEAT_DATA_TYPE = "2";

    /**
     * coma is a delimiter for data, this is what documentation said
     */
    private static final String SAMPLES_DATA_DELIMITER = ",";

    private Processor() {}

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
                                                        .filter(Processor::isANumber)
                                                        .map(singleRow -> new Result.HeartRate(data.index(), Long.parseLong(singleRow)))

                                        ).toList()))
                        .toList()
        );
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
}
