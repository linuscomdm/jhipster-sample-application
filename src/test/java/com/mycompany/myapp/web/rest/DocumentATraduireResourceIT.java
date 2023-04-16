package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DocumentATraduire;
import com.mycompany.myapp.repository.DocumentATraduireRepository;
import com.mycompany.myapp.service.DocumentATraduireService;
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
 * Integration tests for the {@link DocumentATraduireResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DocumentATraduireResourceIT {

    private static final Integer DEFAULT_NOMBRE_DE_PAGES_A_TRADUIRE = 1;
    private static final Integer UPDATED_NOMBRE_DE_PAGES_A_TRADUIRE = 2;

    private static final String DEFAULT_MENTION_TRAITEMENT_PARTICULIER = "AAAAAAAAAA";
    private static final String UPDATED_MENTION_TRAITEMENT_PARTICULIER = "BBBBBBBBBB";

    private static final String DEFAULT_REMARQUES = "AAAAAAAAAA";
    private static final String UPDATED_REMARQUES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/document-a-traduires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentATraduireRepository documentATraduireRepository;

    @Mock
    private DocumentATraduireRepository documentATraduireRepositoryMock;

    @Mock
    private DocumentATraduireService documentATraduireServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentATraduireMockMvc;

    private DocumentATraduire documentATraduire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentATraduire createEntity(EntityManager em) {
        DocumentATraduire documentATraduire = new DocumentATraduire()
            .nombreDePagesATraduire(DEFAULT_NOMBRE_DE_PAGES_A_TRADUIRE)
            .mentionTraitementParticulier(DEFAULT_MENTION_TRAITEMENT_PARTICULIER)
            .remarques(DEFAULT_REMARQUES);
        return documentATraduire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentATraduire createUpdatedEntity(EntityManager em) {
        DocumentATraduire documentATraduire = new DocumentATraduire()
            .nombreDePagesATraduire(UPDATED_NOMBRE_DE_PAGES_A_TRADUIRE)
            .mentionTraitementParticulier(UPDATED_MENTION_TRAITEMENT_PARTICULIER)
            .remarques(UPDATED_REMARQUES);
        return documentATraduire;
    }

    @BeforeEach
    public void initTest() {
        documentATraduire = createEntity(em);
    }

    @Test
    @Transactional
    void createDocumentATraduire() throws Exception {
        int databaseSizeBeforeCreate = documentATraduireRepository.findAll().size();
        // Create the DocumentATraduire
        restDocumentATraduireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentATraduire))
            )
            .andExpect(status().isCreated());

        // Validate the DocumentATraduire in the database
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentATraduire testDocumentATraduire = documentATraduireList.get(documentATraduireList.size() - 1);
        assertThat(testDocumentATraduire.getNombreDePagesATraduire()).isEqualTo(DEFAULT_NOMBRE_DE_PAGES_A_TRADUIRE);
        assertThat(testDocumentATraduire.getMentionTraitementParticulier()).isEqualTo(DEFAULT_MENTION_TRAITEMENT_PARTICULIER);
        assertThat(testDocumentATraduire.getRemarques()).isEqualTo(DEFAULT_REMARQUES);
    }

    @Test
    @Transactional
    void createDocumentATraduireWithExistingId() throws Exception {
        // Create the DocumentATraduire with an existing ID
        documentATraduire.setId(1L);

        int databaseSizeBeforeCreate = documentATraduireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentATraduireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentATraduire))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentATraduire in the database
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreDePagesATraduireIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentATraduireRepository.findAll().size();
        // set the field null
        documentATraduire.setNombreDePagesATraduire(null);

        // Create the DocumentATraduire, which fails.

        restDocumentATraduireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentATraduire))
            )
            .andExpect(status().isBadRequest());

        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDocumentATraduires() throws Exception {
        // Initialize the database
        documentATraduireRepository.saveAndFlush(documentATraduire);

        // Get all the documentATraduireList
        restDocumentATraduireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentATraduire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreDePagesATraduire").value(hasItem(DEFAULT_NOMBRE_DE_PAGES_A_TRADUIRE)))
            .andExpect(jsonPath("$.[*].mentionTraitementParticulier").value(hasItem(DEFAULT_MENTION_TRAITEMENT_PARTICULIER)))
            .andExpect(jsonPath("$.[*].remarques").value(hasItem(DEFAULT_REMARQUES)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocumentATraduiresWithEagerRelationshipsIsEnabled() throws Exception {
        when(documentATraduireServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocumentATraduireMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(documentATraduireServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocumentATraduiresWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(documentATraduireServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocumentATraduireMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(documentATraduireRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDocumentATraduire() throws Exception {
        // Initialize the database
        documentATraduireRepository.saveAndFlush(documentATraduire);

        // Get the documentATraduire
        restDocumentATraduireMockMvc
            .perform(get(ENTITY_API_URL_ID, documentATraduire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentATraduire.getId().intValue()))
            .andExpect(jsonPath("$.nombreDePagesATraduire").value(DEFAULT_NOMBRE_DE_PAGES_A_TRADUIRE))
            .andExpect(jsonPath("$.mentionTraitementParticulier").value(DEFAULT_MENTION_TRAITEMENT_PARTICULIER))
            .andExpect(jsonPath("$.remarques").value(DEFAULT_REMARQUES));
    }

    @Test
    @Transactional
    void getNonExistingDocumentATraduire() throws Exception {
        // Get the documentATraduire
        restDocumentATraduireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentATraduire() throws Exception {
        // Initialize the database
        documentATraduireRepository.saveAndFlush(documentATraduire);

        int databaseSizeBeforeUpdate = documentATraduireRepository.findAll().size();

        // Update the documentATraduire
        DocumentATraduire updatedDocumentATraduire = documentATraduireRepository.findById(documentATraduire.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentATraduire are not directly saved in db
        em.detach(updatedDocumentATraduire);
        updatedDocumentATraduire
            .nombreDePagesATraduire(UPDATED_NOMBRE_DE_PAGES_A_TRADUIRE)
            .mentionTraitementParticulier(UPDATED_MENTION_TRAITEMENT_PARTICULIER)
            .remarques(UPDATED_REMARQUES);

        restDocumentATraduireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocumentATraduire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocumentATraduire))
            )
            .andExpect(status().isOk());

        // Validate the DocumentATraduire in the database
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeUpdate);
        DocumentATraduire testDocumentATraduire = documentATraduireList.get(documentATraduireList.size() - 1);
        assertThat(testDocumentATraduire.getNombreDePagesATraduire()).isEqualTo(UPDATED_NOMBRE_DE_PAGES_A_TRADUIRE);
        assertThat(testDocumentATraduire.getMentionTraitementParticulier()).isEqualTo(UPDATED_MENTION_TRAITEMENT_PARTICULIER);
        assertThat(testDocumentATraduire.getRemarques()).isEqualTo(UPDATED_REMARQUES);
    }

    @Test
    @Transactional
    void putNonExistingDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = documentATraduireRepository.findAll().size();
        documentATraduire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentATraduireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentATraduire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentATraduire))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentATraduire in the database
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = documentATraduireRepository.findAll().size();
        documentATraduire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentATraduireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentATraduire))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentATraduire in the database
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = documentATraduireRepository.findAll().size();
        documentATraduire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentATraduireMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentATraduire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentATraduire in the database
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentATraduireWithPatch() throws Exception {
        // Initialize the database
        documentATraduireRepository.saveAndFlush(documentATraduire);

        int databaseSizeBeforeUpdate = documentATraduireRepository.findAll().size();

        // Update the documentATraduire using partial update
        DocumentATraduire partialUpdatedDocumentATraduire = new DocumentATraduire();
        partialUpdatedDocumentATraduire.setId(documentATraduire.getId());

        partialUpdatedDocumentATraduire.nombreDePagesATraduire(UPDATED_NOMBRE_DE_PAGES_A_TRADUIRE).remarques(UPDATED_REMARQUES);

        restDocumentATraduireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentATraduire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentATraduire))
            )
            .andExpect(status().isOk());

        // Validate the DocumentATraduire in the database
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeUpdate);
        DocumentATraduire testDocumentATraduire = documentATraduireList.get(documentATraduireList.size() - 1);
        assertThat(testDocumentATraduire.getNombreDePagesATraduire()).isEqualTo(UPDATED_NOMBRE_DE_PAGES_A_TRADUIRE);
        assertThat(testDocumentATraduire.getMentionTraitementParticulier()).isEqualTo(DEFAULT_MENTION_TRAITEMENT_PARTICULIER);
        assertThat(testDocumentATraduire.getRemarques()).isEqualTo(UPDATED_REMARQUES);
    }

    @Test
    @Transactional
    void fullUpdateDocumentATraduireWithPatch() throws Exception {
        // Initialize the database
        documentATraduireRepository.saveAndFlush(documentATraduire);

        int databaseSizeBeforeUpdate = documentATraduireRepository.findAll().size();

        // Update the documentATraduire using partial update
        DocumentATraduire partialUpdatedDocumentATraduire = new DocumentATraduire();
        partialUpdatedDocumentATraduire.setId(documentATraduire.getId());

        partialUpdatedDocumentATraduire
            .nombreDePagesATraduire(UPDATED_NOMBRE_DE_PAGES_A_TRADUIRE)
            .mentionTraitementParticulier(UPDATED_MENTION_TRAITEMENT_PARTICULIER)
            .remarques(UPDATED_REMARQUES);

        restDocumentATraduireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentATraduire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentATraduire))
            )
            .andExpect(status().isOk());

        // Validate the DocumentATraduire in the database
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeUpdate);
        DocumentATraduire testDocumentATraduire = documentATraduireList.get(documentATraduireList.size() - 1);
        assertThat(testDocumentATraduire.getNombreDePagesATraduire()).isEqualTo(UPDATED_NOMBRE_DE_PAGES_A_TRADUIRE);
        assertThat(testDocumentATraduire.getMentionTraitementParticulier()).isEqualTo(UPDATED_MENTION_TRAITEMENT_PARTICULIER);
        assertThat(testDocumentATraduire.getRemarques()).isEqualTo(UPDATED_REMARQUES);
    }

    @Test
    @Transactional
    void patchNonExistingDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = documentATraduireRepository.findAll().size();
        documentATraduire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentATraduireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentATraduire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentATraduire))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentATraduire in the database
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = documentATraduireRepository.findAll().size();
        documentATraduire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentATraduireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentATraduire))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentATraduire in the database
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentATraduire() throws Exception {
        int databaseSizeBeforeUpdate = documentATraduireRepository.findAll().size();
        documentATraduire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentATraduireMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentATraduire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentATraduire in the database
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentATraduire() throws Exception {
        // Initialize the database
        documentATraduireRepository.saveAndFlush(documentATraduire);

        int databaseSizeBeforeDelete = documentATraduireRepository.findAll().size();

        // Delete the documentATraduire
        restDocumentATraduireMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentATraduire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentATraduire> documentATraduireList = documentATraduireRepository.findAll();
        assertThat(documentATraduireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
