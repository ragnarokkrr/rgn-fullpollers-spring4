package org.rgn.fp.equip.shard.mng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * EquipmentController.
 *
 * @author ragnarokkrr
 */
@RestController
@RequestMapping("/shard/manager/equipment")
public class EquipmentController {

    private final String POST_EQUIP_QUEUE = "post-equipment";

    @Autowired
    JmsTemplate jmsTemplate;


    @RequestMapping(method = RequestMethod.POST)
    public String post(@RequestBody EquipmentVO input) {
        jmsTemplate.convertAndSend(POST_EQUIP_QUEUE, input);

        System.out.println(input);
        return input.toString();
    }
}
