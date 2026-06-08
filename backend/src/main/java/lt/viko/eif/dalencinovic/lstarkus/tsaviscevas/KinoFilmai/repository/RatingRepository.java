package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Saugykla, skirta filmų vertinimų duomenų valdymui.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
