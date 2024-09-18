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
}
