package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Langue;
import com.mycompany.myapp.repository.LangueRepository;
import com.mycompany.myapp.service.LangueService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LangueResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LangueResourceIT {

    private static final String DEFAULT_CODE_LANGUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_LANGUE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_LANGUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_LANGUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/langues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LangueRepository langueRepository;

    @Mock
    private LangueRepository langueRepositoryMock;

    @Mock
    private LangueService langueServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLangueMockMvc;

    private Langue langue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Langue createEntity(EntityManager em) {
        Langue langue = new Langue().codeLangue(DEFAULT_CODE_LANGUE).nomLangue(DEFAULT_NOM_LANGUE);
        return langue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Langue createUpdatedEntity(EntityManager em) {
        Langue langue = new Langue().codeLangue(UPDATED_CODE_LANGUE).nomLangue(UPDATED_NOM_LANGUE);
        return langue;
    }

    @BeforeEach
    public void initTest() {
        langue = createEntity(em);
    }

    @Test
    @Transactional
    void createLangue() throws Exception {
        int databaseSizeBeforeCreate = langueRepository.findAll().size();
        // Create the Langue
        restLangueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langue)))
            .andExpect(status().isCreated());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeCreate + 1);
        Langue testLangue = langueList.get(langueList.size() - 1);
        assertThat(testLangue.getCodeLangue()).isEqualTo(DEFAULT_CODE_LANGUE);
        assertThat(testLangue.getNomLangue()).isEqualTo(DEFAULT_NOM_LANGUE);
    }

    @Test
    @Transactional
    void createLangueWithExistingId() throws Exception {
        // Create the Langue with an existing ID
        langue.setId(1L);

        int databaseSizeBeforeCreate = langueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLangueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langue)))
            .andExpect(status().isBadRequest());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLangues() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        // Get all the langueList
        restLangueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(langue.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeLangue").value(hasItem(DEFAULT_CODE_LANGUE)))
            .andExpect(jsonPath("$.[*].nomLangue").value(hasItem(DEFAULT_NOM_LANGUE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLanguesWithEagerRelationshipsIsEnabled() throws Exception {
        when(langueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLangueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(langueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLanguesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(langueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLangueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(langueRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLangue() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        // Get the langue
        restLangueMockMvc
            .perform(get(ENTITY_API_URL_ID, langue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(langue.getId().intValue()))
            .andExpect(jsonPath("$.codeLangue").value(DEFAULT_CODE_LANGUE))
            .andExpect(jsonPath("$.nomLangue").value(DEFAULT_NOM_LANGUE));
    }

    @Test
    @Transactional
    void getNonExistingLangue() throws Exception {
        // Get the langue
        restLangueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLangue() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        int databaseSizeBeforeUpdate = langueRepository.findAll().size();

        // Update the langue
        Langue updatedLangue = langueRepository.findById(langue.getId()).get();
        // Disconnect from session so that the updates on updatedLangue are not directly saved in db
        em.detach(updatedLangue);
        updatedLangue.codeLangue(UPDATED_CODE_LANGUE).nomLangue(UPDATED_NOM_LANGUE);

        restLangueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLangue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLangue))
            )
            .andExpect(status().isOk());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
        Langue testLangue = langueList.get(langueList.size() - 1);
        assertThat(testLangue.getCodeLangue()).isEqualTo(UPDATED_CODE_LANGUE);
        assertThat(testLangue.getNomLangue()).isEqualTo(UPDATED_NOM_LANGUE);
    }

    @Test
    @Transactional
    void putNonExistingLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, langue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(langue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(langue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(langue)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLangueWithPatch() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        int databaseSizeBeforeUpdate = langueRepository.findAll().size();

        // Update the langue using partial update
        Langue partialUpdatedLangue = new Langue();
        partialUpdatedLangue.setId(langue.getId());

        restLangueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLangue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLangue))
            )
            .andExpect(status().isOk());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
        Langue testLangue = langueList.get(langueList.size() - 1);
        assertThat(testLangue.getCodeLangue()).isEqualTo(DEFAULT_CODE_LANGUE);
        assertThat(testLangue.getNomLangue()).isEqualTo(DEFAULT_NOM_LANGUE);
    }

    @Test
    @Transactional
    void fullUpdateLangueWithPatch() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        int databaseSizeBeforeUpdate = langueRepository.findAll().size();

        // Update the langue using partial update
        Langue partialUpdatedLangue = new Langue();
        partialUpdatedLangue.setId(langue.getId());

        partialUpdatedLangue.codeLangue(UPDATED_CODE_LANGUE).nomLangue(UPDATED_NOM_LANGUE);

        restLangueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLangue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLangue))
            )
            .andExpect(status().isOk());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
        Langue testLangue = langueList.get(langueList.size() - 1);
        assertThat(testLangue.getCodeLangue()).isEqualTo(UPDATED_CODE_LANGUE);
        assertThat(testLangue.getNomLangue()).isEqualTo(UPDATED_NOM_LANGUE);
    }

    @Test
    @Transactional
    void patchNonExistingLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, langue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(langue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(langue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLangue() throws Exception {
        int databaseSizeBeforeUpdate = langueRepository.findAll().size();
        langue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLangueMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(langue)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Langue in the database
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLangue() throws Exception {
        // Initialize the database
        langueRepository.saveAndFlush(langue);

        int databaseSizeBeforeDelete = langueRepository.findAll().size();

        // Delete the langue
        restLangueMockMvc
            .perform(delete(ENTITY_API_URL_ID, langue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Langue> langueList = langueRepository.findAll();
        assertThat(langueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
