package at.ac.univie;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;

public class Util {

    /**
     * just a json mapper for test utilities
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * just a simply utility method to check if file exist or not
     */
    static boolean doesFileExist(Path path){
        try {
            Files.newInputStream(path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * just a simply utility method to read the file into Result object
     * there is no test for it since production code is doing the same but with other java types
     */
    static Result readFile(Path path){
        try{
            return MAPPER.readValue(Files.newInputStream(path), Result.class);
        } catch (Exception e) {
            throw new RuntimeException("Just a runtime exception to break the test if needed");
        }
    }
}
