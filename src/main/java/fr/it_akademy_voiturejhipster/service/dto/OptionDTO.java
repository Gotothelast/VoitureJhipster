package fr.it_akademy_voiturejhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy_voiturejhipster.domain.Option} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OptionDTO implements Serializable {

    private Long id;

    private String nameOption;

    private Integer priceOption;

    private CarDTO car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOption() {
        return nameOption;
    }

    public void setNameOption(String nameOption) {
        this.nameOption = nameOption;
    }

    public Integer getPriceOption() {
        return priceOption;
    }

    public void setPriceOption(Integer priceOption) {
        this.priceOption = priceOption;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionDTO)) {
            return false;
        }

        OptionDTO optionDTO = (OptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, optionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionDTO{" +
            "id=" + getId() +
            ", nameOption='" + getNameOption() + "'" +
            ", priceOption=" + getPriceOption() +
            ", car=" + getCar() +
            "}";
    }
}
