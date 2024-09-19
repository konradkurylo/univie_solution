package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static at.ac.univie.Util.readFile;


/**
 * since I have exposed 5 separate methods in public api due to the stateful concerns described in ADR 6
 * there is a need to explain how to use this library, this is why this test has been created
 */
public class IntegrationTest {

    static class LibraryClient {
        /**
         * this is how library should be used
         */
        void run(Path summaryPath, Path lapsPath, Path sampleDataPath, Path targetResultPath){
            SummaryInput summaryInput = LibraryPublicApi.loadSummary(summaryPath);
            List<LapInput> lapInputs = LibraryPublicApi.loadLaps(lapsPath);
            List<SamplesDataInput> samplesDataInputList = LibraryPublicApi.loadSamplesData(sampleDataPath);
            Result result = LibraryPublicApi.process(summaryInput, lapInputs, samplesDataInputList);
            LibraryPublicApi.writeResultToJson(result, targetResultPath);
        }
    }

    @Test
    void integrationTest(){
        // given
        Path summaryPath = Path.of("src/test/resources/summary.json");
        Path lapsPath = Path.of("src/test/resources/laps.json");
        Path sampleDataPath = Path.of("src/test/resources/samples-data.json");
        Path targetResultPath = Path.of(String.format("src/test/resources/result_integration_%s.json", System.currentTimeMillis()));
        LibraryClient libraryClient = new LibraryClient();

        // when
        libraryClient.run(summaryPath, lapsPath, sampleDataPath, targetResultPath);

        // then
        Assertions.assertThat(readFile(targetResultPath)).isEqualTo(Fixture.result());

    }
}
