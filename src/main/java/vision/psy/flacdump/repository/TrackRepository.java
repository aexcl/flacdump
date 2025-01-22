package vision.psy.flacdump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vision.psy.flacdump.model.Track;

import java.util.List;
import java.util.Optional;

// neue TrackRepository mit Hibernate
// wird die alte TrackRepository ersetzen

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {
    Optional<Track> findByArtist(String artist);
    Optional<Track> findByLabel(String label);
    List<Track> findByTitleIgnoreCase(String title);
    List<Track> findByreleaseYear(Integer releaseYear);
    List<Track> findByReleaseType(String releaseType);
    List<Track> findByFileFormat(String fileFormat);
    List<Track> findByGenre(String genre);
    // List<Track> findByBpm(Integer bpm);
    // List<Track> findByBpmBetween(Integer minBpm, Integer maxBpm);
}
