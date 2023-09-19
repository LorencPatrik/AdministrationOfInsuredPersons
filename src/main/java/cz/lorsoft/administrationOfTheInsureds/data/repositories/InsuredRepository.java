package cz.lorsoft.administrationOfTheInsureds.data.repositories;

import cz.lorsoft.administrationOfTheInsureds.data.entities.InsuredEntity;
import org.springframework.data.repository.CrudRepository;

public interface InsuredRepository extends CrudRepository<InsuredEntity, Long> {
}
