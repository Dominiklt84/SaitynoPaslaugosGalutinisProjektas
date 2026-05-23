package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Genre entity.
 * Provides CRUD operations for Genre.
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}