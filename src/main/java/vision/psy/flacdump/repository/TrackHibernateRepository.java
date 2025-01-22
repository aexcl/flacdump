package vision.psy.flacdump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vision.psy.flacdump.model.TrackHibernate;

import java.util.List;
import java.util.Optional;

// neue TrackRepository mit Hibernate
// wird die alte TrackRepository ersetzen

@Repository
public interface TrackHibernateRepository extends JpaRepository<TrackHibernate, Integer> {
    Optional<TrackHibernate> findByArtist(String artist);
    Optional<TrackHibernate> findByLabel(String label);
    List<TrackHibernate> findByTitleIgnoreCase(String title);
    List<TrackHibernate> findByreleaseYear(Integer releaseYear);
    List<TrackHibernate> findByReleaseType(String releaseType);
    List<TrackHibernate> findByFileFormat(String fileFormat);
    List<TrackHibernate> findByGenre(String genre);
    List<TrackHibernate> findByBpm(Integer bpm);
    List<TrackHibernate> findByBpmBetween(Integer minBpm, Integer maxBpm);
}
