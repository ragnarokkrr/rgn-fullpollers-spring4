package org.ragna.camel.proc;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.ragna.camel.proc.model.Person;

/**
 * Created by nilseu.padilha on 25/03/15.
 */
public class PersonAggregationStrategy implements AggregationStrategy {


    public Exchange aggregateOld(Exchange oldExchange, Exchange newExchange) {

        if (oldExchange == null) {
            return newExchange;
        }
        System.out.println("OLD EXCHANGE ====>" + oldExchange.getIn().getBody());
        System.out.println("NEW EXCHANGE ====>" + newExchange.getIn().getBody());

        //List<Person> people = Arrays.asList(
        //        oldExchange.getIn().getBody(Person.class)
        //        , newExchange.getIn().getBody(Person.class));


        List<Person> people = new ArrayList<Person>(2);
        people.add(oldExchange.getIn().getBody(Person.class));
        people.add(newExchange.getIn().getBody(Person.class));


        oldExchange.getIn().setBody(people);
        return oldExchange;
    }

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            List<Person> people = new ArrayList<>();
            Person newPeople = newExchange.getIn().getBody(Person.class);
            people.add(newPeople);
            newExchange.getIn().setBody(people);
            return newExchange;
        }
        List<Person> people = oldExchange.getIn().getBody(List.class);
        Person newOutPerson = newExchange.getIn().getBody(Person.class);
        people.add(newOutPerson);
        oldExchange.getIn().setBody(people);
        return oldExchange;
    }

    public Exchange aggregateOld2(Exchange oldExchange, Exchange newExchange) {
        Message newIn = newExchange.getIn();
        Object newBody = newIn.getBody();
        ArrayList list = null;
        if (oldExchange == null) {
            list = new ArrayList();
            list.add(newBody);
            newIn.setBody(list);
            return newExchange;
        } else {
            Message in = oldExchange.getIn();
            list = in.getBody(ArrayList.class);
            list.add(newBody);
            return oldExchange;
        }
    }
}
