package two.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import two.microservice.model.Postulation;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostulationRepository extends JpaRepository<Postulation, Long> {

    List<Postulation> findByDiplomateId(Long diplomateId);

    Optional<Object> findByIdAndDiplomateId(Long postulationId, Long diplomateId);
}
