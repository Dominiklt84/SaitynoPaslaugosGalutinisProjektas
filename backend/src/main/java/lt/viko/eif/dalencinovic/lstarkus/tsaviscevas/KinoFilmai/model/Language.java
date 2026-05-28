package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Represents movie language.
 */
@Entity
@Table(name = "language")
@AttributeOverride(
        name = "id",
        column = @Column(name = "language_id")
)
public class Language extends BaseEntity {

    private String name;

    public Language() {
    }

    public Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}