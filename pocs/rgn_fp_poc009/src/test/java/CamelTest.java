import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ThreadPoolBuilder;
import org.apache.camel.component.hawtdb.HawtDBAggregationRepository;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spi.AggregationRepository;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

/**
 * Created by ragnarokkrr on 19/03/15.
 */
public class CamelTest extends CamelTestSupport {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @EndpointInject(uri = "mock:tap")
    protected MockEndpoint taptEndpoint;


    @Produce(uri = "direct:start")
    protected ProducerTemplate template;


    @Test
    public void testSendMatchingMessage() throws Exception {

        String expectedBody = "<matched/>";

        resultEndpoint.expectedBodiesReceived(expectedBody);

        template.sendBodyAndHeader(expectedBody, "foo", "bar");

        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void testSendNotMatchingMessage() throws Exception {
        resultEndpoint.expectedBodiesReceived(0);

        template.sendBodyAndHeader("<notMatched/>", "foo", "notMatchedHeaderValue");

        resultEndpoint.assertIsNotSatisfied();

    }

    @Test
    public void testSendSeda() throws Exception {

        String body = "Hello Camel";
        Object reply = template.requestBody("seda:start", body);
        System.out.println("Reply: " + reply);
    }

    @Test
    public void testABC() throws Exception {
        resultEndpoint.expectedBodiesReceived("ABC");

        template.sendBodyAndHeader("direct:start:aggregate", "A", "myId", 1);
        template.sendBodyAndHeader("direct:start:aggregate", "B", "myId", 1);
        template.sendBodyAndHeader("direct:start:aggregate", "F", "myId", 2);
        template.sendBodyAndHeader("direct:start:aggregate", "C", "myId", 1);


        resultEndpoint.assertIsSatisfied();
    }


    @Test
    public void testXML() throws Exception {
        resultEndpoint.expectedMessageCount(2);
        template.sendBody("direct:start:aggregate:xml"
                , "<order name=\"motor\" amount=\"1000\" customer=\"honda\" />");
        template.sendBody("direct:start:aggregate:xml"
                , "<order name=\"motor\" amount=\"500\" customer=\"toyota\" />");
        template.sendBody("direct:start:aggregate:xml"
                , "<order name=\"gearbox\" amount=\"200\" customer=\"toyota\" />");

        resultEndpoint.assertIsSatisfied();

    }


    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                ExecutorService lowPool = new ThreadPoolBuilder(context)
                        .poolSize(1).maxPoolSize(5).build("LowPool");

                HawtDBAggregationRepository myRepo = new
                        HawtDBAggregationRepository("myrepo", "data/myrepo.dat");

                myRepo.setUseRecovery(true);
                myRepo.setMaximumRedeliveries(4);
                myRepo.setDeadLetterUri("mock:dead");
                myRepo.setRecoveryInterval(3000);

//                from("direct:start")
//                        .filter((header("foo").isEqualTo("bar")))
//                        .to("mock:result");

                from("direct:start")
                        .filter((header("foo").isEqualTo("bar")))
                        .log("Incoming message ${body}")
                        .wireTap("direct:tap")
                        .to("mock:result");

                from("direct:tap")
                        .log("Tapped message ${body}")
                        .to("mock:tap");

                from("seda:start")
                        .to("log:A")
                        .transform(constant("Bye Camel"))
                        .to("log:B");

                from("direct:start:aggregate")
                        .log("Sending ${body} with correlation key ${header.myId}")
                        .aggregate(header("myId"), new MyAggregationStrategy())
                        .completionSize(3)
                        .log("Sending out ${body}")
                        .to("mock:result");

                from("direct:start:aggregate:xml")
                        .log("Sending ${body}")
                        .aggregate(xpath("/order/@customer"), new MyAggregationStrategy())
                        .aggregationRepository(myRepo)
                        .completionSize(2).completionTimeout(5000)
                        .log("Sending out ${body}")
                        .to("mock:result");
            }
        };
    }
}
