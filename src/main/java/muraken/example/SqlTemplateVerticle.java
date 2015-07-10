package muraken.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import muraken.example.entity.Emp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqlTemplateVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(SqlTemplateVerticle.class);

  @Autowired
  private SqlTemplateService sqlTemplateService;

  @Autowired
  private ObjectMapper jsonMapper;

  public void start() {
    logger.info("start.");

    vertx.eventBus().consumer("eb.sqltemplate", message -> {
      logger.info("Received a message: " + message.body());

      List<Emp> emps = this.sqlTemplateService.findAll();

      try {
        String json =this.jsonMapper.writeValueAsString(emps);
        message.reply(json);
      } catch (JsonProcessingException e) {
        logger.error("convert error.", e);
      }
    });
  }
}
