package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entitity, aprašantis filmo žanrą.
 */
@Entity
@Table(name = "genre")
@AttributeOverride(
        name = "id",
        column = @Column(name = "genre_id")
)
public class Genre extends BaseEntity {

    private String title;

    public Genre() {
    }

    public Genre(String title) {
        this.title = title;
    }

    /**
     * Grąžina žanro pavadinimą.
     *
     * @return žanro pavadinimas
     */
    public String getTitle() {
        return title;
    }

    /**
     * Nustato žanro pavadinimą.
     *
     * @param title žanro pavadinimas
     */
    public void setTitle(String title) {
        this.title = title;
    }
}