package fr.it_akademy_voiturejhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Mechanic.
 */
@Entity
@Table(name = "mechanic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Mechanic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "motor")
    private String motor;

    @Column(name = "power")
    private Integer power;

    @Column(name = "km")
    private Integer km;

    @JsonIgnoreProperties(value = { "mechanic", "options" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "mechanic")
    private Car car;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mechanic id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotor() {
        return this.motor;
    }

    public Mechanic motor(String motor) {
        this.setMotor(motor);
        return this;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public Integer getPower() {
        return this.power;
    }

    public Mechanic power(Integer power) {
        this.setPower(power);
        return this;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getKm() {
        return this.km;
    }

    public Mechanic km(Integer km) {
        this.setKm(km);
        return this;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        if (this.car != null) {
            this.car.setMechanic(null);
        }
        if (car != null) {
            car.setMechanic(this);
        }
        this.car = car;
    }

    public Mechanic car(Car car) {
        this.setCar(car);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mechanic)) {
            return false;
        }
        return getId() != null && getId().equals(((Mechanic) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mechanic{" +
            "id=" + getId() +
            ", motor='" + getMotor() + "'" +
            ", power=" + getPower() +
            ", km=" + getKm() +
            "}";
    }
}
