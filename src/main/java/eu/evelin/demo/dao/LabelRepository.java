package eu.evelin.demo.dao;

import eu.evelin.demo.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {

    Label findOneById(long id);
}
