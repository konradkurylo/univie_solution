package at.ac.univie;

import java.util.List;

/**
 * java representation of result object
 *
 * documentation says:
 * The resulting JSON should include the following:
 * Activity Overview
 * Laps Data
 */
public record Result(ActivityOverview activityOverview, List<LapData> lapsData) {

    /**
     * Key details such as userId, type, device, max heart rate, and duration.
     */
    public record ActivityOverview(String userId, String type, String device, Long maxHeartRate, Long duration) { }

    /**
     * For each lap, include start time, distance, duration, and detailed heart rate samples.
     */
    public record LapData(Long startTime, Long distance, Long duration, List<HeartRate> heartRateList) { }

    /**
     * Heart rate samples should be presented as an array of objects containing two keys: sample index and heart rate.
     */
    public record HeartRate(Long sampleIndex, Long heartRate) { }
}
