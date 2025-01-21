package vision.psy.flacdump.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import vision.psy.flacdump.model.Track;

import java.util.List;
import java.util.Optional;

// zu Lernzwecken alles Plain old Java
// wird noch komplett mit JPA oder Hibernate bzw Spring Data umgeschrieben

@Repository
public class TrackRepository {

    private static final Logger log = LoggerFactory.getLogger(TrackRepository.class);
    private final JdbcClient jdbcClient;

    public TrackRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Track> getAll(){
        return jdbcClient.sql("SELECT * FROM Track")
                .query(Track.class)
                .list();
    }

    public Optional<Track> getById(Integer id){
        return jdbcClient.sql("SELECT * FROM track WHERE id = :id")
                .param("id", id)
                .query(Track.class)
                .optional();
    }

    public Optional<Track> getByArtist(String artist){
        return jdbcClient.sql("SELECT * FROM track WHERE artist = ?")
                .param("artist", artist)
                .query(Track.class)
                .optional();
    }

    public Optional<Track> getByLabel(String label){
        return jdbcClient.sql("SELECT * FROM track WHERE label = ?")
                .param("label", label)
                .query(Track.class)
                .optional();
    }

    public void create(Track track){
        var updated = jdbcClient.sql("INSERT INTO track (id, artist, label, title, trackLength, releaseYear, releaseType, fileFormat, sampleRate, bitRate, genre, albumArt, fileLocation) values (?,?,?,?,?,?,?,?,?,?,?,?,?)")
                .params(track.id(), track.artist(), track.label(), track.title(), track.trackLength(), track.releaseYear(), track.releaseType(), track.fileFormat(), track.sampleRate(), track.bitrate(), track.genre(), track.albumArt(), track.fileLocation())
                .update();
        Assert.state (updated == 1, "Track konnte nicht erstellt werden");
    }

    public void update(Track track, Integer id){
        var updated = jdbcClient.sql("UPDATE track SET title = ?, label = ?, artist = ?, trackLength = ?, releaseYear = ?, releaseType = ?, fileFormat = ?, sampleRate = ? where id = ?")
                .params(track.title(), track.label(), track.artist(), track.trackLength(), track.releaseYear(), track.releaseType(), track.fileFormat(), track.sampleRate(), id)
                .update();
        Assert.state (updated == 1, "Track konnte nicht aktualisiert werden");
    }

    public void delete (Integer id){
        var updated = jdbcClient.sql("DELETE FROM track WHERE id = ?")
                .params(id)
                .update();
        Assert.state (updated == 1, "Track konnte nicht gelöscht werden");
    }

    public void saveAll(List<Track> tracks) {
        tracks.stream().forEach(this::create);
    }

    public void count(){jdbcClient.sql("SELECT COUNT(*) FROM track").query().listOfRows().size();}
}