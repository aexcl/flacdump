package vision.psy.flacdump.service;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vision.psy.flacdump.exceptions.TrackNotFoundException;
import vision.psy.flacdump.model.Track;
import vision.psy.flacdump.repository.TrackRepository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

// Service-Klasse für die Verarbeitung von Tracks

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
//      speichert es in dem Dateisystem und legt anschließend einen DB-Eintrag an.

    public Track uploadTrack(MultipartFile file, Track trackMetadata) {
        try {
            File dir = new File(uploadDir);
            // Zieldatei bestimmen
            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
            File destinationFile = new File(dir, uniqueFilename);

            // Datei auf Festplatte speichern
            file.transferTo(destinationFile);
            log.info("Datei erfolgreich gespeichert: {}", destinationFile);

            // Metadaten auslesen
            Track extractedMetadata = extractMetadataFromFile(destinationFile);
            // Falls Metadaten übergeben wurden, diese verwenden
            Track trackToSave = new Track();
            trackToSave.setId(trackMetadata.getId() != null ? trackMetadata.getId() : extractedMetadata.getId());
            trackToSave.setArtist(
                    (trackMetadata.getArtist() != null && !trackMetadata.getArtist().isBlank())
                            ? trackMetadata.getArtist()
                            : extractedMetadata.getArtist());
            trackToSave.setLabel(
                    (trackMetadata.getLabel() != null && !trackMetadata.getLabel().isBlank())
                            ? trackMetadata.getLabel()
                            : extractedMetadata.getLabel());
            trackToSave.setTitle(
                    (trackMetadata.getTitle() != null && !trackMetadata.getTitle().isBlank())
                            ? trackMetadata.getTitle()
                            : extractedMetadata.getTitle());
            trackToSave.setTrackLength(
                    trackMetadata.getTrackLength() != null
                            ? trackMetadata.getTrackLength()
                            : extractedMetadata.getTrackLength());
            trackToSave.setReleaseYear(
                    trackMetadata.getReleaseYear() != null
                            ? trackMetadata.getReleaseYear()
                            : extractedMetadata.getReleaseYear());
            trackToSave.setFileLocation(destinationFile.getAbsolutePath());

            // In DB speichern
            trackRepository.save(trackToSave);
            log.info("Track in DB gespeichert: {}", trackToSave);

            return trackToSave;

        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Speichern der Datei auf dem Server", e);
        }
    }

    public Track extractMetadataFromFile(File audioFile) throws IOException {
        try {
            AudioFile af = AudioFileIO.read(audioFile);
            Tag tag = af.getTag();

            // Defaults, falls Felder null sind
            String title = "";
            String artist = "";
            String label = "";
            String genre = "";
            String releaseType = "";
            Integer year = null;
            Integer trackLength = null;
            Integer sampleRate = null;
            Integer bitrate = null;
            String format = null;

            if (tag != null) {
                title  = tag.getFirst(FieldKey.TITLE);
                artist = tag.getFirst(FieldKey.ARTIST);
                genre  = tag.getFirst(FieldKey.GENRE);
                label = tag.getFirst(FieldKey.RECORD_LABEL);

                // Versuche YEAR zu parsen
                String rawYear = tag.getFirst(FieldKey.YEAR);
                if (rawYear != null && !rawYear.isBlank()) {
                    try {
                        year = Integer.valueOf(rawYear);
                    } catch (NumberFormatException e) {
                        log.warn("Konnte YEAR nicht parsen: {}", rawYear);
                    }
                }
            }

            if (af.getAudioHeader() != null) {
                trackLength = af.getAudioHeader().getTrackLength();
                format      = af.getAudioHeader().getFormat();

                String bitRateString = af.getAudioHeader().getBitRate();
                if (bitRateString != null && !bitRateString.isBlank()) {
                    try {
                        bitrate = Integer.valueOf(bitRateString);
                    } catch (NumberFormatException e) {
                        log.warn("Konnte Bitrate nicht parsen: {}", bitRateString);
                    }
                }

                String sampleRateString = af.getAudioHeader().getSampleRate();
                if (sampleRateString != null && !sampleRateString.isBlank()) {
                    try {
                        sampleRate = Integer.valueOf(sampleRateString);
                    } catch (NumberFormatException e) {
                        log.warn("Konnte SampleRate nicht parsen: {}", sampleRateString);
                    }
                }
            }

            return new Track(
                    null,
                    artist,
                    label,
                    title,
                    trackLength,
                    year,
                    releaseType,
                    format,
                    sampleRate.longValue(),
                    bitrate.longValue(),
                    genre,
                    null,
                    audioFile.getAbsolutePath()
            );

        } catch (CannotReadException | ReadOnlyFileException | InvalidAudioFrameException | TagException e) {
            throw new IOException("Fehler beim Lesen der Metadaten via JAudiotagger", e);
        }
    }

//     Get Track by ID
    public Track getTrackById(Integer id) {
        return trackRepository.findById(id)
                .orElseThrow(TrackNotFoundException::new);
    }

    // weitere Methoden, z.B. downloadTrack, getByName/Artist/Label; deleteTrackById, usw
}
