package fr.it_akademy_voiturejhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy_voiturejhipster.domain.Agence} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgenceDTO implements Serializable {

    private Long id;

    private String nameAgence;

    private String adressAgence;

    private Long telAgence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAgence() {
        return nameAgence;
    }

    public void setNameAgence(String nameAgence) {
        this.nameAgence = nameAgence;
    }

    public String getAdressAgence() {
        return adressAgence;
    }

    public void setAdressAgence(String adressAgence) {
        this.adressAgence = adressAgence;
    }

    public Long getTelAgence() {
        return telAgence;
    }

    public void setTelAgence(Long telAgence) {
        this.telAgence = telAgence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgenceDTO)) {
            return false;
        }

        AgenceDTO agenceDTO = (AgenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, agenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgenceDTO{" +
            "id=" + getId() +
            ", nameAgence='" + getNameAgence() + "'" +
            ", adressAgence='" + getAdressAgence() + "'" +
            ", telAgence=" + getTelAgence() +
            "}";
    }
}
