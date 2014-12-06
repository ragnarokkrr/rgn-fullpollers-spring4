package rgn.fullpollers.hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Seting Spring Data Rest base path.
 *
 * @author ragnarokkrr
 */
@Configuration
@Import(RepositoryRestMvcConfiguration.class)
public class RestDataConfig extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration configuration) {
        super.configureRepositoryRestConfiguration(configuration);
        try {

            configuration.setBaseUri(new URI("/rest"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
