package vision.psy.flacdump.exceptions;

public class TrackNotFoundException extends RuntimeException {
    public TrackNotFoundException() {
        super("File not found");
    }
}
