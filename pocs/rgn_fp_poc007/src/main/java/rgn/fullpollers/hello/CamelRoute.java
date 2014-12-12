package rgn.fullpollers.hello;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;

/**
 * CamelRoute.
 *
 * @author ragnarokkrr
 */
@Configuration
public class CamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jms:queue:books.new")
                .choice()
                .when().jsonpath("$.store.book[?(@.price < 10)]")
                .to("jms:queue:book.cheap")
                .when().jsonpath("$.store.book[?(@.price < 30)]")
                .to("jms:queue:book.average")
                .otherwise()
                .to("jms:queue:book.expensive");

    }
}
