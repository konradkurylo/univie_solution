package at.ac.univie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static at.ac.univie.Util.doesFileExist;


public class UtilTest {

    /**
     * additional test for utilities
     */
    @Test
    void aTestForUtility(){
        Assertions.assertThat(doesFileExist(Path.of("src/test/resources/summary.json"))).isTrue();
        Assertions.assertThat(doesFileExist(Path.of("src/test/resources/non-existing.json"))).isFalse();
    }
}
