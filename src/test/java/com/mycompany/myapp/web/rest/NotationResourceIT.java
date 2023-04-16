package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Notation;
import com.mycompany.myapp.repository.NotationRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link NotationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NotationResourceIT {

    private static final Integer DEFAULT_NOTETATION = 1;
    private static final Integer UPDATED_NOTETATION = 2;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/notations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotationRepository notationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotationMockMvc;

    private Notation notation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notation createEntity(EntityManager em) {
        Notation notation = new Notation()
            .notetation(DEFAULT_NOTETATION)
            .commentaire(DEFAULT_COMMENTAIRE)
            .dateCreation(DEFAULT_DATE_CREATION);
        return notation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notation createUpdatedEntity(EntityManager em) {
        Notation notation = new Notation()
            .notetation(UPDATED_NOTETATION)
            .commentaire(UPDATED_COMMENTAIRE)
            .dateCreation(UPDATED_DATE_CREATION);
        return notation;
    }

    @BeforeEach
    public void initTest() {
        notation = createEntity(em);
    }

    @Test
    @Transactional
    void createNotation() throws Exception {
        int databaseSizeBeforeCreate = notationRepository.findAll().size();
        // Create the Notation
        restNotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notation)))
            .andExpect(status().isCreated());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeCreate + 1);
        Notation testNotation = notationList.get(notationList.size() - 1);
        assertThat(testNotation.getNotetation()).isEqualTo(DEFAULT_NOTETATION);
        assertThat(testNotation.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testNotation.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
    }

    @Test
    @Transactional
    void createNotationWithExistingId() throws Exception {
        // Create the Notation with an existing ID
        notation.setId(1L);

        int databaseSizeBeforeCreate = notationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notation)))
            .andExpect(status().isBadRequest());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNotetationIsRequired() throws Exception {
        int databaseSizeBeforeTest = notationRepository.findAll().size();
        // set the field null
        notation.setNotetation(null);

        // Create the Notation, which fails.

        restNotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notation)))
            .andExpect(status().isBadRequest());

        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNotations() throws Exception {
        // Initialize the database
        notationRepository.saveAndFlush(notation);

        // Get all the notationList
        restNotationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notation.getId().intValue())))
            .andExpect(jsonPath("$.[*].notetation").value(hasItem(DEFAULT_NOTETATION)))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())));
    }

    @Test
    @Transactional
    void getNotation() throws Exception {
        // Initialize the database
        notationRepository.saveAndFlush(notation);

        // Get the notation
        restNotationMockMvc
            .perform(get(ENTITY_API_URL_ID, notation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notation.getId().intValue()))
            .andExpect(jsonPath("$.notetation").value(DEFAULT_NOTETATION))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNotation() throws Exception {
        // Get the notation
        restNotationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNotation() throws Exception {
        // Initialize the database
        notationRepository.saveAndFlush(notation);

        int databaseSizeBeforeUpdate = notationRepository.findAll().size();

        // Update the notation
        Notation updatedNotation = notationRepository.findById(notation.getId()).get();
        // Disconnect from session so that the updates on updatedNotation are not directly saved in db
        em.detach(updatedNotation);
        updatedNotation.notetation(UPDATED_NOTETATION).commentaire(UPDATED_COMMENTAIRE).dateCreation(UPDATED_DATE_CREATION);

        restNotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNotation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNotation))
            )
            .andExpect(status().isOk());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeUpdate);
        Notation testNotation = notationList.get(notationList.size() - 1);
        assertThat(testNotation.getNotetation()).isEqualTo(UPDATED_NOTETATION);
        assertThat(testNotation.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testNotation.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    void putNonExistingNotation() throws Exception {
        int databaseSizeBeforeUpdate = notationRepository.findAll().size();
        notation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotation() throws Exception {
        int databaseSizeBeforeUpdate = notationRepository.findAll().size();
        notation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotation() throws Exception {
        int databaseSizeBeforeUpdate = notationRepository.findAll().size();
        notation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(notation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotationWithPatch() throws Exception {
        // Initialize the database
        notationRepository.saveAndFlush(notation);

        int databaseSizeBeforeUpdate = notationRepository.findAll().size();

        // Update the notation using partial update
        Notation partialUpdatedNotation = new Notation();
        partialUpdatedNotation.setId(notation.getId());

        partialUpdatedNotation.dateCreation(UPDATED_DATE_CREATION);

        restNotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotation))
            )
            .andExpect(status().isOk());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeUpdate);
        Notation testNotation = notationList.get(notationList.size() - 1);
        assertThat(testNotation.getNotetation()).isEqualTo(DEFAULT_NOTETATION);
        assertThat(testNotation.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testNotation.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    void fullUpdateNotationWithPatch() throws Exception {
        // Initialize the database
        notationRepository.saveAndFlush(notation);

        int databaseSizeBeforeUpdate = notationRepository.findAll().size();

        // Update the notation using partial update
        Notation partialUpdatedNotation = new Notation();
        partialUpdatedNotation.setId(notation.getId());

        partialUpdatedNotation.notetation(UPDATED_NOTETATION).commentaire(UPDATED_COMMENTAIRE).dateCreation(UPDATED_DATE_CREATION);

        restNotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotation))
            )
            .andExpect(status().isOk());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeUpdate);
        Notation testNotation = notationList.get(notationList.size() - 1);
        assertThat(testNotation.getNotetation()).isEqualTo(UPDATED_NOTETATION);
        assertThat(testNotation.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testNotation.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    void patchNonExistingNotation() throws Exception {
        int databaseSizeBeforeUpdate = notationRepository.findAll().size();
        notation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotation() throws Exception {
        int databaseSizeBeforeUpdate = notationRepository.findAll().size();
        notation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotation() throws Exception {
        int databaseSizeBeforeUpdate = notationRepository.findAll().size();
        notation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(notation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Notation in the database
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotation() throws Exception {
        // Initialize the database
        notationRepository.saveAndFlush(notation);

        int databaseSizeBeforeDelete = notationRepository.findAll().size();

        // Delete the notation
        restNotationMockMvc
            .perform(delete(ENTITY_API_URL_ID, notation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notation> notationList = notationRepository.findAll();
        assertThat(notationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
