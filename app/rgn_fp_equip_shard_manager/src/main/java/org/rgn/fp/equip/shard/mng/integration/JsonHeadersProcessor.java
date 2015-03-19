package org.rgn.fp.equip.shard.mng.integration;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * JsonHeadersProcessor.
 *
 * @author ragnarokkrr
 */
public class JsonHeadersProcessor implements  Processor{
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeader("Accept", "application/json");
        exchange.getIn().setHeader("Content-Type", "application/json");
    }
}
