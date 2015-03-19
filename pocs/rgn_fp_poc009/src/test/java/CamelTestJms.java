import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import javax.jms.ConnectionFactory;

/**
 * Created by nilseu.padilha on 19/03/15.
 */
public class CamelTestJms extends CamelTestSupport{

    @Produce(uri="direct:start")
    protected ProducerTemplate template;

    @Test
    public void testSomething(){
        template.sendBody("jms:queue:test", "aaaaaaa");
    }


    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder(){

            @Override
            public void configure() throws Exception {
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
                // Note we can explicit name the component
                context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

                from("jms:queue:test").log("============> ${body}");
            }
        };
    }
}
