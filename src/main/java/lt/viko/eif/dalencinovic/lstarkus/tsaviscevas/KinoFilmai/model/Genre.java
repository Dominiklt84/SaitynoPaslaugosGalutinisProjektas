package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Represents movie genre.
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}