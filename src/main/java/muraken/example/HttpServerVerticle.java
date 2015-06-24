package muraken.example;

import io.vertx.core.AbstractVerticle;
import org.springframework.stereotype.Component;

@Component
public class HttpServerVerticle extends AbstractVerticle {

  public void start() {
    console("start.");

    vertx.createHttpServer().requestHandler(req -> {
      console("Received a http request");

      vertx.eventBus().send("eb.sqltemplate", "findAll req", ar -> {
        if (ar.succeeded()) {
          console("Received reply: " + ar.result().body());
          req.response().end((String) ar.result().body());
        }
      });
    }).listen(8080);
  }

  private void console(String message) {
    System.out.println("[" + Thread.currentThread().getName() + " ] " + getClass().getName() + " : " + message);
  }
}
