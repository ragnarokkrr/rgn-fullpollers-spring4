package org.ragna.camel.proc;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hazelcast.HazelcastConstants;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.ragna.camel.proc.model.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

/**
 * Created by ragnarokkrr on 24/03/15.
 *
 * take a look http://svn.apache.org/repos/asf/camel/trunk/camel-core/src/test/java/org/apache/camel/processor/ComposedMessageProcessorTest.java
 */
@ContextConfiguration(

)
@Import({CamelSpringTest.CamelContextConfig.class
    , CamelSpringTest.BeansConfiguration.class})
public class CamelSpringTest extends AbstractJUnit4SpringContextTests {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @EndpointInject(uri = "mock:result:aggregate")
    protected MockEndpoint resultAggregateEndpoint;


    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Produce(uri = "direct:hazelcast")
    protected ProducerTemplate hazelTemplate;

    @Produce(uri = "direct:person:start")
    protected ProducerTemplate personTemplate;

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
    public void testAggregate() throws Exception {

        // template.sendBody("hazelcast:queue:myqueue:put", "test");

        personTemplate.sendBodyAndHeader("init", "procId", 4666);

        resultAggregateEndpoint.getExchanges();

        System.out.println(resultAggregateEndpoint.getExchanges().get(0).getIn().getBody(List.class));
        // resultEndpoint.assertIsSatisfied();

    }

    @Configuration
    public static class BeansConfiguration {

        @Bean
        public PersonRepository personRepository() {
            return new PersonRepository();
        }

        @Bean
        public PersonPlanningProcessor personPlanningProcessor(PersonRepository personRepository) {
            return new PersonPlanningProcessor(personRepository);
        }

        @Bean
        public PersonUpdaterProcessor personUpdaterProcessor() {
            return new PersonUpdaterProcessor();
        }

    }

    @Configuration
    public static class CamelContextConfig extends SingleRouteCamelConfiguration {

        @Bean
        public RouteBuilder route() {
            return new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    startAmq();

                    from("direct:start")
                            .filter(header("foo").isEqualTo("bar")).to("mock:result");

                    from("hazelcast:queue:myqueue:get")
                            .threads(6)
                            .to("mock:result");

                    from("direct:hazelcast")
                            .log("===================> ${body}")
                            .setHeader(HazelcastConstants.OPERATION, constant(HazelcastConstants.INCREMENT_OPERATION))
                            .toF("hazelcast:%sprocId", HazelcastConstants.ATOMICNUMBER_PREFIX)
                            // .setBody("new body '${body}'")
                            .log("===================> ${header[procId]}")
                            .log("===================> ${headers}")
                            .log("===================> ${body}")

                    ;

                    from("direct:person:start")
                            .beanRef("personPlanningProcessor")
                            .log("=====> BEFORE SPLIT ${body} procId: ${header[procId]}")
                            .split().body().parallelProcessing()
                                .beanRef("personRepository", "findById")
                                .log("=====> AFTER SPLIT ${body} procId: ${header[procId]}")
                                .beanRef("personUpdaterProcessor")
                                .log("=====> AFTER UPDATE ${body} procId: ${header[procId]}")
                            .to("seda:aggregate:person");

                    from("seda:aggregate:person")
                            .log("################### SEDA ${body} ########")
                            .aggregate(new PersonAggregationStrategy()).header("procId").completionTimeout(5 * 1000L)
                            .log("################### AGGREGATE ${body} ########")
                            .to("mock:result:aggregate");
                            ;

                }

                private void startAmq() throws Exception {
                    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
                    // Note we can explicit name the component
                    camelContext().addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
                }
            };
        }
    }
}
