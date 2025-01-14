package vision.psy.flacdump.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vision.psy.flacdump.model.Track;
import vision.psy.flacdump.exceptions.TrackNotFoundException;
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
    List<Track> getAll(){
        return trackRepository.getAll();
    }

    @GetMapping("/{id}")
    Track getById(@PathVariable Integer id){

        Optional<Track> track = trackRepository.getById(id);
        if (track.isEmpty()) {
            throw new TrackNotFoundException();}
        return track.get();
    }

    @GetMapping("/{artist}")
    Track geByArtist(@PathVariable String artist){
        Optional<Track> track = trackRepository.getByArtist(artist);
        if (track.isEmpty()) {
            throw new TrackNotFoundException();}
        return track.get();
    }

    // Post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void createFile(@Valid @RequestBody Track track){
        trackRepository.create(track);
    }


    // Put
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    void updateFile(@Valid @RequestBody Track track, @PathVariable Integer id){
        trackRepository.update(track, id);
    }


    // Delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteFile(@PathVariable Integer id){
        trackRepository.delete(id);
    }
}
