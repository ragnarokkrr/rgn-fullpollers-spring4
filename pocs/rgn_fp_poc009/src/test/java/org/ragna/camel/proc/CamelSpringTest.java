package org.ragna.camel.proc;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ThreadPoolBuilder;
import org.apache.camel.component.hazelcast.HazelcastConstants;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.junit.Test;
import org.ragna.camel.proc.model.Person;
import org.ragna.camel.proc.model.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.jms.ConnectionFactory;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by ragnarokkrr on 24/03/15.
 * <p/>
 * take a look http://svn.apache.org/repos/asf/camel/trunk/camel-core/src/test/java/org/apache/camel/processor/ComposedMessageProcessorTest.java
 */
@ContextConfiguration
@Import({CamelSpringTest.CamelContextConfig.class
        , CamelSpringTest.BeansConfiguration.class})
public class CamelSpringTest extends AbstractJUnit4SpringContextTests {

    @EndpointInject(uri = "mock:result:aggregate")
    protected MockEndpoint resultAggregateEndpoint;


    @Produce(uri = "direct:set:proc:id")
    protected ProducerTemplate setProcIdTemplate;

    @Produce(uri = "direct:next:proc:id")
    protected ProducerTemplate nextProcIdTemplate;

    @Produce(uri = "direct:person:start")
    protected ProducerTemplate personTemplate;

    @DirtiesContext
    @Test
    public void testAggregate() throws Exception {

        setProcIdTemplate.requestBody(100L);

        Long procId = (Long) nextProcIdTemplate.requestBody(2000L,Long.class);

        List<Person> people = (List<Person>) personTemplate.requestBodyAndHeader("init", "procId", procId);

        Thread.sleep(3000);

        resultAggregateEndpoint.getExchanges().get(0).getIn().getBody(List.class);
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

        @Bean
        public PersonUpdater2Processor personUpdater2Processor() {
            return new PersonUpdater2Processor();
        }


    }

    @Configuration
    public static class CamelContextConfig extends SingleRouteCamelConfiguration {

        @Bean
        public RouteBuilder route() {
            return new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    ExecutorService peopleExecutor = buildExecutorService();
                    startAmq();

                    from("direct:person:start").id("person-synching")
                            .setExchangePattern(ExchangePattern.InOut)
                            .to("seda:person:planning")
                    ;

                    from("seda:person:planning").id("person-planning-split")
                            .beanRef("personPlanningProcessor")
                            .log("=====> BEFORE SPLIT ${body} procId: ${header[procId]}")
                            .split(body(), new PersonCollectionAggregationStrategy()).parallelProcessing().executorService(peopleExecutor)
                                .inOut("direct:person:process")
                                .inOnly("seda:progress")
                            .end()
                            .to("seda:aggregate:person:result")
                    ;

                    from("direct:person:process").id("person-process")
                            .setHeader("personId").groovy("exchange.in.body")
                            .beanRef("personRepository", "findById")
                            .setHeader("person").groovy("exchange.in.body")
                            .beanRef("personUpdaterProcessor")
                            .beanRef("personUpdater2Processor")
                                    //.setHeader("personProcessed").groovy("exchange.in.body")
                            .log("=====> AFTER SPLIT Process ${body} procId: ${header[procId]}")
                    ;

                    from("seda:aggregate:person:result").id("person-aggregate")
                            .log("=====> AGGREGATE RESULT ${body} ########")
                            .to("mock:result:aggregate")
                    ;

                    from("seda:progress").id("person-progress")
                            .log(LoggingLevel.DEBUG, "=====> PROGRESS ${body.name} ${header[procId]} ${header[person.allIds]}")
                            .transform().groovy("1 / exchange.in.headers['person.allIds'].size() * 100")
                            .transform().groovy("sprintf ('%.2f', exchange.in.body)")
                            .setHeader("person.progress.msg")
                                    .groovy("sprintf ('PROGRESS increment for Process ID \\'%s\\': %s%% (from person \\'%s\\') '"
                                            + ", exchange.in.headers['procId']"
                                            + ", exchange.in.body"
                                            + ", exchange.in.headers['person']"
                                            + ")")
                            .to("seda:status:update")
                            .log("=====> ${header[person.progress.msg]}")
                    ;

                    from("seda:status:update").id("person-status-update-action")
                            .log("=====> STATUS UPDATE procId(${header[procId]}) - personId(${header[person.id]}): ${body}")
                            .setBody().groovy("exchange.in.body")
                            .to("direct:person:status:upd")

                    ;

                    from("direct:set:proc:id").id("set-proc-id")
                            .setHeader(HazelcastConstants.OPERATION, constant(HazelcastConstants.SETVALUE_OPERATION))
                            .toF("hazelcast:%sproc-id", HazelcastConstants.ATOMICNUMBER_PREFIX)
                    ;

                    from("direct:next:proc:id").id("next-proc-id")
                            .setHeader(HazelcastConstants.OPERATION, constant(HazelcastConstants.INCREMENT_OPERATION))
                            .toF("hazelcast:%sproc-id", HazelcastConstants.ATOMICNUMBER_PREFIX)
                    ;

                    from("direct:person:status:upd").id("person-status-update")
                            .log(LoggingLevel.DEBUG, "======> UPDATE MAP STATUS '${header[procId]}': ${header[person.id]} ${body}")
                            //.aggregate(header("procId")).completionSize(3)
                            .setHeader(HazelcastConstants.OBJECT_ID).groovy("exchange.in.headers['procId']")
                            .setHeader(HazelcastConstants.OPERATION, constant(HazelcastConstants.PUT_OPERATION))
                            .toF("hazelcast:%sperson:status", HazelcastConstants.MAP_PREFIX)
                    ;

                    fromF("hazelcast:%sperson:status", HazelcastConstants.MAP_PREFIX).id("status-listener")
                            .log(LoggingLevel.DEBUG, "object... ${header[CamelHazelcastObjectId]}")
                            .choice()
                            .when(header(HazelcastConstants.LISTENER_ACTION).isEqualTo(HazelcastConstants.ADDED))
                                .log("Object '${header[CamelHazelcastObjectId]}' ...added")
                                .to("mock:added")
                            .when(header(HazelcastConstants.LISTENER_ACTION).isEqualTo(HazelcastConstants.EVICTED))
                                .log("Object '${header[CamelHazelcastObjectId]}' ...envicted")
                                .to("mock:envicted")
                            .when(header(HazelcastConstants.LISTENER_ACTION).isEqualTo(HazelcastConstants.UPDATED))
                                .log("Object '${header[CamelHazelcastObjectId]}' ...updated")
                                .to("mock:updated")
                            .when(header(HazelcastConstants.LISTENER_ACTION).isEqualTo(HazelcastConstants.REMOVED))
                                .log("Object '${header[CamelHazelcastObjectId]}' ...removed")
                                .to("mock:removed")
                            .otherwise()
                               .log("fail!")
                    ;


                }

                private ExecutorService buildExecutorService() throws Exception {
                    ThreadPoolBuilder threadPoolBuilder = new ThreadPoolBuilder(camelContext());
                    return threadPoolBuilder.poolSize(3).maxPoolSize(5)
                            .maxQueueSize(20).build("peopleExecutor");
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
