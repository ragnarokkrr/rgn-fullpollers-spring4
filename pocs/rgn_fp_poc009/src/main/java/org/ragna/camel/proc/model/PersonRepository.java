package org.ragna.camel.proc.model;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by nilseu.padilha on 25/03/15.
 */
public class PersonRepository {
    private Map<Long, Person> personMap;

    public PersonRepository() {
        this.personMap = getPersonsSource().stream().collect(toMap(Person::getId, p -> p));
    }

    public List<Person> findAll() {
        return Collections.unmodifiableList(getPersonsSource());
    }

    public List<Long> findAllIds() {
        return Collections.unmodifiableList(personMap.keySet().stream().collect(toList()));
    }

    public Person findById(Long id) {
        return personMap.getOrDefault(id, Person.DUMMY);
    }

    private List<Person> getPersonsSource() {
        return Arrays.asList(
                new Person(1, "John Connor")
                , new Person(2, "Sarah Connor")
                , new Person(3, "Arnold Schwarzenegger")
                , new Person(4, "Sylvester Stallone")
                , new Person(5, "Chuck Norris")
                , new Person(6, "Jean-Claude van Dame")
                , new Person(7, "Pedro Barusco")
                , new Person(8, "Graça Foster")
                , new Person(9, "Lula X9")
                );
    }
}
