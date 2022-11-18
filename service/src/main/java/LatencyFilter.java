import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LatencyFilter {

    final Timer timer;

    @Inject
    public LatencyFilter(MeterRegistry meterRegistry) {
        timer = Timer.builder("requests_latency")
                .description("Request latency seconds")
                .register(meterRegistry);
    }

    @RouteFilter
    public void measureLatency(RoutingContext routingContext) {
        final Timer.Sample sample = Timer.start();
        routingContext.response().endHandler(event -> {
            sample.stop(timer);
        });
        routingContext.next();
    }
}
