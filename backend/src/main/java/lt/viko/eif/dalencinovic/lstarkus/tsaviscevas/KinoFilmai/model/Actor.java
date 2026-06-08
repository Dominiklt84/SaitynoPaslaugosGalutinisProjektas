package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entitity, aprašantis filmo aktorių.
 */
@Entity
@Table(name = "actor")
@AttributeOverride(
        name = "id",
        column = @Column(name = "actor_id")
)
public class Actor extends BaseEntity {

    private String firstName;

    private String lastName;

    public Actor() {
    }

    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Grąžina aktoriaus vardą.
     *
     * @return aktoriaus vardas
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Grąžina aktoriaus pavardę.
     *
     * @return aktoriaus pavardė
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Nustato aktoriaus vardą.
     *
     * @param firstName aktoriaus vardas
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Nustato aktoriaus pavardę.
     *
     * @param lastName aktoriaus pavardė
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}