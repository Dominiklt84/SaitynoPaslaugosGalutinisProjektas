package lt.viko.eif.dalencinovic.lstarkus.tsaviscevas.KinoFilmai.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entitity, aprašantis filmo kilmės šalį.
 */
@Entity
@Table(name = "country")
@AttributeOverride(
        name = "id",
        column = @Column(name = "country_id")
)
public class Country extends BaseEntity {

    private String name;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    /**
     * Grąžina šalies pavadinimą.
     *
     * @return šalies pavadinimas
     */
    public String getName() {
        return name;
    }

    /**
     * Nustato šalies pavadinimą.
     *
     * @param name šalies pavadinimas
     */
    public void setName(String name) {
        this.name = name;
    }
}