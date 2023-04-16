package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgenceBanqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgenceBanque.class);
        AgenceBanque agenceBanque1 = new AgenceBanque();
        agenceBanque1.setId(1L);
        AgenceBanque agenceBanque2 = new AgenceBanque();
        agenceBanque2.setId(agenceBanque1.getId());
        assertThat(agenceBanque1).isEqualTo(agenceBanque2);
        agenceBanque2.setId(2L);
        assertThat(agenceBanque1).isNotEqualTo(agenceBanque2);
        agenceBanque1.setId(null);
        assertThat(agenceBanque1).isNotEqualTo(agenceBanque2);
    }
}
