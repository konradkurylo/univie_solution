package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LibraryPublicApiTest {

	@Test
	void testLoadSummaryHappyPath() {
		// given
		String source = "src/test/resources/summary.json";
		// when
		SummaryInput result = LibraryPublicApi.loadSummary(source);
		// then
		Assertions.assertThat(result).isEqualTo(summaryInputResult());
	}

	@Test
	void testLoadSummarySadPath() {
		// given
		String source = "/non-existing.json";
		// when
		LibraryIssueWithLoadingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithLoadingDataException.class, () -> LibraryPublicApi.loadSummary(source));
		// then
		Assertions.assertThat(exception.getMessage()).isEqualTo("Issue while loading summary from source: /non-existing.json");
	}

	private SummaryInput summaryInputResult(){
		return new SummaryInput(
				 "1234567890",
				 9480958402L,
				 "Indoor Cycling",
				 3667L,
				 1661158927L,
				 7200L,
				 "INDOOR_CYCLING",
				 150L,
				 561L,
				 "instinct2",
				 190L
		);
	}
}
