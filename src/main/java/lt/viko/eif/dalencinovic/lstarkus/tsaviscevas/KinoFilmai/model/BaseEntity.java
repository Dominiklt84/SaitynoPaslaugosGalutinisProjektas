package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Base entity class that provides ID field for all entities.
 */
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Primary key identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Returns entity identifier.
     *
     * @return entity ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets entity identifier.
     *
     * @param id entity ID
     */
    public void setId(Long id) {
        this.id = id;
    }
}