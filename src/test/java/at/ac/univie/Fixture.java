package at.ac.univie;

import java.util.List;

/**
 * fixture - container for dummy test data
 */
class Fixture {

    private Fixture() {}

    static SummaryInput summaryInputResult(){
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

    static List<SamplesDataInput> sampleDataInputResult() {
        return List.of(
                new SamplesDataInput(5L, "0", "86,87,88,88,88,90,91"),
                new SamplesDataInput(5L, "2", "120,126,122,140,142,155,145"),
                new SamplesDataInput(5L, "2", "141,147,155,160,180,152,120"),
                new SamplesDataInput(5L, "0", "86,87,88,88,88,90,91"),
                new SamplesDataInput(5L, "1", "143,87,88,88,88,90,91"),
                new SamplesDataInput(5L, "2", "143,151,164,null,173,181,180"),
                new SamplesDataInput(5L, "2", "182,170,188,181,174,172,158"),
                new SamplesDataInput(5L, "3", "143,87,88,88,88,90,91")
        );
    }


    static List<LapInput> lapInputResult(){
        return List.of(
                new LapInput(1661158927L,28L,109L,15L,600L),
                new LapInput(1661158929L,28L,107L,30L,900L)
        );
    }
}
