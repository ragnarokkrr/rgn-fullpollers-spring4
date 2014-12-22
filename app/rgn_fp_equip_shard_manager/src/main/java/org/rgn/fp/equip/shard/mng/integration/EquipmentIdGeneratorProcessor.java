package org.rgn.fp.equip.shard.mng.integration;

import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rgn.fp.equip.shard.mng.EquipmentVO;

/**
 * IdGeneratorProcessor.
 *
 * @author ragnarokkrr
 */
class EquipmentIdGeneratorProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        long newId = UUID.randomUUID().getLeastSignificantBits();
        EquipmentVO equipmentVO = exchange.getIn().getBody(EquipmentVO.class);
        equipmentVO.setEquipmentId(Long.toString(newId));
    }
}
