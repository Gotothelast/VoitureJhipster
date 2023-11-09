package fr.it_akademy_voiturejhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy_voiturejhipster.domain.Mechanic} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MechanicDTO implements Serializable {

    private Long id;

    private String motor;

    private Integer power;

    private Integer km;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MechanicDTO)) {
            return false;
        }

        MechanicDTO mechanicDTO = (MechanicDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mechanicDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MechanicDTO{" +
            "id=" + getId() +
            ", motor='" + getMotor() + "'" +
            ", power=" + getPower() +
            ", km=" + getKm() +
            "}";
    }
}
