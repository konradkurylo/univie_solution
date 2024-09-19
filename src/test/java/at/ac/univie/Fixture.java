package at.ac.univie;

import java.util.List;

/**
 * fixture - container for dummy test data
 */
class Fixture {

    private Fixture() {}

    /**
     * i made assumption that laps can be uniquely identified by its startTime
     */
    static final long FIRST_LAP_IDENTIFIER = 1661158927L;
    static final long SECOND_LAP_IDENTIFIER = 1661158929L;

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

    static Result result() {
        SummaryInput summaryInput = summaryInputResult();
        return new Result(new Result.ActivityOverview(summaryInput.userId(),
                summaryInput.activityType(),
                summaryInput.deviceName(),
                summaryInput.maxHeartRateInBeatsPerMinute(),
                summaryInput.durationInSeconds()),
                List.of(expectedFirstLap(), expectedSecondLap())
        );
    }
    /**
     * data copied from jsons, presented in a way described in software-engineer-task.md
     */
    static Result.LapData expectedFirstLap() {
        return new Result.LapData(FIRST_LAP_IDENTIFIER, 15L, 600L,
                List.of(
                        new Result.HeartRate(1L, 120L),
                        new Result.HeartRate(1L, 126L),
                        new Result.HeartRate(1L, 122L),
                        new Result.HeartRate(1L, 140L),
                        new Result.HeartRate(1L, 142L),
                        new Result.HeartRate(1L, 155L),
                        new Result.HeartRate(1L, 145L),

                        new Result.HeartRate(2L, 141L),
                        new Result.HeartRate(2L, 147L),
                        new Result.HeartRate(2L, 155L),
                        new Result.HeartRate(2L, 160L),
                        new Result.HeartRate(2L, 180L),
                        new Result.HeartRate(2L, 152L),
                        new Result.HeartRate(2L, 120L)
                )
        );
    }

    /**
     * data copied from jsons, presented in a way described in software-engineer-task.md
     */
    static Result.LapData expectedSecondLap() {
        return new Result.LapData(SECOND_LAP_IDENTIFIER, 30L, 900L,
                List.of(
                        new Result.HeartRate(5L, 143L),
                        new Result.HeartRate(5L, 151L),
                        new Result.HeartRate(5L, 164L),
                        // new Result.HeartRate(5L, null), // i assumed that we don't need null value so i dropped it
                        new Result.HeartRate(5L, 173L),
                        new Result.HeartRate(5L, 181L),
                        new Result.HeartRate(5L, 180L),

                        new Result.HeartRate(6L, 182L),
                        new Result.HeartRate(6L, 170L),
                        new Result.HeartRate(6L, 188L),
                        new Result.HeartRate(6L, 181L),
                        new Result.HeartRate(6L, 174L),
                        new Result.HeartRate(6L, 172L),
                        new Result.HeartRate(6L, 158L)
                )
        );
    }
}
