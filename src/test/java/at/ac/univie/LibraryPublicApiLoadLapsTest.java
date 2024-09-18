package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class LibraryPublicApiLoadLapsTest {

	@Test
	void testLoadLapsHappyPath() {
		// given
		String source = "src/test/resources/laps.json";
		// when
		List<LapInput> result = LibraryPublicApi.loadLaps(source);
		// then
		Assertions.assertThat(result).isEqualTo(Fixture.lapInputResult());
	}

	@Test
	void testLoadLapsSadPathNonExistingJson() {
		// given
		String source = "/non-existing.json";
		// when
		LibraryIssueWithLoadingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithLoadingDataException.class, () -> LibraryPublicApi.loadLaps(source));
		// then
		Assertions.assertThat(exception.getMessage()).isEqualTo("Issue while loading laps from source: /non-existing.json");
	}

	@Test
	void testLoadLapsSadPathWrongJson() {
		// given
		String source = "src/test/resources/summary.json";
		// when
		LibraryIssueWithLoadingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithLoadingDataException.class, () -> LibraryPublicApi.loadLaps(source));
		// then
		Assertions.assertThat(exception.getMessage()).isEqualTo("Issue while loading laps from source: src/test/resources/summary.json");
	}
}
