package rgn.fullpollers.hello;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Dummy Health.
 *
 * @author ragnarokkrr
 */
@Component
public class MyHealth implements HealthIndicator {
    @Override
    public Health health() {
        return Health.down().withException(new Exception("dummy")).build();
    }
}
