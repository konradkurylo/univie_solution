package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class LibraryPublicApiLoadSamplesDataTest {

    @Test
    void testLoadDataSamplesHappyPath() {
        // given
        String source = "src/test/resources/samples-data.json";
        // when
        List<SamplesDataInput> result = LibraryPublicApi.loadSamplesData(source);
        // then
        Assertions.assertThat(result).isEqualTo(sampleDataInputResult());
    }

    @Test
    void testLoadDataSamplesSadPathNonExistingJson() {
        // given
        String source = "/non-existing.json";
        // when
        LibraryIssueWithLoadingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithLoadingDataException.class, () -> LibraryPublicApi.loadSamplesData(source));
        // then
        Assertions.assertThat(exception.getMessage()).isEqualTo("Issue while loading samples data from source: /non-existing.json");
    }

    @Test
    void testLoadDataSamplesSadPathWrongJson() {
        // given
        String source = "src/test/resources/summary.json";
        // when
        LibraryIssueWithLoadingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithLoadingDataException.class, () -> LibraryPublicApi.loadSamplesData(source));
        // then
        Assertions.assertThat(exception.getMessage()).isEqualTo("Issue while loading samples data from source: src/test/resources/summary.json");
    }

    private List<SamplesDataInput> sampleDataInputResult() {
        return List.of(
                new SamplesDataInput(5L, "0", "86,87,88,88,88,90,91"),
				new SamplesDataInput(5L, "2", "120,126,122,140,142,155,145"),
				new SamplesDataInput(5L, "2", "141,147,155,160,180,152,120"),
				new SamplesDataInput(5L, "0", "86,87,88,88,88,90,91"),
				new SamplesDataInput(5L, "1", "143,87,88,88,88,90,91"),
				new SamplesDataInput(5L, "2", "143,151,164,null,173,181,180"),
				new SamplesDataInput(5L, "2", "182,170,188,181,174,172,158"),
				new SamplesDataInput(5L, "3", "143,87,88,88,88,90,91")
        );
    }
}
