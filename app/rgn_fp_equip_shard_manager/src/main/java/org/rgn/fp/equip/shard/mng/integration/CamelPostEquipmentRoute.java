package org.rgn.fp.equip.shard.mng.integration;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.rgn.fp.equip.shard.mng.EquipmentVO;
import org.rgn.fp.equip.shard.mng.adapter.City;
import org.rgn.fp.equip.shard.mng.adapter.Equipment;
import org.rgn.fp.equip.shard.mng.adapter.EquipmentModel;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * CamelPostEquipmentRoute.
 *
 * @author ragnarokkrr
 */
@Component
public class CamelPostEquipmentRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("jms:queue:post-equipment").
                log(LoggingLevel.INFO, "INBOUND>> ${body}").
                process(new IdGeneratorProcessor()).
                choice().
                when().groovy("exchange.in.body.cityState == \"RS\"").to("jms:queue:post-equipment-RS").
                when().groovy("exchange.in.body.cityState == \"RJ\"").to("jms:queue:post-equipment-RJ").
                when().groovy("exchange.in.body.cityState == \"SP\"").to("jms:queue:post-equipment-SP").
                otherwise().
                to("jms:queue:post-equipment-undefined");

        from("jms:queue:post-equipment-RS")
                .log(LoggingLevel.INFO, "OUTBOUND RS >>>>>>> ${body}")
                .process(new ConversorProcessor()).process(new SiloEndpointProcessor());
        from("jms:queue:post-equipment-RJ").log(LoggingLevel.INFO, "OUTBOUND RJ >>>>>>> ${body}").to("stream:out");
        from("jms:queue:post-equipment-SP").log(LoggingLevel.INFO, "OUTBOUND SP >>>>>>> ${body}").to("stream:out");
    }
}

/**
 * IdGeneratorProcessor.
 *
 * @author ragnarokkrr
 */
class IdGeneratorProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        long newId = UUID.randomUUID().getLeastSignificantBits();
        EquipmentVO equipmentVO = exchange.getIn().getBody(EquipmentVO.class);
        equipmentVO.setEquipmentId(Long.toString(newId));
    }
}

/**
 * ConversorProcessor.
 *
 * @author ragnarokkrr
 */
class ConversorProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        EquipmentVO equipmentVO = exchange.getIn().getBody(EquipmentVO.class);

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

        exchange.getIn().setBody(equipment);
    }
}

/**
 * SiloEndpointProcessor.
 *
 * @author ragnarokkrr
 */
class SiloEndpointProcessor implements Processor {
    final Map<String, String> silos;

    SiloEndpointProcessor() {
        silos = new HashMap<>();
        silos.put("RS", "http://localhost:8090/rest/equipments");
        silos.put("RJ", "http://localhost:8092/rest/equipments");
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Equipment equipment = exchange.getIn().getBody(Equipment.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Equipment> requestEntity = new HttpEntity<Equipment>(equipment, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8090/rest/equipments", HttpMethod.POST, requestEntity, String.class);
    }
}