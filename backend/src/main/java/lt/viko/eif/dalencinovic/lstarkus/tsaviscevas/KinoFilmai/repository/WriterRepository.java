package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Saugykla, skirta scenaristų duomenų valdymui.
 */
@Repository
public interface WriterRepository extends JpaRepository<Writer, Long> {
    Optional<Writer> findByFirstNameAndLastName(String firstName, String lastName);
}