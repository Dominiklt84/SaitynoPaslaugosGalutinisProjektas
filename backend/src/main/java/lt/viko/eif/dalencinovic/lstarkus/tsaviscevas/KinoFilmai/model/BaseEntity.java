package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Bazinė entity klasė.
 * Suteikia unikalų identifikatorių visiems paveldintiems objektams.
 */
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Unikalus entity identifikatorius.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Grąžina entity identifikatorių.
     *
     * @return entity identifikatorius
     */
    public Long getId() {
        return id;
    }

    /**
     * Nustato entity identifikatorių.
     *
     * @param id entity identifikatorius
     */
    public void setId(Long id) {
        this.id = id;
    }
}