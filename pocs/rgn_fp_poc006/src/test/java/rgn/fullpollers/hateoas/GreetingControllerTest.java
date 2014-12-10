package rgn.fullpollers.hateoas;

import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

/**
 * Test for {@link GreetingController}.
 *
 * @author ragnarokkrr
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class GreetingControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void canGreetJohn() {
        String name = "John";

        when().
                get("/greeting?name={name}", name).
                then().
                log().everything().
                statusCode(HttpStatus.SC_OK).
                body("content", is("Hello, John!")).
                body("_links.self.href", containsString("/greeting?name=John"));
        ;

    }
}
