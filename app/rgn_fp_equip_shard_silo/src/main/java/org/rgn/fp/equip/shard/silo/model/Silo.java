package org.rgn.fp.equip.shard.silo.model;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Silo.
 *
 * @author ragnarokkrr
 */
@Entity
public class Silo implements Serializable{
    private long id;
    String name;
    String description;
    String state;

    @Id
    @Column(name = "id", unique = true)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Silo that = (Silo) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.description, that.description) &&
                Objects.equal(this.state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, state);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("state", state)
                .toString();
    }
}
