package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Actor entity.
 * Provides CRUD operations for Actor.
 */
@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Optional<Actor> findByFirstNameAndLastName(String firstName, String lastName);
}