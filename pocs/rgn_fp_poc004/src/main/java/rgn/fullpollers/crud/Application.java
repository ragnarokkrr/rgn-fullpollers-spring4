package rgn.fullpollers.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * Bootstrap.
 *
 * @author ragnarokkrr
 */
@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@Import(RepositoryRestMvcConfiguration.class)
@ComponentScan
public class Application implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(taskRepository.findAll());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
