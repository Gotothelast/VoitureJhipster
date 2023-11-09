package fr.it_akademy_voiturejhipster.domain;

import static fr.it_akademy_voiturejhipster.domain.CarTestSamples.*;
import static fr.it_akademy_voiturejhipster.domain.MechanicTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy_voiturejhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MechanicTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mechanic.class);
        Mechanic mechanic1 = getMechanicSample1();
        Mechanic mechanic2 = new Mechanic();
        assertThat(mechanic1).isNotEqualTo(mechanic2);

        mechanic2.setId(mechanic1.getId());
        assertThat(mechanic1).isEqualTo(mechanic2);

        mechanic2 = getMechanicSample2();
        assertThat(mechanic1).isNotEqualTo(mechanic2);
    }

    @Test
    void carTest() throws Exception {
        Mechanic mechanic = getMechanicRandomSampleGenerator();
        Car carBack = getCarRandomSampleGenerator();

        mechanic.setCar(carBack);
        assertThat(mechanic.getCar()).isEqualTo(carBack);
        assertThat(carBack.getMechanic()).isEqualTo(mechanic);

        mechanic.car(null);
        assertThat(mechanic.getCar()).isNull();
        assertThat(carBack.getMechanic()).isNull();
    }
}
