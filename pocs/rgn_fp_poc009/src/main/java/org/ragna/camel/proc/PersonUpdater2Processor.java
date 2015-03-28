package org.ragna.camel.proc;

import org.apache.camel.Body;
import org.ragna.camel.proc.model.Person;

/**
 * Created by nilseu.padilha on 25/03/15.
 */
public class PersonUpdater2Processor {

    public Person update(@Body Person person) {
        return new Person(person.getId(), person.getName() + " [UPD2]");
    }
}
