package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static at.ac.univie.Util.doesFileExist;
import static at.ac.univie.Util.readFile;

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
	void writeResultToJsonHappyPathCompareOutput() {
		// given
		Path path = Path.of(String.format("src/test/resources/result_%s.json", System.currentTimeMillis()));
		Result result = Fixture.result();
		// when
		LibraryPublicApi.writeResultToJson(result, path);
		// then
		Assertions.assertThat(readFile(path)).isEqualTo(result);
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



}
