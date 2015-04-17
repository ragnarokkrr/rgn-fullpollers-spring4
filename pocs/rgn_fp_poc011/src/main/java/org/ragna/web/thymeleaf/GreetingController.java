package org.ragna.web.thymeleaf;

/**
 * Created by ragnarokkrr on 15/04/15.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(
            value = "name"
            , required = true
            , defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);

        return "greeting";
    }


}
