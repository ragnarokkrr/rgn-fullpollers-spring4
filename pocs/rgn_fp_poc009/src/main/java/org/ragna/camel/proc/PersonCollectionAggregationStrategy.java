package org.ragna.camel.proc;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AbstractListAggregationStrategy;
import org.ragna.camel.proc.model.Person;

/**
 * Created by ragnarokkrr on 25/03/15.
 */
public class PersonCollectionAggregationStrategy extends AbstractListAggregationStrategy<Person> {
    @Override
    public Person getValue(Exchange exchange) {
        return exchange.getIn().getBody(Person.class);
    }
}
