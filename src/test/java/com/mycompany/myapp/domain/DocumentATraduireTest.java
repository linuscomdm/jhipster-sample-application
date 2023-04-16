package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentATraduireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentATraduire.class);
        DocumentATraduire documentATraduire1 = new DocumentATraduire();
        documentATraduire1.setId(1L);
        DocumentATraduire documentATraduire2 = new DocumentATraduire();
        documentATraduire2.setId(documentATraduire1.getId());
        assertThat(documentATraduire1).isEqualTo(documentATraduire2);
        documentATraduire2.setId(2L);
        assertThat(documentATraduire1).isNotEqualTo(documentATraduire2);
        documentATraduire1.setId(null);
        assertThat(documentATraduire1).isNotEqualTo(documentATraduire2);
    }
}
