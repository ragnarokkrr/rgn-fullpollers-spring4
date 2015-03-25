package org.ragna.camel.proc.model;

/**
 * Created by nilseu.padilha on 25/03/15.
 */
public class Person {

    public static final Person DUMMY = new Person(-1, "Dummy");

    private long id;
    private String name;

    public Person(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Person person = (Person) o;

        if (id != person.id)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }
}
