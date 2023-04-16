package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.NatureDocumentATraduire;
import com.mycompany.myapp.repository.NatureDocumentATraduireRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NatureDocumentATraduireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NatureDocumentATraduireResourceIT {

    private static final String DEFAULT_CODE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_DOCUMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nature-document-a-traduires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatureDocumentATraduireRepository natureDocumentATraduireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureDocumentATraduireMockMvc;

    private NatureDocumentATraduire natureDocumentATraduire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureDocumentATraduire createEntity(EntityManager em) {
        NatureDocumentATraduire natureDocumentATraduire = new NatureDocumentATraduire()
            .codeType(DEFAULT_CODE_TYPE)
            .typeDocument(DEFAULT_TYPE_DOCUMENT);
        return natureDocumentATraduire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureDocumentATraduire createUpdatedEntity(EntityManager em) {
        NatureDocumentATraduire natureDocumentATraduire = new NatureDocumentATraduire()
            .codeType(UPDATED_CODE_TYPE)
            .typeDocument(UPDATED_TYPE_DOCUMENT);
        return natureDocumentATraduire;
    }

    @BeforeEach
    public void initTest() {
        natureDocumentATraduire = createEntity(em);
    }

    @Test
    @Transactional
    void createNatureDocumentATraduire() throws Exception {
        int databaseSizeBeforeCreate = natureDocumentATraduireRepository.findAll().size();
        // Create the NatureDocumentATraduire
        restNatureDocumentATraduireMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureDocumentATraduire))
            )
            .andExpect(status().isCreated());

        // Validate the NatureDocumentATraduire in the database
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeCreate + 1);
        NatureDocumentATraduire testNatureDocumentATraduire = natureDocumentATraduireList.get(natureDocumentATraduireList.size() - 1);
        assertThat(testNatureDocumentATraduire.getCodeType()).isEqualTo(DEFAULT_CODE_TYPE);
        assertThat(testNatureDocumentATraduire.getTypeDocument()).isEqualTo(DEFAULT_TYPE_DOCUMENT);
    }

    @Test
    @Transactional
    void createNatureDocumentATraduireWithExistingId() throws Exception {
        // Create the NatureDocumentATraduire with an existing ID
        natureDocumentATraduire.setId(1L);

        int databaseSizeBeforeCreate = natureDocumentATraduireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureDocumentATraduireMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureDocumentATraduire))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureDocumentATraduire in the database
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNatureDocumentATraduires() throws Exception {
        // Initialize the database
        natureDocumentATraduireRepository.saveAndFlush(natureDocumentATraduire);

        // Get all the natureDocumentATraduireList
        restNatureDocumentATraduireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureDocumentATraduire.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeType").value(hasItem(DEFAULT_CODE_TYPE)))
            .andExpect(jsonPath("$.[*].typeDocument").value(hasItem(DEFAULT_TYPE_DOCUMENT)));
    }

    @Test
    @Transactional
    void getNatureDocumentATraduire() throws Exception {
        // Initialize the database
        natureDocumentATraduireRepository.saveAndFlush(natureDocumentATraduire);

        // Get the natureDocumentATraduire
        restNatureDocumentATraduireMockMvc
            .perform(get(ENTITY_API_URL_ID, natureDocumentATraduire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natureDocumentATraduire.getId().intValue()))
            .andExpect(jsonPath("$.codeType").value(DEFAULT_CODE_TYPE))
            .andExpect(jsonPath("$.typeDocument").value(DEFAULT_TYPE_DOCUMENT));
    }

    @Test
    @Transactional
    void getNonExistingNatureDocumentATraduire() throws Exception {
        // Get the natureDocumentATraduire
        restNatureDocumentATraduireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNatureDocumentATraduire() throws Exception {
        // Initialize the database
        natureDocumentATraduireRepository.saveAndFlush(natureDocumentATraduire);

        int databaseSizeBeforeUpdate = natureDocumentATraduireRepository.findAll().size();

        // Update the natureDocumentATraduire
        NatureDocumentATraduire updatedNatureDocumentATraduire = natureDocumentATraduireRepository
            .findById(natureDocumentATraduire.getId())
            .get();
        // Disconnect from session so that the updates on updatedNatureDocumentATraduire are not directly saved in db
        em.detach(updatedNatureDocumentATraduire);
        updatedNatureDocumentATraduire.codeType(UPDATED_CODE_TYPE).typeDocument(UPDATED_TYPE_DOCUMENT);

        restNatureDocumentATraduireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNatureDocumentATraduire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNatureDocumentATraduire))
            )
            .andExpect(status().isOk());

        // Validate the NatureDocumentATraduire in the database
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeUpdate);
        NatureDocumentATraduire testNatureDocumentATraduire = natureDocumentATraduireList.get(natureDocumentATraduireList.size() - 1);
        assertThat(testNatureDocumentATraduire.getCodeType()).isEqualTo(UPDATED_CODE_TYPE);
        assertThat(testNatureDocumentATraduire.getTypeDocument()).isEqualTo(UPDATED_TYPE_DOCUMENT);
    }

    @Test
    @Transactional
    void putNonExistingNatureDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = natureDocumentATraduireRepository.findAll().size();
        natureDocumentATraduire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureDocumentATraduireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureDocumentATraduire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureDocumentATraduire))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureDocumentATraduire in the database
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNatureDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = natureDocumentATraduireRepository.findAll().size();
        natureDocumentATraduire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureDocumentATraduireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureDocumentATraduire))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureDocumentATraduire in the database
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNatureDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = natureDocumentATraduireRepository.findAll().size();
        natureDocumentATraduire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureDocumentATraduireMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureDocumentATraduire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureDocumentATraduire in the database
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatureDocumentATraduireWithPatch() throws Exception {
        // Initialize the database
        natureDocumentATraduireRepository.saveAndFlush(natureDocumentATraduire);

        int databaseSizeBeforeUpdate = natureDocumentATraduireRepository.findAll().size();

        // Update the natureDocumentATraduire using partial update
        NatureDocumentATraduire partialUpdatedNatureDocumentATraduire = new NatureDocumentATraduire();
        partialUpdatedNatureDocumentATraduire.setId(natureDocumentATraduire.getId());

        restNatureDocumentATraduireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureDocumentATraduire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureDocumentATraduire))
            )
            .andExpect(status().isOk());

        // Validate the NatureDocumentATraduire in the database
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeUpdate);
        NatureDocumentATraduire testNatureDocumentATraduire = natureDocumentATraduireList.get(natureDocumentATraduireList.size() - 1);
        assertThat(testNatureDocumentATraduire.getCodeType()).isEqualTo(DEFAULT_CODE_TYPE);
        assertThat(testNatureDocumentATraduire.getTypeDocument()).isEqualTo(DEFAULT_TYPE_DOCUMENT);
    }

    @Test
    @Transactional
    void fullUpdateNatureDocumentATraduireWithPatch() throws Exception {
        // Initialize the database
        natureDocumentATraduireRepository.saveAndFlush(natureDocumentATraduire);

        int databaseSizeBeforeUpdate = natureDocumentATraduireRepository.findAll().size();

        // Update the natureDocumentATraduire using partial update
        NatureDocumentATraduire partialUpdatedNatureDocumentATraduire = new NatureDocumentATraduire();
        partialUpdatedNatureDocumentATraduire.setId(natureDocumentATraduire.getId());

        partialUpdatedNatureDocumentATraduire.codeType(UPDATED_CODE_TYPE).typeDocument(UPDATED_TYPE_DOCUMENT);

        restNatureDocumentATraduireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureDocumentATraduire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureDocumentATraduire))
            )
            .andExpect(status().isOk());

        // Validate the NatureDocumentATraduire in the database
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeUpdate);
        NatureDocumentATraduire testNatureDocumentATraduire = natureDocumentATraduireList.get(natureDocumentATraduireList.size() - 1);
        assertThat(testNatureDocumentATraduire.getCodeType()).isEqualTo(UPDATED_CODE_TYPE);
        assertThat(testNatureDocumentATraduire.getTypeDocument()).isEqualTo(UPDATED_TYPE_DOCUMENT);
    }

    @Test
    @Transactional
    void patchNonExistingNatureDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = natureDocumentATraduireRepository.findAll().size();
        natureDocumentATraduire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureDocumentATraduireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natureDocumentATraduire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureDocumentATraduire))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureDocumentATraduire in the database
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNatureDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = natureDocumentATraduireRepository.findAll().size();
        natureDocumentATraduire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureDocumentATraduireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureDocumentATraduire))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureDocumentATraduire in the database
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNatureDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = natureDocumentATraduireRepository.findAll().size();
        natureDocumentATraduire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureDocumentATraduireMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureDocumentATraduire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureDocumentATraduire in the database
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNatureDocumentATraduire() throws Exception {
        // Initialize the database
        natureDocumentATraduireRepository.saveAndFlush(natureDocumentATraduire);

        int databaseSizeBeforeDelete = natureDocumentATraduireRepository.findAll().size();

        // Delete the natureDocumentATraduire
        restNatureDocumentATraduireMockMvc
            .perform(delete(ENTITY_API_URL_ID, natureDocumentATraduire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureDocumentATraduire> natureDocumentATraduireList = natureDocumentATraduireRepository.findAll();
        assertThat(natureDocumentATraduireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
