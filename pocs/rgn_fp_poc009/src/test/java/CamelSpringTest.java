import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.jms.ConnectionFactory;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ragnarokkrr on 24/03/15.
 */
@ContextConfiguration(

)
@Import({CamelSpringTest.CamelContextConfig.class
        , CamelSpringTest.BeansConfiguration.class})
public class CamelSpringTest extends AbstractJUnit4SpringContextTests {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Autowired
    protected Test test;

    @DirtiesContext
    @org.junit.Test
    public void testSendMatchingMessage() throws Exception {
        String expectedBody = "<matched/>";

        resultEndpoint.expectedBodiesReceived(expectedBody);

        template.sendBodyAndHeader(expectedBody, "foo", "bar");

        template.sendBody("hazelcast:queue:myqueue", "test");

        resultEndpoint.assertIsSatisfied();

    }
    @DirtiesContext
    @org.junit.Test
    public void testHazelcast() throws Exception {

        template.sendBody("hazelcast:queue:myqueue:put", "test");

        //resultEndpoint.assertIsSatisfied();

    }



    public static class Test {
        private String value;

        public Test(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }

    @Configuration
    public static class BeansConfiguration {

        @Bean
        public Test teste() {
            return new Test("Teste");
        }

    }

    @Configuration
    public static class CamelContextConfig extends SingleRouteCamelConfiguration {

        @Bean
        public RouteBuilder route() {
            return new RouteBuilder() {
                @Override
                public void configure() throws Exception {


                    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
                    // Note we can explicit name the component
                    camelContext().addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

                    

                    from("direct:start")
                            .filter(header("foo").isEqualTo("bar")).to("mock:result");

                    from("hazelcast:queue:myqueue:get")
                            .threads(6)
                            .to("mock:result");
                }
            };
        }
    }
}
