package fr.it_akademy_voiturejhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Agence.
 */
@Entity
@Table(name = "agence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name_agence")
    private String nameAgence;

    @Column(name = "adress_agence")
    private String adressAgence;

    @Column(name = "tel_agence")
    private Long telAgence;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agence")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mechanic", "options", "agence" }, allowSetters = true)
    private Set<Car> cars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Agence id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAgence() {
        return this.nameAgence;
    }

    public Agence nameAgence(String nameAgence) {
        this.setNameAgence(nameAgence);
        return this;
    }

    public void setNameAgence(String nameAgence) {
        this.nameAgence = nameAgence;
    }

    public String getAdressAgence() {
        return this.adressAgence;
    }

    public Agence adressAgence(String adressAgence) {
        this.setAdressAgence(adressAgence);
        return this;
    }

    public void setAdressAgence(String adressAgence) {
        this.adressAgence = adressAgence;
    }

    public Long getTelAgence() {
        return this.telAgence;
    }

    public Agence telAgence(Long telAgence) {
        this.setTelAgence(telAgence);
        return this;
    }

    public void setTelAgence(Long telAgence) {
        this.telAgence = telAgence;
    }

    public Set<Car> getCars() {
        return this.cars;
    }

    public void setCars(Set<Car> cars) {
        if (this.cars != null) {
            this.cars.forEach(i -> i.setAgence(null));
        }
        if (cars != null) {
            cars.forEach(i -> i.setAgence(this));
        }
        this.cars = cars;
    }

    public Agence cars(Set<Car> cars) {
        this.setCars(cars);
        return this;
    }

    public Agence addCars(Car car) {
        this.cars.add(car);
        car.setAgence(this);
        return this;
    }

    public Agence removeCars(Car car) {
        this.cars.remove(car);
        car.setAgence(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agence)) {
            return false;
        }
        return getId() != null && getId().equals(((Agence) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agence{" +
            "id=" + getId() +
            ", nameAgence='" + getNameAgence() + "'" +
            ", adressAgence='" + getAdressAgence() + "'" +
            ", telAgence=" + getTelAgence() +
            "}";
    }
}
