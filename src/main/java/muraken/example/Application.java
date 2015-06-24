package muraken.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import ninja.cero.sqltemplate.core.SqlTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

  @Bean
  SqlTemplate sqlTemplate(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new SqlTemplate(jdbcTemplate, namedParameterJdbcTemplate);
  }

  @Bean
  ObjectMapper jsonMapper() {
    return new ObjectMapper();
  }

  @Bean
  SqlTemplateVerticle sqlTemplateVerticle() {
    return new SqlTemplateVerticle();
  }

  @Bean
  HttpServerVerticle httpServerVerticle() {
    return new HttpServerVerticle();
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

    final Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(context.getBean(SqlTemplateVerticle.class), new DeploymentOptions().setWorker(true));
    vertx.deployVerticle(context.getBean(HttpServerVerticle.class));

  }
}
