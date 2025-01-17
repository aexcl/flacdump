package vision.psy.flacdump.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

// Muss komplett überarbeitet werden. Keine Verwendung von Records
// JPA oder Hibernate verwenden

public record Track(
        @Positive
        Integer id,
        @NotEmpty
        String artist,
        String label,
        String title,
        //@Positive
        Integer trackLength,
        Integer releaseYear,
        String releaseType,
        String fileFormat,
        Integer sampleRate,
        Integer bitrate,
        String genre,
        String albumArt,
        String fileLocation
) {
    public Track {
//        if (trackLength < 0) {
//            throw new IllegalArgumentException("Tracklänge darf nicht negativ sein");
//        }
    }
}
