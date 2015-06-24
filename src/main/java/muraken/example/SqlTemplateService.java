package muraken.example;

import muraken.example.entity.Emp;
import ninja.cero.sqltemplate.core.SqlTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqlTemplateService {
  @Autowired
  SqlTemplate template;

  public List<Emp> findAll() {
    return template.forList("sql/selectAll.sql", Emp.class);
  }
}
