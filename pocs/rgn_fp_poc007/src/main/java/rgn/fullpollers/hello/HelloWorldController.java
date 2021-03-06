package rgn.fullpollers.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Controller.
 *
 * @author ragnarokkrr
 */
@Controller
public class HelloWorldController {

    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    JmsTemplate jmsTemplate;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public
    @ResponseBody
    Greeting sayHello(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) throws InterruptedException {
        Thread.sleep(1000); // simulated delay
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @MessageMapping("/hello-world")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(3000);
        return new Greeting(counter.incrementAndGet(), "Hello, " + message.getName() + "!");
    }


    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public
    @ResponseBody
    Greeting sayHello2(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) throws InterruptedException {
        jmsTemplate.convertAndSend("teste-jms", name);

        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }


}
