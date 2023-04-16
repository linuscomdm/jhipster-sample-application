package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeDeTraductionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeDeTraduction.class);
        DemandeDeTraduction demandeDeTraduction1 = new DemandeDeTraduction();
        demandeDeTraduction1.setId(1L);
        DemandeDeTraduction demandeDeTraduction2 = new DemandeDeTraduction();
        demandeDeTraduction2.setId(demandeDeTraduction1.getId());
        assertThat(demandeDeTraduction1).isEqualTo(demandeDeTraduction2);
        demandeDeTraduction2.setId(2L);
        assertThat(demandeDeTraduction1).isNotEqualTo(demandeDeTraduction2);
        demandeDeTraduction1.setId(null);
        assertThat(demandeDeTraduction1).isNotEqualTo(demandeDeTraduction2);
    }
}
