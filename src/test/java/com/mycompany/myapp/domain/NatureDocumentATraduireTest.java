package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureDocumentATraduireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureDocumentATraduire.class);
        NatureDocumentATraduire natureDocumentATraduire1 = new NatureDocumentATraduire();
        natureDocumentATraduire1.setId(1L);
        NatureDocumentATraduire natureDocumentATraduire2 = new NatureDocumentATraduire();
        natureDocumentATraduire2.setId(natureDocumentATraduire1.getId());
        assertThat(natureDocumentATraduire1).isEqualTo(natureDocumentATraduire2);
        natureDocumentATraduire2.setId(2L);
        assertThat(natureDocumentATraduire1).isNotEqualTo(natureDocumentATraduire2);
        natureDocumentATraduire1.setId(null);
        assertThat(natureDocumentATraduire1).isNotEqualTo(natureDocumentATraduire2);
    }
}
