/*
package vision.psy.flacdump.track;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import vision.psy.flacdump.repository.TrackRepository;

import java.io.IOException;
import java.io.InputStream;

// ==============================================================================================
// ***  Lädt die JSON-Datei mit den Tracks in die Datenbank  ***
// ***  Bei Bedarf anpassen  ***
// ***  Json derzeit nicht erforderlich  ***
// ==============================================================================================


public class FileJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(FileJsonDataLoader.class);
    private final TrackRepository trackRepository;
    private final ObjectMapper objectMapper;

    public FileJsonDataLoader(TrackRepository trackRepository, ObjectMapper objectMapper) {
        this.trackRepository = trackRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (trackRepository.findAll().isEmpty()) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/files.json")) {
                Tracks allTracks = objectMapper.readValue(inputStream, Tracks.class);
                log.info("Reading {} tracks from JSON data and saving to in-memory collection.", allTracks.tracks().size());
                trackRepository.saveAll(allTracks.tracks());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Tracks from JSON data because the collection contains data.");
        }
    }
}
    */



