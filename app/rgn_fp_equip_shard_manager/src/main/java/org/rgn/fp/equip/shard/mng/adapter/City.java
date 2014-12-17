package org.rgn.fp.equip.shard.mng.adapter;

import com.google.common.base.Objects;

/**
 * City.
 *
 * @author ragnarokkrr
 */
public class City {
    private long id;
    private String name;
    private String state;

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
