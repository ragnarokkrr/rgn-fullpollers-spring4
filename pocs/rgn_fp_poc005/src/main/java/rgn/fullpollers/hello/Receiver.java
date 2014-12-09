package rgn.fullpollers.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.FileSystemUtils;

import java.io.File;

/**
 * Receive a Message
 *
 * @author ragnarokkrr
 */
public class Receiver {

    @Autowired
    ConfigurableApplicationContext context;

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        context.close();
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }

}
