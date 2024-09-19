package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class LibraryPublicApiWriteResultToJsonTest {

	@Test
	void writeResultToJsonSadPathWriteToNonExistingFolder() {
		// given
		Path path = Path.of(String.format("src/test/resources/non-existing-folder/result_%s.json", System.currentTimeMillis()));
		Result result = Fixture.result();
		// when
		LibraryIssueWithWritingDataException exception = org.junit.jupiter.api.Assertions.assertThrows(LibraryIssueWithWritingDataException.class, () -> LibraryPublicApi.writeResultToJson(result, path));
		// then
		Assertions.assertThat(exception.getMessage()).isEqualTo(String.format("Issue while writing to file: %s", path));
	}
}
