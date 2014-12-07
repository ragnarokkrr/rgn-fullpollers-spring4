package rgn.fullpollers.crud;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home Controller.
 *
 * @author ragnarokkrr
 */
@Controller
public class HomeController {
    @RequestMapping("/home")
    public String home() {
        return "index";
    }
}
