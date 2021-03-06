package org.rgn.fp.equip.shard.mng.adapter;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Equipment.
 *
 * @author ragnarokkrr
 */
public class Equipment implements Serializable {
    private long id;
    private String name;
    private String description;
    private City city;
    private EquipmentModel equipmentModel;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public EquipmentModel getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(EquipmentModel equipmentModel) {
        this.equipmentModel = equipmentModel;
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
