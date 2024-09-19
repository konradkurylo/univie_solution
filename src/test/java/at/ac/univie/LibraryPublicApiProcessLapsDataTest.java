package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * I assume that once we load data with success (without exception)
 * we need to process it no matter how much trash is inside without throwing any library exceptions like
 * "user didn't record one single heartbeat, so library cannot process it"
 * therefore ill be testing only happy paths, at least for now
 */
public class LibraryPublicApiProcessLapsDataTest {

    /**
     * i made assumption that laps can be uniquely identified by its startTime
     */
    private static final long FIRST_LAP_IDENTIFIER = 1661158927L;
    private static final long SECOND_LAP_IDENTIFIER = 1661158929L;

    @Test
    void testProcessLapSizeMatches() {
        // given
        SummaryInput summaryInput = Fixture.summaryInputResult();
        List<LapInput> lapInputs = Fixture.lapInputResult();
        List<SamplesDataInput> samplesDataInputList = Fixture.sampleDataInputResult();
        // when
        Result result = LibraryPublicApi.process(summaryInput, lapInputs, samplesDataInputList);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.lapsData()).isNotNull();
        Assertions.assertThat(result.lapsData().size()).isEqualTo(lapInputs.size());
    }

    @Test
    void testProcessLapStartTimesMatches() {
        // given
        SummaryInput summaryInput = Fixture.summaryInputResult();
        List<LapInput> lapInputs = Fixture.lapInputResult();
        List<SamplesDataInput> samplesDataInputList = Fixture.sampleDataInputResult();
        // when
        Result result = LibraryPublicApi.process(summaryInput, lapInputs, samplesDataInputList);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.lapsData()).isNotNull();
        Assertions.assertThat(result.lapsData().stream().map(Result.LapData::startTime).toList())
                .isEqualTo(List.of(FIRST_LAP_IDENTIFIER, SECOND_LAP_IDENTIFIER));
    }

    @Test
    void testProcessLapDistanceMatches() {
        // given
        SummaryInput summaryInput = Fixture.summaryInputResult();
        List<LapInput> lapInputs = Fixture.lapInputResult();
        List<SamplesDataInput> samplesDataInputList = Fixture.sampleDataInputResult();
        // when
        Result result = LibraryPublicApi.process(summaryInput, lapInputs, samplesDataInputList);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.lapsData()).isNotNull();
        Assertions.assertThat(findLap(result, FIRST_LAP_IDENTIFIER).distance()).isEqualTo(expectedFirstLap().distance());
        Assertions.assertThat(findLap(result, SECOND_LAP_IDENTIFIER).distance()).isEqualTo(expectedSecondLap().distance());
    }

    @Test
    void testProcessLapDurationMatches() {
        // given
        SummaryInput summaryInput = Fixture.summaryInputResult();
        List<LapInput> lapInputs = Fixture.lapInputResult();
        List<SamplesDataInput> samplesDataInputList = Fixture.sampleDataInputResult();
        // when
        Result result = LibraryPublicApi.process(summaryInput, lapInputs, samplesDataInputList);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.lapsData()).isNotNull();
        Assertions.assertThat(findLap(result, FIRST_LAP_IDENTIFIER).duration()).isEqualTo(expectedFirstLap().duration());
        Assertions.assertThat(findLap(result, SECOND_LAP_IDENTIFIER).duration()).isEqualTo(expectedSecondLap().duration());
    }

    /**
     * i made assumption that laps can be uniquely identified by its startTime
     * @param result result to extract
     * @param startTime unique identifier
     * @return single lap representation from the list
     * @throws RuntimeException when identifier not found in provided result
     */
    private Result.LapData findLap(Result result, Long startTime) {
        return result.lapsData()
                .stream()
                .filter(e -> e.startTime().equals(startTime))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("just an exception to break test if identifier of lap not found in result"));
    }

    /**
     * data copied from jsons, presented in a way described in software-engineer-task.md
     */
    private Result.LapData expectedFirstLap() {
        return new Result.LapData(FIRST_LAP_IDENTIFIER, 15L, 600L,
                List.of(
                        new Result.HeartRate(1L, 120L),
                        new Result.HeartRate(1L, 126L),
                        new Result.HeartRate(1L, 122L),
                        new Result.HeartRate(1L, 140L),
                        new Result.HeartRate(1L, 142L),
                        new Result.HeartRate(1L, 155L),
                        new Result.HeartRate(1L, 145L),

                        new Result.HeartRate(2L, 141L),
                        new Result.HeartRate(2L, 147L),
                        new Result.HeartRate(2L, 155L),
                        new Result.HeartRate(2L, 160L),
                        new Result.HeartRate(2L, 180L),
                        new Result.HeartRate(2L, 152L),
                        new Result.HeartRate(2L, 120L)
                )
        );
    }

    /**
     * data copied from jsons, presented in a way described in software-engineer-task.md
     */
    private Result.LapData expectedSecondLap() {
        return new Result.LapData(SECOND_LAP_IDENTIFIER, 30L, 900L,
                List.of(
                        new Result.HeartRate(5L, 143L),
                        new Result.HeartRate(5L, 151L),
                        new Result.HeartRate(5L, 164L),
                        new Result.HeartRate(5L, null),
                        new Result.HeartRate(5L, 173L),
                        new Result.HeartRate(5L, 181L),
                        new Result.HeartRate(5L, 180L),

                        new Result.HeartRate(6L, 182L),
                        new Result.HeartRate(6L, 170L),
                        new Result.HeartRate(6L, 188L),
                        new Result.HeartRate(6L, 181L),
                        new Result.HeartRate(6L, 174L),
                        new Result.HeartRate(6L, 172L),
                        new Result.HeartRate(6L, 158L)
                )
        );
    }
}
