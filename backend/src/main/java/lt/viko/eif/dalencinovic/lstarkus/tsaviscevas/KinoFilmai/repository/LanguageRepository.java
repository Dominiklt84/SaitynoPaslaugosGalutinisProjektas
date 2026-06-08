package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Saugykla, skirta kalbų duomenų valdymui.
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findByName(String title);
}