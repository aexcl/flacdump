package vision.psy.flacdump.model;

import jakarta.persistence.*;

// Diese Klasse wird den bestehenden Track-Record ersetzen
// und die Datenbank-Entität Track mithilfe von Hibernate abbilden

@Entity
@Table(name = "track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String artist;
    @Column
    private String label;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Integer trackLength;
    @Column
    private Integer releaseYear;
    @Column
    private String releaseType;      // Später Enum
    @Column
    private String fileFormat;       // Später Enum
    @Column
    public Long sampleRate;
    @Column
    private Long bitRate;
    @Column
    private String genre;            // Später Enum
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] albumArt;
    @Column(nullable = false)
    private String fileLocation;     // Später ggf verschlüsseln

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTrackLength() {
        return trackLength;
    }

    public void setTrackLength(Integer trackLength) {
        this.trackLength = trackLength;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public Long getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(Long sampleRate) {
        this.sampleRate = sampleRate;
    }

    public Long getBitRate() {
        return bitRate;
    }

    public void setBitRate(Long bitRate) {
        this.bitRate = bitRate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public byte[] getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(byte[] albumArt) {
        this.albumArt = albumArt;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    // Konstruktoren
    public Track() {
    }

    public Track(Integer id, String artist, String label, String title, Integer trackLength, Integer releaseYear, String releaseType, String fileFormat, Long sampleRate, Long bitRate, String genre, byte[] albumArt, String fileLocation) {
        this.id = id;
        this.artist = artist;
        this.label = label;
        this.title = title;
        this.trackLength = trackLength;
        this.releaseYear = releaseYear;
        this.releaseType = releaseType;
        this.fileFormat = fileFormat;
        this.sampleRate = sampleRate;
        this.bitRate = bitRate;
        this.genre = genre;
        this.albumArt = albumArt;
        this.fileLocation = fileLocation;
    }
}