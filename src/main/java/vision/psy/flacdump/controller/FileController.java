package vision.psy.flacdump.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vision.psy.flacdump.exceptions.TrackNotFoundException;
import vision.psy.flacdump.model.Track;
import vision.psy.flacdump.repository.TrackRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final TrackRepository trackRepository;
    public FileController(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    // Get
    @GetMapping({"", "/"})
    public List<Track> findAll(){
        return trackRepository.findAll();
    }

    @GetMapping("/{id}")
    public Track findById(@PathVariable Integer id){
        Optional<Track> track = trackRepository.findById(id);
        if (track.isEmpty()) {
            throw new TrackNotFoundException();}
        return track.get();
    }

    @GetMapping("/artist/{artist}")
    public Track findByArtist(@PathVariable String artist){
        Optional<Track> track = trackRepository.findByArtist(artist);
        if (track.isEmpty()) {
            throw new TrackNotFoundException();}
        return track.get();
    }

    // Post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void createFile(@Valid @RequestBody Track track) {
        trackRepository.save(track);
    }

    // Put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateFile(@PathVariable Integer id, @Valid @RequestBody Track track) {
        if (!trackRepository.existsById(id)) {
            throw new TrackNotFoundException();
        }
        track.setId(id);
        trackRepository.save(track);
    }


    // Delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Integer id){
        if(!trackRepository.existsById(id)){
            throw new TrackNotFoundException();
        }
        trackRepository.deleteById(id);
    }
}
