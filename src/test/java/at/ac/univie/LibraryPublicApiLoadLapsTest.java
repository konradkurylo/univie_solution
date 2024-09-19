package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

class LibraryPublicApiLoadLapsTest {

	@Test
	void testLoadLapsHappyPath() {
		// given
		Path source = Path.of("src/test/resources/laps.json");
		// when
		List<LapInput> result = LibraryPublicApi.loadLaps(source);
		// then
		Assertions.assertThat(result).isEqualTo(Fixture.lapInputResult());
	}

	@Test
	void testLoadLapsSadPathNonExistingJson() {
		// given
		Path source = Path.of("/non-existing.json");
		// when
		LibraryIssueWithLoadingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithLoadingDataException.class, () -> LibraryPublicApi.loadLaps(source));
		// then
		Assertions.assertThat(exception.getMessage()).isEqualTo("Issue while loading laps from source: /non-existing.json");
	}

	@Test
	void testLoadLapsSadPathWrongJson() {
		// given
		Path source = Path.of("src/test/resources/summary.json");
		// when
		LibraryIssueWithLoadingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithLoadingDataException.class, () -> LibraryPublicApi.loadLaps(source));
		// then
		Assertions.assertThat(exception.getMessage()).isEqualTo("Issue while loading laps from source: src/test/resources/summary.json");
	}
}
