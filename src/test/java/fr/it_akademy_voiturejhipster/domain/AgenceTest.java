package fr.it_akademy_voiturejhipster.domain;

import static fr.it_akademy_voiturejhipster.domain.AgenceTestSamples.*;
import static fr.it_akademy_voiturejhipster.domain.CarTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy_voiturejhipster.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AgenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agence.class);
        Agence agence1 = getAgenceSample1();
        Agence agence2 = new Agence();
        assertThat(agence1).isNotEqualTo(agence2);

        agence2.setId(agence1.getId());
        assertThat(agence1).isEqualTo(agence2);

        agence2 = getAgenceSample2();
        assertThat(agence1).isNotEqualTo(agence2);
    }

    @Test
    void carsTest() throws Exception {
        Agence agence = getAgenceRandomSampleGenerator();
        Car carBack = getCarRandomSampleGenerator();

        agence.addCars(carBack);
        assertThat(agence.getCars()).containsOnly(carBack);
        assertThat(carBack.getAgence()).isEqualTo(agence);

        agence.removeCars(carBack);
        assertThat(agence.getCars()).doesNotContain(carBack);
        assertThat(carBack.getAgence()).isNull();

        agence.cars(new HashSet<>(Set.of(carBack)));
        assertThat(agence.getCars()).containsOnly(carBack);
        assertThat(carBack.getAgence()).isEqualTo(agence);

        agence.setCars(new HashSet<>());
        assertThat(agence.getCars()).doesNotContain(carBack);
        assertThat(carBack.getAgence()).isNull();
    }
}
