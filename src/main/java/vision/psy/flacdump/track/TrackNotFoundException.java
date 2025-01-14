package vision.psy.flacdump.track;

public class TrackNotFoundException extends RuntimeException {
    public TrackNotFoundException() {
        super("File not found");
    }
}
