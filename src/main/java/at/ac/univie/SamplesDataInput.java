package at.ac.univie;

import com.fasterxml.jackson.annotation.JsonAlias;

public record SamplesDataInput(
        @JsonAlias("recording-rate") Long recordingRate,
        @JsonAlias("sample-type") String sampleType,
        String data
) { }
