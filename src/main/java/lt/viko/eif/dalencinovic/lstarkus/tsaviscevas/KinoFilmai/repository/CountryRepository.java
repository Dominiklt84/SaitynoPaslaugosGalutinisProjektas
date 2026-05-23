package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Country entity.
 * Provides CRUD operations for Country.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String name);
}
