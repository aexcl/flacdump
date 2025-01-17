package vision.psy.flacdump.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vision.psy.flacdump.model.Track;
import vision.psy.flacdump.repository.TrackRepository;
import vision.psy.flacdump.exceptions.TrackNotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class TrackService {

    private static final Logger log = LoggerFactory.getLogger(TrackService.class);

    private final TrackRepository trackRepository;

    // Später in application.properties auslagern
    // Pfad zum Ordner, in dem die Dateien gespeichert werden sollen
    private final String uploadDir = "/home/alex/Documents/Projekt Flacdump/storage";

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }


//      Nimmt ein MultipartFile entgegen (z.B. hochgeladen von einem REST-Endpunkt),
//      speichert es auf dem Dateisystem und legt anschließend einen DB-Eintrag an.

    public Track uploadTrack(MultipartFile file, Track trackMetadata) {
        try {
            File dir = new File(uploadDir);
            // Zieldatei bestimmen
            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
            File destinationFile = new File(dir, uniqueFilename);

            // Datei auf Festplatte kopieren
            file.transferTo(destinationFile);
            log.info("Datei erfolgreich gespeichert: {}", destinationFile);

            // trackMetadata um den Pfad ergänzen
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

            // In DB speichern
            trackRepository.create(trackToSave);
            log.info("Track in DB gespeichert: {}", trackToSave);

            return trackToSave;

        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Speichern der Datei auf dem Server", e);
        }
    }

//     Get Track by ID
    public Track getTrackById(Integer id) {
        Optional<Track> optTrack = trackRepository.getById(id);
        if (optTrack.isEmpty()) {
            throw new TrackNotFoundException();
        }
        return optTrack.get();
    }

    // weitere Methoden, z.B. getByName/Artist/Label; deleteTrackById, updateTrack, usw
}
