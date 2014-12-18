package org.rgn.fp.equip.shard.mng;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * EquipmentVO.
 *
 * @author ragnarokkrr
 */
public class EquipmentVO implements Serializable {

    private String name;
    private String description;
    private String cityId;
    private String cityName;
    private String cityState;
    private String equipmentId;
    private String equipmentName;
    private String equipmentModelCode;


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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityState() {
        return cityState;
    }

    public void setCityState(String cityState) {
        this.cityState = cityState;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentModelCode() {
        return equipmentModelCode;
    }

    public void setEquipmentModelCode(String equipmentmodelCode) {
        this.equipmentModelCode = equipmentmodelCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquipmentVO that = (EquipmentVO) o;

        return Objects.equal(this.name, that.name) &&
                Objects.equal(this.description, that.description) &&
                Objects.equal(this.cityId, that.cityId) &&
                Objects.equal(this.cityName, that.cityName) &&
                Objects.equal(this.cityState, that.cityState) &&
                Objects.equal(this.equipmentId, that.equipmentId) &&
                Objects.equal(this.equipmentName, that.equipmentName) &&
                Objects.equal(this.equipmentModelCode, that.equipmentModelCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, description, cityId, cityName, cityState, equipmentId,
                equipmentName, equipmentModelCode);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("description", description)
                .add("cityId", cityId)
                .add("cityName", cityName)
                .add("cityState", cityState)
                .add("equipmentId", equipmentId)
                .add("equipmentName", equipmentName)
                .add("equipmentModelCode", equipmentModelCode)
                .toString();
    }
}
