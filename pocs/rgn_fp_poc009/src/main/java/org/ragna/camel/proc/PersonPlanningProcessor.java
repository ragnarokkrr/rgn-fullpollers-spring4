package org.ragna.camel.proc;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.ragna.camel.proc.model.PersonRepository;

/**
 * Created by nilseu.padilha on 25/03/15.
 */
public class PersonPlanningProcessor implements Processor {
    private PersonRepository personRepository;

    public PersonPlanningProcessor(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final List<Long> allIds = personRepository.findAllIds();

        exchange.getIn().setHeader("person.allIds", allIds);
        exchange.getIn().setBody(allIds);
    }
}
