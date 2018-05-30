package lynn.VertxEventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerRequest;

public class WebVerticle extends AbstractVerticle {


    private final Logger logger = LoggerFactory.getLogger(WebVerticle.class);

    @Override
    public void start() {
        vertx.createHttpServer()
                .requestHandler(httpRequest -> handleHttpRequest(httpRequest) )
                .listen(8180);
    }

    private void handleHttpRequest(final HttpServerRequest httpRequest) {

        /* Invoke using the event bus. */
        vertx.eventBus().send(Services.HELLO_WORLD.toString(),
                HelloWorldOperations.SAY_HELLO_WORLD.toString(), response -> {

            if (response.succeeded()) {
                /* Send the result from HelloWorldService to the http connection. */
                httpRequest.response().end(response.result().body().toString());
            } else {
                System.out.println("Can't send message to hello service: "  + response.cause());
                httpRequest.response().setStatusCode(500).end(response.cause().getMessage());
            }
        });
    }
}