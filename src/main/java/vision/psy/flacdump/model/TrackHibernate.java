package vision.psy.flacdump.model;

import jakarta.persistence.*;

// Diese Klasse wird den bestehenden Track-Record ersetzen
// und die Datenbank-Entität Track mithilfe von Hibernate abbilden

@Entity
@Table(name = "track")
public class TrackHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(nullable = false)
    public String artist;
    @Column
    public String label;
    @Column(nullable = false)
    public String title;
    @Column(nullable = false)
    public Integer trackLength;
    @Column
    public Integer releaseYear;
    @Column
    public String releaseType;      // Später Enum
    @Column
    public String fileFormat;       // Später Enum
    @Column
    public Long sampleRate;
    @Column
    public Long bitRate;
    @Column
    public String genre;            // Später Enum
    @Lob
    @Column(columnDefinition = "BLOB")
    public byte[] albumArt;
    @Column(nullable = false)
    public String fileLocation;     // Später ggf verschlüsseln


    public TrackHibernate() {
    }
}