package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.AgenceBanque;
import com.mycompany.myapp.repository.AgenceBanqueRepository;
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
 * Integration tests for the {@link AgenceBanqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgenceBanqueResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/agence-banques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AgenceBanqueRepository agenceBanqueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgenceBanqueMockMvc;

    private AgenceBanque agenceBanque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgenceBanque createEntity(EntityManager em) {
        AgenceBanque agenceBanque = new AgenceBanque().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE);
        return agenceBanque;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgenceBanque createUpdatedEntity(EntityManager em) {
        AgenceBanque agenceBanque = new AgenceBanque().code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
        return agenceBanque;
    }

    @BeforeEach
    public void initTest() {
        agenceBanque = createEntity(em);
    }

    @Test
    @Transactional
    void createAgenceBanque() throws Exception {
        int databaseSizeBeforeCreate = agenceBanqueRepository.findAll().size();
        // Create the AgenceBanque
        restAgenceBanqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agenceBanque)))
            .andExpect(status().isCreated());

        // Validate the AgenceBanque in the database
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeCreate + 1);
        AgenceBanque testAgenceBanque = agenceBanqueList.get(agenceBanqueList.size() - 1);
        assertThat(testAgenceBanque.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAgenceBanque.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createAgenceBanqueWithExistingId() throws Exception {
        // Create the AgenceBanque with an existing ID
        agenceBanque.setId(1L);

        int databaseSizeBeforeCreate = agenceBanqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgenceBanqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agenceBanque)))
            .andExpect(status().isBadRequest());

        // Validate the AgenceBanque in the database
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenceBanqueRepository.findAll().size();
        // set the field null
        agenceBanque.setCode(null);

        // Create the AgenceBanque, which fails.

        restAgenceBanqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agenceBanque)))
            .andExpect(status().isBadRequest());

        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenceBanqueRepository.findAll().size();
        // set the field null
        agenceBanque.setLibelle(null);

        // Create the AgenceBanque, which fails.

        restAgenceBanqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agenceBanque)))
            .andExpect(status().isBadRequest());

        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAgenceBanques() throws Exception {
        // Initialize the database
        agenceBanqueRepository.saveAndFlush(agenceBanque);

        // Get all the agenceBanqueList
        restAgenceBanqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agenceBanque.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getAgenceBanque() throws Exception {
        // Initialize the database
        agenceBanqueRepository.saveAndFlush(agenceBanque);

        // Get the agenceBanque
        restAgenceBanqueMockMvc
            .perform(get(ENTITY_API_URL_ID, agenceBanque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agenceBanque.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingAgenceBanque() throws Exception {
        // Get the agenceBanque
        restAgenceBanqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgenceBanque() throws Exception {
        // Initialize the database
        agenceBanqueRepository.saveAndFlush(agenceBanque);

        int databaseSizeBeforeUpdate = agenceBanqueRepository.findAll().size();

        // Update the agenceBanque
        AgenceBanque updatedAgenceBanque = agenceBanqueRepository.findById(agenceBanque.getId()).get();
        // Disconnect from session so that the updates on updatedAgenceBanque are not directly saved in db
        em.detach(updatedAgenceBanque);
        updatedAgenceBanque.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

        restAgenceBanqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgenceBanque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAgenceBanque))
            )
            .andExpect(status().isOk());

        // Validate the AgenceBanque in the database
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeUpdate);
        AgenceBanque testAgenceBanque = agenceBanqueList.get(agenceBanqueList.size() - 1);
        assertThat(testAgenceBanque.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAgenceBanque.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingAgenceBanque() throws Exception {
        int databaseSizeBeforeUpdate = agenceBanqueRepository.findAll().size();
        agenceBanque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenceBanqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agenceBanque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agenceBanque))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgenceBanque in the database
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgenceBanque() throws Exception {
        int databaseSizeBeforeUpdate = agenceBanqueRepository.findAll().size();
        agenceBanque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenceBanqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agenceBanque))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgenceBanque in the database
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgenceBanque() throws Exception {
        int databaseSizeBeforeUpdate = agenceBanqueRepository.findAll().size();
        agenceBanque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenceBanqueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agenceBanque)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgenceBanque in the database
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgenceBanqueWithPatch() throws Exception {
        // Initialize the database
        agenceBanqueRepository.saveAndFlush(agenceBanque);

        int databaseSizeBeforeUpdate = agenceBanqueRepository.findAll().size();

        // Update the agenceBanque using partial update
        AgenceBanque partialUpdatedAgenceBanque = new AgenceBanque();
        partialUpdatedAgenceBanque.setId(agenceBanque.getId());

        restAgenceBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgenceBanque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgenceBanque))
            )
            .andExpect(status().isOk());

        // Validate the AgenceBanque in the database
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeUpdate);
        AgenceBanque testAgenceBanque = agenceBanqueList.get(agenceBanqueList.size() - 1);
        assertThat(testAgenceBanque.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAgenceBanque.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateAgenceBanqueWithPatch() throws Exception {
        // Initialize the database
        agenceBanqueRepository.saveAndFlush(agenceBanque);

        int databaseSizeBeforeUpdate = agenceBanqueRepository.findAll().size();

        // Update the agenceBanque using partial update
        AgenceBanque partialUpdatedAgenceBanque = new AgenceBanque();
        partialUpdatedAgenceBanque.setId(agenceBanque.getId());

        partialUpdatedAgenceBanque.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

        restAgenceBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgenceBanque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgenceBanque))
            )
            .andExpect(status().isOk());

        // Validate the AgenceBanque in the database
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeUpdate);
        AgenceBanque testAgenceBanque = agenceBanqueList.get(agenceBanqueList.size() - 1);
        assertThat(testAgenceBanque.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAgenceBanque.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingAgenceBanque() throws Exception {
        int databaseSizeBeforeUpdate = agenceBanqueRepository.findAll().size();
        agenceBanque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenceBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agenceBanque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agenceBanque))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgenceBanque in the database
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgenceBanque() throws Exception {
        int databaseSizeBeforeUpdate = agenceBanqueRepository.findAll().size();
        agenceBanque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenceBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agenceBanque))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgenceBanque in the database
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgenceBanque() throws Exception {
        int databaseSizeBeforeUpdate = agenceBanqueRepository.findAll().size();
        agenceBanque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenceBanqueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(agenceBanque))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgenceBanque in the database
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgenceBanque() throws Exception {
        // Initialize the database
        agenceBanqueRepository.saveAndFlush(agenceBanque);

        int databaseSizeBeforeDelete = agenceBanqueRepository.findAll().size();

        // Delete the agenceBanque
        restAgenceBanqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, agenceBanque.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgenceBanque> agenceBanqueList = agenceBanqueRepository.findAll();
        assertThat(agenceBanqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
