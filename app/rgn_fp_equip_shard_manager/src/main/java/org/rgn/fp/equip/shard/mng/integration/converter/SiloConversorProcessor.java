package org.rgn.fp.equip.shard.mng.integration.converter;

import org.apache.camel.Converter;
import org.rgn.fp.equip.shard.mng.EquipmentVO;
import org.rgn.fp.equip.shard.mng.adapter.City;
import org.rgn.fp.equip.shard.mng.adapter.Equipment;
import org.rgn.fp.equip.shard.mng.adapter.EquipmentModel;

/**
 * SiloConversorProcessor.
 * 
 * @author ragnarokkrr 
 */
@Converter
public class SiloConversorProcessor {

    @Converter
    public static Equipment convertEquipmentManagerToSilo(EquipmentVO equipmentVO) throws Exception {

        City city = new City();
        city.setId(Long.valueOf(equipmentVO.getCityId()));
        city.setName(equipmentVO.getCityName());
        city.setState(equipmentVO.getCityState());

        EquipmentModel equipmentModel = new EquipmentModel();
        equipmentModel.setId(Long.valueOf(equipmentVO.getEquipmentModelId()));
        equipmentModel.setModelCode(equipmentVO.getEquipmentModelCode());
        equipmentModel.setName(equipmentVO.getEquipmentModelName());

        Equipment equipment = new Equipment();
        equipment.setId(Long.valueOf(equipmentVO.getEquipmentId()));
        equipment.setName(equipmentVO.getEquipmentName());
        equipment.setDescription(equipmentVO.getDescription());
        equipment.setCity(city);
        equipment.setEquipmentModel(equipmentModel);

        return equipment;
    }
}
