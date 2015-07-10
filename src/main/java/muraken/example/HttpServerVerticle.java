package muraken.example;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HttpServerVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

  public void start() {
    logger.info("start.");

    vertx.createHttpServer().requestHandler(req -> {
      logger.info("Received a http request");

      vertx.eventBus().send("eb.sqltemplate", "findAll req", ar -> {
        if (ar.succeeded()) {
          logger.info("Received reply: " + ar.result().body());
          req.response().end((String) ar.result().body());
        }
      });
    }).listen(8080);

    logger.debug("Started HttpServer(port=8080).");
  }
}
