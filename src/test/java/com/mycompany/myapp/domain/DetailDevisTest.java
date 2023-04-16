package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailDevisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailDevis.class);
        DetailDevis detailDevis1 = new DetailDevis();
        detailDevis1.setId(1L);
        DetailDevis detailDevis2 = new DetailDevis();
        detailDevis2.setId(detailDevis1.getId());
        assertThat(detailDevis1).isEqualTo(detailDevis2);
        detailDevis2.setId(2L);
        assertThat(detailDevis1).isNotEqualTo(detailDevis2);
        detailDevis1.setId(null);
        assertThat(detailDevis1).isNotEqualTo(detailDevis2);
    }
}
