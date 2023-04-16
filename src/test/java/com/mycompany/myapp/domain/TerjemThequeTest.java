package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TerjemThequeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TerjemTheque.class);
        TerjemTheque terjemTheque1 = new TerjemTheque();
        terjemTheque1.setId(1L);
        TerjemTheque terjemTheque2 = new TerjemTheque();
        terjemTheque2.setId(terjemTheque1.getId());
        assertThat(terjemTheque1).isEqualTo(terjemTheque2);
        terjemTheque2.setId(2L);
        assertThat(terjemTheque1).isNotEqualTo(terjemTheque2);
        terjemTheque1.setId(null);
        assertThat(terjemTheque1).isNotEqualTo(terjemTheque2);
    }
}
