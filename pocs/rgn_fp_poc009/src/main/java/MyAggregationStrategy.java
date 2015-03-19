import org.apache.camel.Exchange;

/**
 * Created by ragnarokkrr on 19/03/15.
 */
public class MyAggregationStrategy implements org.apache.camel.processor.aggregate.AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            return newExchange;
        }

        String oldBody = oldExchange.getIn().getBody(String.class);
        String newBody = newExchange.getIn().getBody(String.class);

        String body = oldBody + newBody;

        oldExchange.getIn().setBody(body);
        return oldExchange;
    }
}
