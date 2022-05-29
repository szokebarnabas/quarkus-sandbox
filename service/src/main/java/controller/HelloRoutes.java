package controller;

import io.quarkus.vertx.web.Route;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloRoutes {

    @Route(path = "/say-my-name/:name", methods = Route.HttpMethod.GET)
    public void handle(RoutingContext rc) {
        rc.response().end("Hello " + rc.request().getParam("name"));
    }
}
