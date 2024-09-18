package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LibraryPublicApiLoadSummaryTest {

	@Test
	void testLoadSummaryHappyPath() {
		// given
		String source = "src/test/resources/summary.json";
		// when
		SummaryInput result = LibraryPublicApi.loadSummary(source);
		// then
		Assertions.assertThat(result).isEqualTo(Fixture.summaryInputResult());
	}

	@Test
	void testLoadSummarySadPathNonExistingJson() {
		// given
		String source = "/non-existing.json";
		// when
		LibraryIssueWithLoadingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithLoadingDataException.class, () -> LibraryPublicApi.loadSummary(source));
		// then
		Assertions.assertThat(exception.getMessage()).isEqualTo("Issue while loading summary from source: /non-existing.json");
	}

	@Test
	void testLoadSummarySadPathWrongJson() {
		// given
		String source = "src/test/resources/laps.json";
		// when
		LibraryIssueWithLoadingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithLoadingDataException.class, () -> LibraryPublicApi.loadSummary(source));
		// then
		Assertions.assertThat(exception.getMessage()).isEqualTo("Issue while loading summary from source: src/test/resources/laps.json");
	}
}
