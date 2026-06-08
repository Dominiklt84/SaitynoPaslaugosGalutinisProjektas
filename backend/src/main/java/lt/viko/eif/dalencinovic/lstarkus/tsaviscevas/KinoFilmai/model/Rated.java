package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.*;

/**
 * Entitity, aprašantis filmo amžiaus cenzą.
 */
@Entity
@Table(name = "rated")
@AttributeOverride(
        name = "id",
        column = @Column(name = "rated_id")
)
public class Rated extends BaseEntity {

    private String title;

    public Rated() {
    }

    public Rated(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}