package two.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import two.microservice.model.Diplomate;

@Repository
public interface DiplomateRepository extends JpaRepository<Diplomate, Long> {

}
