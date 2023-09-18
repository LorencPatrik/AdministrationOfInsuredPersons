package cz.lorsoft.administrationOfTheInsureds.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String renderIndex(){
        return "pages/home/index";
    }
    @GetMapping("/aboutMe")
    public String renderAboutMe() {
        return "pages/home/aboutMe";
    }
    @GetMapping("/insureds")
    public String renderInsureds(){
        return "pages/home/insureds";
    }
}
