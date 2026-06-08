package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entitity, aprašantis filmo režisierių.
 */
@Entity
@Table(name = "director")
@AttributeOverride(
        name = "id",
        column = @Column(name = "director_id")
)
public class Director extends BaseEntity {

    private String firstName;

    private String lastName;

    public Director() {
    }

    public Director(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Grąžina režisieriaus vardą.
     *
     * @return režisieriaus vardas
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Grąžina režisieriaus pavardę.
     *
     * @return režisieriaus pavardė
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Nustato režisieriaus vardą.
     *
     * @param firstName režisieriaus vardas
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Nustato režisieriaus pavardę.
     *
     * @param lastName režisieriaus pavardė
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}