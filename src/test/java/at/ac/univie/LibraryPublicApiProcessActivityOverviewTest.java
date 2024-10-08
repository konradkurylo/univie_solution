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
public class LibraryPublicApiProcessActivityOverviewTest {

    @Test
    void testProcessUserIdMatches() {
        // given
        SummaryInput summaryInput = Fixture.summaryInputResult();
        List<LapInput> lapInputs = Fixture.lapInputResult();
        List<SamplesDataInput> samplesDataInputList = Fixture.sampleDataInputResult();
        String userId = summaryInput.userId();
        // when
        Result result = LibraryPublicApi.process(summaryInput, lapInputs, samplesDataInputList);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.activityOverview()).isNotNull();
        Assertions.assertThat(result.activityOverview().userId()).isEqualTo(userId);
    }

    @Test
    void testProcessTypeMatches() {
        // given
        SummaryInput summaryInput = Fixture.summaryInputResult();
        List<LapInput> lapInputs = Fixture.lapInputResult();
        List<SamplesDataInput> samplesDataInputList = Fixture.sampleDataInputResult();
        String type = summaryInput.activityType();
        // when
        Result result = LibraryPublicApi.process(summaryInput, lapInputs, samplesDataInputList);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.activityOverview()).isNotNull();
        Assertions.assertThat(result.activityOverview().type()).isEqualTo(type);
    }

    @Test
    void testProcessDeviceMatches() {
        // given
        SummaryInput summaryInput = Fixture.summaryInputResult();
        List<LapInput> lapInputs = Fixture.lapInputResult();
        List<SamplesDataInput> samplesDataInputList = Fixture.sampleDataInputResult();
        String deviceName = summaryInput.deviceName();
        // when
        Result result = LibraryPublicApi.process(summaryInput, lapInputs, samplesDataInputList);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.activityOverview()).isNotNull();
        Assertions.assertThat(result.activityOverview().device()).isEqualTo(deviceName);
    }

    @Test
    void testProcessMaxHeartRateMatches() {
        // given
        SummaryInput summaryInput = Fixture.summaryInputResult();
        List<LapInput> lapInputs = Fixture.lapInputResult();
        List<SamplesDataInput> samplesDataInputList = Fixture.sampleDataInputResult();
        Long maxHeartRate = summaryInput.maxHeartRateInBeatsPerMinute();
        // when
        Result result = LibraryPublicApi.process(summaryInput, lapInputs, samplesDataInputList);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.activityOverview()).isNotNull();
        Assertions.assertThat(result.activityOverview().maxHeartRate()).isEqualTo(maxHeartRate);
    }

    @Test
    void testProcessDurationMatches() {
        // given
        SummaryInput summaryInput = Fixture.summaryInputResult();
        List<LapInput> lapInputs = Fixture.lapInputResult();
        List<SamplesDataInput> samplesDataInputList = Fixture.sampleDataInputResult();
        Long duration = summaryInput.durationInSeconds();
        // when
        Result result = LibraryPublicApi.process(summaryInput, lapInputs, samplesDataInputList);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.activityOverview()).isNotNull();
        Assertions.assertThat(result.activityOverview().duration()).isEqualTo(duration);
    }
}
