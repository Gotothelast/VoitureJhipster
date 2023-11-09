package fr.it_akademy_voiturejhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy_voiturejhipster.domain.Car} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarDTO implements Serializable {

    private Long id;

    private String carName;

    private String carModel;

    private String carReference;

    private Integer carYear;

    private Integer carPrice;

    private MechanicDTO mechanic;

    private AgenceDTO agence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarReference() {
        return carReference;
    }

    public void setCarReference(String carReference) {
        this.carReference = carReference;
    }

    public Integer getCarYear() {
        return carYear;
    }

    public void setCarYear(Integer carYear) {
        this.carYear = carYear;
    }

    public Integer getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(Integer carPrice) {
        this.carPrice = carPrice;
    }

    public MechanicDTO getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicDTO mechanic) {
        this.mechanic = mechanic;
    }

    public AgenceDTO getAgence() {
        return agence;
    }

    public void setAgence(AgenceDTO agence) {
        this.agence = agence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarDTO)) {
            return false;
        }

        CarDTO carDTO = (CarDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, carDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarDTO{" +
            "id=" + getId() +
            ", carName='" + getCarName() + "'" +
            ", carModel='" + getCarModel() + "'" +
            ", carReference='" + getCarReference() + "'" +
            ", carYear=" + getCarYear() +
            ", carPrice=" + getCarPrice() +
            ", mechanic=" + getMechanic() +
            ", agence=" + getAgence() +
            "}";
    }
}
