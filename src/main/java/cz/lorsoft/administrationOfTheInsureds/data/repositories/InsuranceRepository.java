package cz.lorsoft.administrationOfTheInsureds.data.repositories;

import cz.lorsoft.administrationOfTheInsureds.data.entities.InsuranceEntity;
import org.springframework.data.repository.CrudRepository;

public interface InsuranceRepository extends CrudRepository<InsuranceEntity, Long> {
}
