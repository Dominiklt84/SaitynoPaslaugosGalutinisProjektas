package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Represents movie age rating.
 */
@Entity
@Table(name = "rated")
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