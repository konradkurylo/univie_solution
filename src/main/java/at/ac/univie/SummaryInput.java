package at.ac.univie;

public record SummaryInput(
        String userId,
        Long activityId,
        String activityName,
        Long durationInSeconds,
        Long startTimeInSeconds,
        Long startTimeOffsetInSeconds,
        String activityType,
        Long averageHeartRateInBeatsPerMinute,
        Long activeKilocalories,
        String deviceName,
        Long maxHeartRateInBeatsPerMinute
) {
}
