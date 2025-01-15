package vision.psy.flacdump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import vision.psy.flacdump.model.UserAccount;
import vision.psy.flacdump.service.UserService;

@Controller
public class NavigationController {

    @Autowired
    private UserService userService;

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
        UserAccount userAccount = userService.login(username, password);
        if (userAccount != null) {
            return "index";
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