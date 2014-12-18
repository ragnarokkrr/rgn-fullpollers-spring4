package org.rgn.fp.equip.frontend.model;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * EquipmentType;
 *
 * @author ragnarokkrr
 */
@Entity
public class EquipmentModel implements Serializable {
    private long id;
    private String name;
    private String modelCode;

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

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquipmentModel that = (EquipmentModel) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.modelCode, that.modelCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, modelCode);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("modelCode", modelCode)
                .toString();
    }
}
