package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.repository;

import lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model.MovieView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for MovieView entity.
 * Provides CRUD operations for MovieView.
 */
@Repository
public interface MovieViewRepository extends JpaRepository<MovieView, Long> {
}