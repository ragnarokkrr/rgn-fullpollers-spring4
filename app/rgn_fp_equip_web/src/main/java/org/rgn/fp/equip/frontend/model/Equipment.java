package org.rgn.fp.equip.frontend.model;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Equipment.
 *
 * It a little bit inverted.
 *
 * @author ragnarokkrr
 */
public class Equipment implements Serializable{

    private long id;
    private String name;
    private String description;
    private EquipmentModel model;
    private City city;

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

    public EquipmentModel getModel() {
        return model;
    }

    public void setModel(EquipmentModel model) {
        this.model = model;
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
                Objects.equal(this.description, that.description) &&
                Objects.equal(this.model, that.model) &&
                Objects.equal(this.city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, model, city);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("model", model)
                .add("city", city)
                .toString();
    }
}
