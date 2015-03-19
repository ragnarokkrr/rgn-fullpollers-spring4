package org.rgn.fp.equip.shard.mng;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Producer;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

/**
 * EquipmentController.
 *
 * @author ragnarokkrr
 */
@RestController
@RequestMapping("/shard/manager/equipment")
public class EquipmentController {

    private final String POST_EQUIP_QUEUE = "post-equipment";

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    ConsumerTemplate consumerTemplate;

    @Autowired
    CamelContext camelContext;

    @RequestMapping(method = RequestMethod.POST)
    public String post(@RequestBody EquipmentVO input) {

        producerTemplate.sendBody("jms:queue:" + POST_EQUIP_QUEUE, ExchangePattern.InOnly, input);

        // requestViaJmsTemplate(input);

        // requestReply(input);

        // requestViaProducerTemplateTempQueue(input);

        // requestViaProducerTemplateResponseQueue(input);

        System.out.println(input);
        return input.toString();
    }

    private void requestViaProducerTemplateResponseQueue(EquipmentVO input) {
        Map<String, Object> jmsHeaders = Maps.newHashMap();

        jmsHeaders.put("JMSReplyTo", "jms:queue:"
            + POST_EQUIP_QUEUE + "-res");

        final Object response = producerTemplate.requestBodyAndHeaders("jms:queue:" + POST_EQUIP_QUEUE, input, jmsHeaders);

        System.out.println(response);
    }

    private void requestViaProducerTemplateTempQueue(EquipmentVO input) {
        final Object requestBody = producerTemplate.requestBody("jms:queue:" + POST_EQUIP_QUEUE, input);
        System.out.println("Request Body>>>>>>> " + requestBody);
    }

    private void requestViaJmsTemplate(EquipmentVO input) {
        jmsTemplate.convertAndSend(POST_EQUIP_QUEUE, input);
    }

    private void requestReply(EquipmentVO input) {
        try {
            // get the endpoint from the camel context
            Endpoint endpoint = camelContext.getEndpoint("jms:queue:numbers");

            // create the exchange used for the communication
            // we use the in out pattern for a synchronized exchange where we expect a response
            Exchange exchange = endpoint.createExchange(ExchangePattern.InOut);
            // set the input on the in body
            // must be correct type to match the expected type of an Integer object
            exchange.getIn().setBody(input);

            // to send the exchange we need an producer to do it for us
            Producer producer = endpoint.createProducer();
            // start the producer so it can operate
            producer.start();

            // let the producer process the exchange where it does all the work in this oneline of code
            System.out.println("Invoking the multiply with 11");
            producer.process(exchange);

            // get the response from the out body and cast it to an integer
            String response = exchange.getOut().getBody(String.class);
            System.out.println("... the result is: " + response);

            // stop and exit the client
            producer.stop();

        } catch (Exception e) {
            System.out.println("nao deu....");
            e.printStackTrace();
        }
    }

}
