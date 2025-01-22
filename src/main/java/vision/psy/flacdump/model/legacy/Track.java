// ehemaliger Record, der durch JPA / Hibernate ersetzt wurde

/*package vision.psy.flacdump.model.legacy;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

// Record für die Datenbank-Entität Track
// *** POJO zu Lernzwecken ***
// wird durch JPA / Hibernate ersetzt

public record Track(
        @Positive
        Integer id,
        @NotEmpty
        String artist,
        String label,
        @NotEmpty
        String title,
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
    }
}*/