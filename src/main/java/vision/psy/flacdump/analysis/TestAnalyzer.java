package vision.psy.flacdump.analysis;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestAnalyzer {
// Testklasse für die Analyse von HQ Audiodateien
// später Integration in TrackService
    public String analyzeFlacToJson(String filePath) throws IOException {
        File file = new File(filePath);
        Map<String, String> metadata = new HashMap<>();

        try {
            AudioFile audioFile = AudioFileIO.read(file);
            Tag tag = audioFile.getTag();

            if (tag != null) {
                // Extrahiere allgemeine Metadaten
                metadata.put("Title", tag.getFirst(FieldKey.TITLE));
                metadata.put("Artist", tag.getFirst(FieldKey.ARTIST));
                metadata.put("Album", tag.getFirst(FieldKey.ALBUM));
                metadata.put("Year", tag.getFirst(FieldKey.YEAR));
                metadata.put("Genre", tag.getFirst(FieldKey.GENRE));
                metadata.put("Track", tag.getFirst(FieldKey.TRACK));
                metadata.put("Disc", tag.getFirst(FieldKey.DISC_NO));
            }

            // Speichere spezifische Metadaten
            metadata.put("Duration", String.valueOf(audioFile.getAudioHeader().getTrackLength()));
            metadata.put("Bitrate", audioFile.getAudioHeader().getBitRate());
            metadata.put("SampleRate", audioFile.getAudioHeader().getSampleRate());
            metadata.put("Format", audioFile.getAudioHeader().getFormat());
        } catch (CannotReadException | ReadOnlyFileException | InvalidAudioFrameException | TagException e) {
            throw new IOException("Failed to read FLAC file metadata", e);
        }

        // Konvertiere Metadaten in JSON
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(metadata);
    }

    public static void main(String[] args) {
        TestAnalyzer analyzer = new TestAnalyzer();
        try {
            String filePath = "/XXX/XXX/Music/Geomagnetic label group - BPM - The Tribe.flac"; // Pfad zur FLAC-Datei
            String jsonOutput = analyzer.analyzeFlacToJson(filePath);
            System.out.println(jsonOutput);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
