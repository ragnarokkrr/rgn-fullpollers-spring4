package org.rgn.fp.equip.frontend.model;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;

/**
 * City.
 *
 * @author ragnarokkrr
 */
@Entity
public class City implements Serializable {
    private long id;
    private String name;
    private StateEnum state;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City that = (City) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, state);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("state", state)
                .toString();
    }
}

