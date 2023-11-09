package fr.it_akademy_voiturejhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "car_name")
    private String carName;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "car_reference")
    private String carReference;

    @Column(name = "car_year")
    private Integer carYear;

    @Column(name = "car_price")
    private Integer carPrice;

    @JsonIgnoreProperties(value = { "car" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Mechanic mechanic;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "car" }, allowSetters = true)
    private Set<Option> options = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cars" }, allowSetters = true)
    private Agence agence;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Car id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarName() {
        return this.carName;
    }

    public Car carName(String carName) {
        this.setCarName(carName);
        return this;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarModel() {
        return this.carModel;
    }

    public Car carModel(String carModel) {
        this.setCarModel(carModel);
        return this;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarReference() {
        return this.carReference;
    }

    public Car carReference(String carReference) {
        this.setCarReference(carReference);
        return this;
    }

    public void setCarReference(String carReference) {
        this.carReference = carReference;
    }

    public Integer getCarYear() {
        return this.carYear;
    }

    public Car carYear(Integer carYear) {
        this.setCarYear(carYear);
        return this;
    }

    public void setCarYear(Integer carYear) {
        this.carYear = carYear;
    }

    public Integer getCarPrice() {
        return this.carPrice;
    }

    public Car carPrice(Integer carPrice) {
        this.setCarPrice(carPrice);
        return this;
    }

    public void setCarPrice(Integer carPrice) {
        this.carPrice = carPrice;
    }

    public Mechanic getMechanic() {
        return this.mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Car mechanic(Mechanic mechanic) {
        this.setMechanic(mechanic);
        return this;
    }

    public Set<Option> getOptions() {
        return this.options;
    }

    public void setOptions(Set<Option> options) {
        if (this.options != null) {
            this.options.forEach(i -> i.setCar(null));
        }
        if (options != null) {
            options.forEach(i -> i.setCar(this));
        }
        this.options = options;
    }

    public Car options(Set<Option> options) {
        this.setOptions(options);
        return this;
    }

    public Car addOptions(Option option) {
        this.options.add(option);
        option.setCar(this);
        return this;
    }

    public Car removeOptions(Option option) {
        this.options.remove(option);
        option.setCar(null);
        return this;
    }

    public Agence getAgence() {
        return this.agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Car agence(Agence agence) {
        this.setAgence(agence);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }
        return getId() != null && getId().equals(((Car) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", carName='" + getCarName() + "'" +
            ", carModel='" + getCarModel() + "'" +
            ", carReference='" + getCarReference() + "'" +
            ", carYear=" + getCarYear() +
            ", carPrice=" + getCarPrice() +
            "}";
    }
}
