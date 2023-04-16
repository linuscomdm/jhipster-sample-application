package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notation.class);
        Notation notation1 = new Notation();
        notation1.setId(1L);
        Notation notation2 = new Notation();
        notation2.setId(notation1.getId());
        assertThat(notation1).isEqualTo(notation2);
        notation2.setId(2L);
        assertThat(notation1).isNotEqualTo(notation2);
        notation1.setId(null);
        assertThat(notation1).isNotEqualTo(notation2);
    }
}
