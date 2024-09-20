package at.ac.univie;

import java.nio.file.Path;
import java.util.List;

/**
 * library entry point, a bunch of static stateless methods
 * since it is only an entry point to library, there is no implementation here,
 * only facade/delegation pattern
 */
public class LibraryPublicApi {

    /**
     * why even need to initialize it, just a bunch of static library methods
     */
    private LibraryPublicApi(){}

    /**
     * A method to load summary, for now only support loading jsons,
     * @param source which is a path to json file
     * @return loaded Summary representation
     * which is needed for {@link at.ac.univie.LibraryPublicApi#process(SummaryInput, List, List)}
     */
    public static SummaryInput loadSummary(Path source) {
        return Loader.loadSummary(source);
    }

    /**
     * A method to load laps, for now only support loading jsons,
     * @param source which is a path to json file
     * @return loaded List of LapInput representation
     * which is needed for {@link at.ac.univie.LibraryPublicApi#process(SummaryInput, List, List)}
     */
    public static List<LapInput> loadLaps(Path source) {
        return Loader.loadLaps(source);
    }

    /**
     * A method to load samples data, for now only support loading jsons,
     * @param source which is a path to json file
     * @return loaded List of SamplesDataInput representation
     * which is needed for {@link at.ac.univie.LibraryPublicApi#process(SummaryInput, List, List)}
     */
    public static List<SamplesDataInput> loadSamplesData(Path source) {
        return Loader.loadSamplesData(source);
    }

    /**
     * I had a mixed feeling between creating process method that takes inputs from previous supplier methods
     * and creating a stateful library, since I treat stateful as greater evil, first options has been chosen
     *
     * @param summaryInput taken from output of {@link at.ac.univie.LibraryPublicApi#loadSamplesData(Path)}
     * @param lapsInput taken from output of {@link at.ac.univie.LibraryPublicApi#loadSamplesData(Path)}
     * @param samplesDataInputs taken from output of {@link at.ac.univie.LibraryPublicApi#loadSamplesData(Path)}
     *
     * @return result described in software-engineer-task.md
     */

    public static Result process(SummaryInput summaryInput, List<LapInput> lapsInput, List<SamplesDataInput> samplesDataInputs) {
        return Processor.process(summaryInput, lapsInput, samplesDataInputs);
    }

    /**
     * method to store result as json in desired directory
     * @param result result of {@link at.ac.univie.LibraryPublicApi#process(SummaryInput, List, List)} method
     * @param targetPath that contain target directory and target file name
     */
    public static void writeResultToJson(Result result, Path targetPath){
        Writer.writeResultToJson(result, targetPath);
    }
}
