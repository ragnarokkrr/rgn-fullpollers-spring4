package rgn.fullpollers.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Bootstrap.
 *
 * @author ragnarokkrr
 */
@Configuration
@EnableJpaRepositories
@Import({RepositoryRestMvcConfiguration.class, RestDataConfig.class})
@ComponentScan
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run(String... strings) throws Exception {
/*
        personRepository.deleteAll();
        personRepository.save(populatePersons());
*/
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private List<Person> populatePersons() {
        Person johnConnor;
        Person sarahConnor;
        Person kyleReese;
        Person nickStahl;
        Person edwardFurlong;
        Person christianBale;

        johnConnor = new Person("John", "Connor");
        sarahConnor = new Person("Sarah", "Connor");
        kyleReese = new Person("Kyle", "Reese");
        nickStahl = new Person("Nick", "Stahl");
        edwardFurlong = new Person("Edward", "Furlong");
        christianBale = new Person("Christian", "Bale");
        return Arrays.asList(johnConnor, sarahConnor, kyleReese, nickStahl, edwardFurlong, christianBale);
    }

}
