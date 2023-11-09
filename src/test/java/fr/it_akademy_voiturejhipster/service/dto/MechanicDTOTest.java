package fr.it_akademy_voiturejhipster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy_voiturejhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MechanicDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MechanicDTO.class);
        MechanicDTO mechanicDTO1 = new MechanicDTO();
        mechanicDTO1.setId(1L);
        MechanicDTO mechanicDTO2 = new MechanicDTO();
        assertThat(mechanicDTO1).isNotEqualTo(mechanicDTO2);
        mechanicDTO2.setId(mechanicDTO1.getId());
        assertThat(mechanicDTO1).isEqualTo(mechanicDTO2);
        mechanicDTO2.setId(2L);
        assertThat(mechanicDTO1).isNotEqualTo(mechanicDTO2);
        mechanicDTO1.setId(null);
        assertThat(mechanicDTO1).isNotEqualTo(mechanicDTO2);
    }
}
