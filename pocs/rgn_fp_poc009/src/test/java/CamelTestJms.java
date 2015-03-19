import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.rx.ReactiveCamel;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;

import javax.jms.ConnectionFactory;

/**
 * Created by nilseu.padilha on 19/03/15.
 */
public class CamelTestJms extends CamelTestSupport{

    @Produce(uri="direct:start")
    protected ProducerTemplate template;

    @Test
    public void testSomething(){

        ReactiveCamel rx =  new ReactiveCamel(context());


        Observable<Message> observable = rx.toObservable("jms:queue:test");

        final Observable<String> myHeaderObservable = observable.filter(new Func1<Message, Boolean>() {
            @Override
            public Boolean call(Message message) {
                final Object myHeader = message.getHeader("myHeader");
                return myHeader != null && Long.valueOf(425).equals(myHeader);
            }
        }).map(new Func1<Message, String>() {
            @Override
            public String call(Message message) {
                String body = message.getBody(String.class);
                System.out.println("OBSERVED ================> " + body);
                return "observed ====>" + body;
            }
        });



        template.sendBodyAndHeader("jms:queue:test", "aaaaaaa", "myHeader", 425);
        template.sendBodyAndHeader("jms:queue:test", "bbbbbbbb", "myHeader", 1000);


        System.out.println("Observable ====> " +
        myHeaderObservable.take(1));
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
