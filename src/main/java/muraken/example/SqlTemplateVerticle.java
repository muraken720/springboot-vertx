package muraken.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import muraken.example.entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqlTemplateVerticle extends AbstractVerticle {

  @Autowired
  private SqlTemplateService sqlTemplateService;

  @Autowired
  private ObjectMapper jsonMapper;

  public void start() throws JsonProcessingException {
    console("start.");

    vertx.eventBus().consumer("eb.sqltemplate", message -> {
      console("Received a message: " + message.body());

      List<Emp> emps = this.sqlTemplateService.findAll();

      try {
        String json =this.jsonMapper.writeValueAsString(emps);
        message.reply(json);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    });
  }

  private void console(String message) {
    System.out.println("[" + Thread.currentThread().getName() + " ] " + getClass().getName() + " : " + message);
  }
}
