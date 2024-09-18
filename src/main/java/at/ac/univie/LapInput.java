package at.ac.univie;

public record LapInput(
        Long startTimeInSeconds,
        Long airTemperatureCelsius,
        Long heartRate,
        Long totalDistanceInMeters,
        Long timerDurationInSeconds
) { }
