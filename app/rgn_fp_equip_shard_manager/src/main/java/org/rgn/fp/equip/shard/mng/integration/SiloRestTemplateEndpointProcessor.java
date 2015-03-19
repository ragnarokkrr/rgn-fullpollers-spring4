package org.rgn.fp.equip.shard.mng.integration;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.rgn.fp.equip.shard.mng.adapter.Equipment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * SiloEndpointProcessor.
 * 
 * @author ragnarokkrr
 */
@Deprecated

public class SiloRestTemplateEndpointProcessor implements Processor {
    final Map<String, String> silos;

    SiloRestTemplateEndpointProcessor() {
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
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8090/rest/equipments", HttpMethod.POST,
                requestEntity, String.class);
    }
}