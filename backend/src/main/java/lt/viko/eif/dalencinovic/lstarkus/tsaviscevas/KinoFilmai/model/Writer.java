package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entitity, aprašantis filmo scenaristą.
 */
@Entity
@Table(name = "writer")
@AttributeOverride(
        name = "id",
        column = @Column(name = "writer_id")
)
public class Writer extends BaseEntity {

    private String firstName;

    private String lastName;

    public Writer() {
    }

    public Writer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Grąžina scenaristo vardą.
     *
     * @return scenaristo vardas
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Grąžina scenaristo pavardę.
     *
     * @return scenaristo pavardė
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Nustato scenaristo vardą.
     *
     * @param firstName scenaristo vardas
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Nustato scenaristo pavardę.
     *
     * @param lastName scenaristo pavardė
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}