package vision.psy.flacdump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import vision.psy.flacdump.model.Track;
import vision.psy.flacdump.model.UserAccount;
import vision.psy.flacdump.service.TrackService;
import vision.psy.flacdump.service.UserService;

// Überarbeitung mit Spring Security

@Controller
public class NavigationController {

    @Autowired
    private UserService userService;

    private final TrackService trackService;

    public NavigationController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/")
    public String landing() {
        return "landing";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

//    @GetMapping("/index")
//    public String index() {
//        return "index";
//    }


    @PostMapping("/login")
    public String authenticate(@RequestParam String username, @RequestParam String password, Model model) {
        UserAccount userAccount = userService.login(username, password);
        if (userAccount != null) {
            return "index";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @PostMapping("/upload")
    public String handleUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("artist") String artist,
            @RequestParam("label") String label,
            @RequestParam("title") String title,
            Model model
    ) {
        try{
            Track track = new Track();
            Track savedTrack = trackService.uploadTrack(file, track);
            model.addAttribute("message", "Upload successful");
            model.addAttribute("messageType", "success");
            model.addAttribute("track", savedTrack);
        } catch (Exception e) {
            model.addAttribute("message", "Upload failed: " + e.getMessage());
            model.addAttribute("messageType", "error");
        }
        return "upload";
    }
}