package at.ac.univie;

/**
 * same as {@link at.ac.univie.SamplesDataInput} but with index
 * not public since we need it only for library implementation, not a public contract
 */
record IndexedSamplesDataInput(
        Long index,
        Long recordingRate,
        String sampleType,
        String data
) {
    static IndexedSamplesDataInput from(SamplesDataInput samplesDataInput, Long index){
        return new IndexedSamplesDataInput(index, samplesDataInput.recordingRate(), samplesDataInput.sampleType(), samplesDataInput.data());
    }
}
