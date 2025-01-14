package vision.psy.flacdump.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vision.psy.flacdump.model.Track;
import vision.psy.flacdump.repository.TrackRepository;
import vision.psy.flacdump.track.TrackNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class TrackService {

    private static final Logger log = LoggerFactory.getLogger(TrackService.class);

    private final TrackRepository trackRepository;

    // Pfad zum Ordner, in dem wir Audio-Dateien speichern wollen
    // Das kann auch aus application.properties konfigurierbar gemacht werden
    private final String uploadDir = "/home/alex/Documents/Projekt Flacdump/storage";

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    /**
     * Nimmt ein MultipartFile entgegen (z.B. hochgeladen von einem REST-Endpunkt),
     * speichert es auf dem Dateisystem und legt anschließend einen DB-Eintrag an.
     */
    public Track uploadTrack(MultipartFile file, Track trackMetadata) {
        try {
            // 1) Prüfen, ob uploadDir existiert, sonst erstellen
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 2) Zieldatei bestimmen
            String originalFilename = file.getOriginalFilename();
            // Evtl. unique Name generieren, z.B. UUID + Original-Endung
            String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;

            File destinationFile = new File(dir, uniqueFilename);

            // 3) Datei auf Festplatte kopieren
            file.transferTo(destinationFile);

            // 4) trackMetadata um den Pfad ergänzen
            Track trackToSave = new Track(
                    trackMetadata.id(),
                    trackMetadata.artist(),
                    trackMetadata.label(),
                    trackMetadata.title(),
                    trackMetadata.trackLength(),
                    trackMetadata.releaseYear(),
                    trackMetadata.releaseType(),
                    trackMetadata.fileFormat(),
                    trackMetadata.sampleRate(),
                    trackMetadata.bitrate(),
                    trackMetadata.genre(),
                    trackMetadata.albumArt(),
                    destinationFile.getAbsolutePath() // <-- fileLocation
            );

            // 5) In DB speichern
            trackRepository.create(trackToSave); // bei dir: create(Track)
            log.info("Track in DB gespeichert: {}", trackToSave);

            return trackToSave;

        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Speichern der Datei auf dem Server", e);
        }
    }

    /**
     * Einfaches Beispiel: Hole Track by ID
     */
    public Track getTrackById(Integer id) {
        Optional<Track> optTrack = trackRepository.getById(id);
        if (optTrack.isEmpty()) {
            throw new TrackNotFoundException();
        }
        return optTrack.get();
    }

    // ... und so weiter ...
}
