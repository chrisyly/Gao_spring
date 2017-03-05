package demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloApplication {

    @RequestMapping("/")
    public String index() {
        return "Greetings from UC DAVIS!";
    }

}
