package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

class LibraryPublicApiWriteResultToJsonTest {

	@Test
	void writeResultToJsonHappyPathJustCheckTheFileExists() {
		// given
		Path path = Path.of(String.format("src/test/resources/result_%s.json", System.currentTimeMillis()));
		Result result = Fixture.result();
		// when
		LibraryPublicApi.writeResultToJson(result, path);
		// then
		Assertions.assertThat(doesFileExist(path)).isTrue();
	}

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

	/**
	 * additional test for utilities
	 */
	@Test
	void aTestForUtility(){
		Assertions.assertThat(doesFileExist(Path.of("src/test/resources/summary.json"))).isTrue();
		Assertions.assertThat(doesFileExist(Path.of("src/test/resources/non-existing.json"))).isFalse();
	}

	/**
	 * just a simply utility method to check if file exist or not
	 */
	private boolean doesFileExist(Path path){
		try {
			Files.newInputStream(path);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
