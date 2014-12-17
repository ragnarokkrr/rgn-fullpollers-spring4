package org.rgn.fp.equip.shard.mng.integration;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

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
                choice().
                when().groovy("exchange.in.body.cityState == \"RS\"").to("jms:queue:post-equipment-RS").
                when().groovy("exchange.in.body.cityState == \"RJ\"").to("jms:queue:post-equipment-RJ").
                when().groovy("exchange.in.body.cityState == \"SP\"").to("jms:queue:post-equipment-SP").
                otherwise().
                to("jms:queue:post-equipment-undefined");

        from("jms:queue:post-equipment-RS").log(LoggingLevel.INFO, "OUTBOUND RS >>>>>>> ${body}").to("stream:out");
        from("jms:queue:post-equipment-RJ").log(LoggingLevel.INFO, "OUTBOUND RJ >>>>>>> ${body}").to("stream:out");
        from("jms:queue:post-equipment-SP").log(LoggingLevel.INFO, "OUTBOUND SP >>>>>>> ${body}").to("stream:out");
    }
}
