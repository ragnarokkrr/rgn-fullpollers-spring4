package org.rgn.fp.equip.shard.silo.model;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Equipment.
 *
 * @author ragnarokkrr
 */
@Entity
public class Equipment implements Serializable {
    private long id;
    private String name;
    private City city;

    @Id
/*
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
*/
    //@GeneratedValue(strategy = GenerationType.AUTO)
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Equipment that = (Equipment) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, city);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("city", city)
                .toString();
    }
}
