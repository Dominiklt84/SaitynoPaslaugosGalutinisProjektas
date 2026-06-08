package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Saugykla, skirta režisierių duomenų valdymui.
 */
@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    Optional<Director> findByFirstNameAndLastName(String firstName, String lastName);
}