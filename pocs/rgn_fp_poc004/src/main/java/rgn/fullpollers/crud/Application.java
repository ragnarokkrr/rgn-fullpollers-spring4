package rgn.fullpollers.crud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Bootstrap.
 *
 * @author ragnarokkrr
 */
@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@ComponentScan
public class Application implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        /* no op */
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
