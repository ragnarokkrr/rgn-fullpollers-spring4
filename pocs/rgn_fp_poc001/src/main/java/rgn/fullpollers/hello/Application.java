package rgn.fullpollers.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Bootstrap.
 *
 * @author ragnarokkrr
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class);

        System.out.println("Let's inspect the beans provided....");

        String[] beanNames = context.getBeanDefinitionNames();

        List<String> beanNamesList = Arrays.asList(beanNames);

        beanNamesList.stream().forEach(System.out::println);

    }
}
