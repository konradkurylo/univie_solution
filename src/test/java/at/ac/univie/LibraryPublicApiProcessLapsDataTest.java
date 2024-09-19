package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static at.ac.univie.Fixture.*;

/**
 * I assume that once we load data with success (without exception)
 * we need to process it no matter how much trash is inside without throwing any library exceptions like
 * "user didn't record one single heartbeat, so library cannot process it"
 * therefore ill be testing only happy paths, at least for now
 */
public class LibraryPublicApiProcessLapsDataTest {

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

    @Test
    void testProcessLapsHeartRatesMatch() {
        // given
        SummaryInput summaryInput = Fixture.summaryInputResult();
        List<LapInput> lapInputs = Fixture.lapInputResult();
        List<SamplesDataInput> samplesDataInputList = Fixture.sampleDataInputResult();
        // when
        Result result = LibraryPublicApi.process(summaryInput, lapInputs, samplesDataInputList);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.lapsData()).isNotNull();
        Assertions.assertThat(findLap(result, FIRST_LAP_IDENTIFIER).heartRateList()).isEqualTo(expectedFirstLap().heartRateList());
        Assertions.assertThat(findLap(result, SECOND_LAP_IDENTIFIER).heartRateList()).isEqualTo(expectedSecondLap().heartRateList());
    }

    /**
     * i made assumption that laps can be uniquely identified by its startTime
     *
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
}
