package fr.it_akademy_voiturejhipster.domain;

import static fr.it_akademy_voiturejhipster.domain.CarTestSamples.*;
import static fr.it_akademy_voiturejhipster.domain.MechanicTestSamples.*;
import static fr.it_akademy_voiturejhipster.domain.OptionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy_voiturejhipster.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car.class);
        Car car1 = getCarSample1();
        Car car2 = new Car();
        assertThat(car1).isNotEqualTo(car2);

        car2.setId(car1.getId());
        assertThat(car1).isEqualTo(car2);

        car2 = getCarSample2();
        assertThat(car1).isNotEqualTo(car2);
    }

    @Test
    void mechanicTest() throws Exception {
        Car car = getCarRandomSampleGenerator();
        Mechanic mechanicBack = getMechanicRandomSampleGenerator();

        car.setMechanic(mechanicBack);
        assertThat(car.getMechanic()).isEqualTo(mechanicBack);

        car.mechanic(null);
        assertThat(car.getMechanic()).isNull();
    }

    @Test
    void optionsTest() throws Exception {
        Car car = getCarRandomSampleGenerator();
        Option optionBack = getOptionRandomSampleGenerator();

        car.addOptions(optionBack);
        assertThat(car.getOptions()).containsOnly(optionBack);
        assertThat(optionBack.getCar()).isEqualTo(car);

        car.removeOptions(optionBack);
        assertThat(car.getOptions()).doesNotContain(optionBack);
        assertThat(optionBack.getCar()).isNull();

        car.options(new HashSet<>(Set.of(optionBack)));
        assertThat(car.getOptions()).containsOnly(optionBack);
        assertThat(optionBack.getCar()).isEqualTo(car);

        car.setOptions(new HashSet<>());
        assertThat(car.getOptions()).doesNotContain(optionBack);
        assertThat(optionBack.getCar()).isNull();
    }
}
