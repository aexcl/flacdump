package vision.psy.flacdump.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class NavigationController {

    @GetMapping("/")
    public String landing() {
        return "landing";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String username, @RequestParam String password, Model model) {
        if ("ooz".equals(username) && "pass13".equals(password)) {
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}