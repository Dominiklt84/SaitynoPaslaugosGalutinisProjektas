package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Rated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Saugykla, skirta amžiaus reitingų duomenų valdymui.
 */
@Repository
public interface RatedRepository extends JpaRepository<Rated, Long> {
    Optional<Rated> findByTitle(String title);

}