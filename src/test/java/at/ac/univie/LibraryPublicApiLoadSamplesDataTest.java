package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

class LibraryPublicApiLoadSamplesDataTest {

    @Test
    void testLoadDataSamplesHappyPath() {
        // given
        Path source = Path.of("src/test/resources/samples-data.json");
        // when
        List<SamplesDataInput> result = LibraryPublicApi.loadSamplesData(source);
        // then
        Assertions.assertThat(result).isEqualTo(Fixture.sampleDataInputResult());
    }

    @Test
    void testLoadDataSamplesSadPathNonExistingJson() {
        // given
        Path source = Path.of("/non-existing.json");
        // when
        LibraryIssueWithLoadingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithLoadingDataException.class, () -> LibraryPublicApi.loadSamplesData(source));
        // then
        Assertions.assertThat(exception.getMessage()).isEqualTo("Issue while loading samples data from source: /non-existing.json");
    }

    @Test
    void testLoadDataSamplesSadPathWrongJson() {
        // given
        Path source = Path.of("src/test/resources/summary.json");
        // when
        LibraryIssueWithLoadingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithLoadingDataException.class, () -> LibraryPublicApi.loadSamplesData(source));
        // then
        Assertions.assertThat(exception.getMessage()).isEqualTo("Issue while loading samples data from source: src/test/resources/summary.json");
    }
}
