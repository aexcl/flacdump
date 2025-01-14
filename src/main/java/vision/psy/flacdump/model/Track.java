package vision.psy.flacdump.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record Track(
        @Positive
        Integer id,
        @NotEmpty
        String artist,
        String label,
        String title,
        @Positive
        Integer trackLength,
        Integer releaseYear,
        ReleaseType releaseType,
        String fileFormat,
        Integer sampleRate,
        Integer bitrate,
        String genre,
        String albumArt,
        String fileLocation
) {
    public Track {
        if (trackLength <= 0) {
            throw new IllegalArgumentException("Tracklänge darf nicht negativ sein");
        }
    }

    // Track Analyse mit JAudioTagger
    // muss überarbeitet werden weil Track als Record deklariert ist
/*
    public void analyze(String filePath) {
        try {
            AudioFile audioFile = AudioFileIO.read(new File(filePath));
            Tag tag = audioFile.getTag();

            if (tag != null) {
                this.artist = tag.getFirst(FieldKey.ARTIST);
                this.title = tag.getFirst(FieldKey.TITLE);
                this.genre = tag.getFirst(FieldKey.GENRE);
                this.albumArt = tag.getFirst(FieldKey.COVER_ART);
            }

            this.trackLength = (int) audioFile.getAudioHeader().getTrackLength();
            this.bitrate = Math.toIntExact(audioFile.getAudioHeader().getBitRateAsNumber());
            this.sampleRate = audioFile.getAudioHeader().getSampleRateAsNumber();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
            throw new RuntimeException("Fehler beim Analysieren der Datei: " + filePath, e);
        }
    }
    */
}
