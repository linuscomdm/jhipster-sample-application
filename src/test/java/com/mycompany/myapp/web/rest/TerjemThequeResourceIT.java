package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TerjemTheque;
import com.mycompany.myapp.domain.enumeration.FormatDocTraduit;
import com.mycompany.myapp.repository.TerjemThequeRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link TerjemThequeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TerjemThequeResourceIT {

    private static final String DEFAULT_LIEN_DOWNLOAD = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_DOWNLOAD = "BBBBBBBBBB";

    private static final FormatDocTraduit DEFAULT_FORMAT_DOC_TRADUIT = FormatDocTraduit.PDF;
    private static final FormatDocTraduit UPDATED_FORMAT_DOC_TRADUIT = FormatDocTraduit.PNG;

    private static final String DEFAULT_NOM_FICHIER = "AAAAAAAAAA";
    private static final String UPDATED_NOM_FICHIER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOC_TRADUIT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOC_TRADUIT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOC_TRADUIT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOC_TRADUIT_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ETAT = 1L;
    private static final Long UPDATED_ETAT = 2L;

    private static final String ENTITY_API_URL = "/api/terjem-theques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TerjemThequeRepository terjemThequeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerjemThequeMockMvc;

    private TerjemTheque terjemTheque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TerjemTheque createEntity(EntityManager em) {
        TerjemTheque terjemTheque = new TerjemTheque()
            .lienDownload(DEFAULT_LIEN_DOWNLOAD)
            .formatDocTraduit(DEFAULT_FORMAT_DOC_TRADUIT)
            .nomFichier(DEFAULT_NOM_FICHIER)
            .docTraduit(DEFAULT_DOC_TRADUIT)
            .docTraduitContentType(DEFAULT_DOC_TRADUIT_CONTENT_TYPE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .etat(DEFAULT_ETAT);
        return terjemTheque;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TerjemTheque createUpdatedEntity(EntityManager em) {
        TerjemTheque terjemTheque = new TerjemTheque()
            .lienDownload(UPDATED_LIEN_DOWNLOAD)
            .formatDocTraduit(UPDATED_FORMAT_DOC_TRADUIT)
            .nomFichier(UPDATED_NOM_FICHIER)
            .docTraduit(UPDATED_DOC_TRADUIT)
            .docTraduitContentType(UPDATED_DOC_TRADUIT_CONTENT_TYPE)
            .dateCreation(UPDATED_DATE_CREATION)
            .etat(UPDATED_ETAT);
        return terjemTheque;
    }

    @BeforeEach
    public void initTest() {
        terjemTheque = createEntity(em);
    }

    @Test
    @Transactional
    void createTerjemTheque() throws Exception {
        int databaseSizeBeforeCreate = terjemThequeRepository.findAll().size();
        // Create the TerjemTheque
        restTerjemThequeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terjemTheque)))
            .andExpect(status().isCreated());

        // Validate the TerjemTheque in the database
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeCreate + 1);
        TerjemTheque testTerjemTheque = terjemThequeList.get(terjemThequeList.size() - 1);
        assertThat(testTerjemTheque.getLienDownload()).isEqualTo(DEFAULT_LIEN_DOWNLOAD);
        assertThat(testTerjemTheque.getFormatDocTraduit()).isEqualTo(DEFAULT_FORMAT_DOC_TRADUIT);
        assertThat(testTerjemTheque.getNomFichier()).isEqualTo(DEFAULT_NOM_FICHIER);
        assertThat(testTerjemTheque.getDocTraduit()).isEqualTo(DEFAULT_DOC_TRADUIT);
        assertThat(testTerjemTheque.getDocTraduitContentType()).isEqualTo(DEFAULT_DOC_TRADUIT_CONTENT_TYPE);
        assertThat(testTerjemTheque.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testTerjemTheque.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void createTerjemThequeWithExistingId() throws Exception {
        // Create the TerjemTheque with an existing ID
        terjemTheque.setId(1L);

        int databaseSizeBeforeCreate = terjemThequeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerjemThequeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terjemTheque)))
            .andExpect(status().isBadRequest());

        // Validate the TerjemTheque in the database
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLienDownloadIsRequired() throws Exception {
        int databaseSizeBeforeTest = terjemThequeRepository.findAll().size();
        // set the field null
        terjemTheque.setLienDownload(null);

        // Create the TerjemTheque, which fails.

        restTerjemThequeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terjemTheque)))
            .andExpect(status().isBadRequest());

        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormatDocTraduitIsRequired() throws Exception {
        int databaseSizeBeforeTest = terjemThequeRepository.findAll().size();
        // set the field null
        terjemTheque.setFormatDocTraduit(null);

        // Create the TerjemTheque, which fails.

        restTerjemThequeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terjemTheque)))
            .andExpect(status().isBadRequest());

        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomFichierIsRequired() throws Exception {
        int databaseSizeBeforeTest = terjemThequeRepository.findAll().size();
        // set the field null
        terjemTheque.setNomFichier(null);

        // Create the TerjemTheque, which fails.

        restTerjemThequeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terjemTheque)))
            .andExpect(status().isBadRequest());

        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTerjemTheques() throws Exception {
        // Initialize the database
        terjemThequeRepository.saveAndFlush(terjemTheque);

        // Get all the terjemThequeList
        restTerjemThequeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terjemTheque.getId().intValue())))
            .andExpect(jsonPath("$.[*].lienDownload").value(hasItem(DEFAULT_LIEN_DOWNLOAD)))
            .andExpect(jsonPath("$.[*].formatDocTraduit").value(hasItem(DEFAULT_FORMAT_DOC_TRADUIT.toString())))
            .andExpect(jsonPath("$.[*].nomFichier").value(hasItem(DEFAULT_NOM_FICHIER)))
            .andExpect(jsonPath("$.[*].docTraduitContentType").value(hasItem(DEFAULT_DOC_TRADUIT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].docTraduit").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOC_TRADUIT))))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.intValue())));
    }

    @Test
    @Transactional
    void getTerjemTheque() throws Exception {
        // Initialize the database
        terjemThequeRepository.saveAndFlush(terjemTheque);

        // Get the terjemTheque
        restTerjemThequeMockMvc
            .perform(get(ENTITY_API_URL_ID, terjemTheque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(terjemTheque.getId().intValue()))
            .andExpect(jsonPath("$.lienDownload").value(DEFAULT_LIEN_DOWNLOAD))
            .andExpect(jsonPath("$.formatDocTraduit").value(DEFAULT_FORMAT_DOC_TRADUIT.toString()))
            .andExpect(jsonPath("$.nomFichier").value(DEFAULT_NOM_FICHIER))
            .andExpect(jsonPath("$.docTraduitContentType").value(DEFAULT_DOC_TRADUIT_CONTENT_TYPE))
            .andExpect(jsonPath("$.docTraduit").value(Base64Utils.encodeToString(DEFAULT_DOC_TRADUIT)))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTerjemTheque() throws Exception {
        // Get the terjemTheque
        restTerjemThequeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTerjemTheque() throws Exception {
        // Initialize the database
        terjemThequeRepository.saveAndFlush(terjemTheque);

        int databaseSizeBeforeUpdate = terjemThequeRepository.findAll().size();

        // Update the terjemTheque
        TerjemTheque updatedTerjemTheque = terjemThequeRepository.findById(terjemTheque.getId()).get();
        // Disconnect from session so that the updates on updatedTerjemTheque are not directly saved in db
        em.detach(updatedTerjemTheque);
        updatedTerjemTheque
            .lienDownload(UPDATED_LIEN_DOWNLOAD)
            .formatDocTraduit(UPDATED_FORMAT_DOC_TRADUIT)
            .nomFichier(UPDATED_NOM_FICHIER)
            .docTraduit(UPDATED_DOC_TRADUIT)
            .docTraduitContentType(UPDATED_DOC_TRADUIT_CONTENT_TYPE)
            .dateCreation(UPDATED_DATE_CREATION)
            .etat(UPDATED_ETAT);

        restTerjemThequeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTerjemTheque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTerjemTheque))
            )
            .andExpect(status().isOk());

        // Validate the TerjemTheque in the database
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeUpdate);
        TerjemTheque testTerjemTheque = terjemThequeList.get(terjemThequeList.size() - 1);
        assertThat(testTerjemTheque.getLienDownload()).isEqualTo(UPDATED_LIEN_DOWNLOAD);
        assertThat(testTerjemTheque.getFormatDocTraduit()).isEqualTo(UPDATED_FORMAT_DOC_TRADUIT);
        assertThat(testTerjemTheque.getNomFichier()).isEqualTo(UPDATED_NOM_FICHIER);
        assertThat(testTerjemTheque.getDocTraduit()).isEqualTo(UPDATED_DOC_TRADUIT);
        assertThat(testTerjemTheque.getDocTraduitContentType()).isEqualTo(UPDATED_DOC_TRADUIT_CONTENT_TYPE);
        assertThat(testTerjemTheque.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testTerjemTheque.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void putNonExistingTerjemTheque() throws Exception {
        int databaseSizeBeforeUpdate = terjemThequeRepository.findAll().size();
        terjemTheque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerjemThequeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, terjemTheque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terjemTheque))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerjemTheque in the database
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTerjemTheque() throws Exception {
        int databaseSizeBeforeUpdate = terjemThequeRepository.findAll().size();
        terjemTheque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerjemThequeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terjemTheque))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerjemTheque in the database
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTerjemTheque() throws Exception {
        int databaseSizeBeforeUpdate = terjemThequeRepository.findAll().size();
        terjemTheque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerjemThequeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terjemTheque)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TerjemTheque in the database
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTerjemThequeWithPatch() throws Exception {
        // Initialize the database
        terjemThequeRepository.saveAndFlush(terjemTheque);

        int databaseSizeBeforeUpdate = terjemThequeRepository.findAll().size();

        // Update the terjemTheque using partial update
        TerjemTheque partialUpdatedTerjemTheque = new TerjemTheque();
        partialUpdatedTerjemTheque.setId(terjemTheque.getId());

        partialUpdatedTerjemTheque.formatDocTraduit(UPDATED_FORMAT_DOC_TRADUIT).nomFichier(UPDATED_NOM_FICHIER);

        restTerjemThequeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerjemTheque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerjemTheque))
            )
            .andExpect(status().isOk());

        // Validate the TerjemTheque in the database
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeUpdate);
        TerjemTheque testTerjemTheque = terjemThequeList.get(terjemThequeList.size() - 1);
        assertThat(testTerjemTheque.getLienDownload()).isEqualTo(DEFAULT_LIEN_DOWNLOAD);
        assertThat(testTerjemTheque.getFormatDocTraduit()).isEqualTo(UPDATED_FORMAT_DOC_TRADUIT);
        assertThat(testTerjemTheque.getNomFichier()).isEqualTo(UPDATED_NOM_FICHIER);
        assertThat(testTerjemTheque.getDocTraduit()).isEqualTo(DEFAULT_DOC_TRADUIT);
        assertThat(testTerjemTheque.getDocTraduitContentType()).isEqualTo(DEFAULT_DOC_TRADUIT_CONTENT_TYPE);
        assertThat(testTerjemTheque.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testTerjemTheque.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void fullUpdateTerjemThequeWithPatch() throws Exception {
        // Initialize the database
        terjemThequeRepository.saveAndFlush(terjemTheque);

        int databaseSizeBeforeUpdate = terjemThequeRepository.findAll().size();

        // Update the terjemTheque using partial update
        TerjemTheque partialUpdatedTerjemTheque = new TerjemTheque();
        partialUpdatedTerjemTheque.setId(terjemTheque.getId());

        partialUpdatedTerjemTheque
            .lienDownload(UPDATED_LIEN_DOWNLOAD)
            .formatDocTraduit(UPDATED_FORMAT_DOC_TRADUIT)
            .nomFichier(UPDATED_NOM_FICHIER)
            .docTraduit(UPDATED_DOC_TRADUIT)
            .docTraduitContentType(UPDATED_DOC_TRADUIT_CONTENT_TYPE)
            .dateCreation(UPDATED_DATE_CREATION)
            .etat(UPDATED_ETAT);

        restTerjemThequeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerjemTheque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerjemTheque))
            )
            .andExpect(status().isOk());

        // Validate the TerjemTheque in the database
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeUpdate);
        TerjemTheque testTerjemTheque = terjemThequeList.get(terjemThequeList.size() - 1);
        assertThat(testTerjemTheque.getLienDownload()).isEqualTo(UPDATED_LIEN_DOWNLOAD);
        assertThat(testTerjemTheque.getFormatDocTraduit()).isEqualTo(UPDATED_FORMAT_DOC_TRADUIT);
        assertThat(testTerjemTheque.getNomFichier()).isEqualTo(UPDATED_NOM_FICHIER);
        assertThat(testTerjemTheque.getDocTraduit()).isEqualTo(UPDATED_DOC_TRADUIT);
        assertThat(testTerjemTheque.getDocTraduitContentType()).isEqualTo(UPDATED_DOC_TRADUIT_CONTENT_TYPE);
        assertThat(testTerjemTheque.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testTerjemTheque.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void patchNonExistingTerjemTheque() throws Exception {
        int databaseSizeBeforeUpdate = terjemThequeRepository.findAll().size();
        terjemTheque.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerjemThequeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, terjemTheque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(terjemTheque))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerjemTheque in the database
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTerjemTheque() throws Exception {
        int databaseSizeBeforeUpdate = terjemThequeRepository.findAll().size();
        terjemTheque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerjemThequeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(terjemTheque))
            )
            .andExpect(status().isBadRequest());

        // Validate the TerjemTheque in the database
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTerjemTheque() throws Exception {
        int databaseSizeBeforeUpdate = terjemThequeRepository.findAll().size();
        terjemTheque.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerjemThequeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(terjemTheque))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TerjemTheque in the database
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTerjemTheque() throws Exception {
        // Initialize the database
        terjemThequeRepository.saveAndFlush(terjemTheque);

        int databaseSizeBeforeDelete = terjemThequeRepository.findAll().size();

        // Delete the terjemTheque
        restTerjemThequeMockMvc
            .perform(delete(ENTITY_API_URL_ID, terjemTheque.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TerjemTheque> terjemThequeList = terjemThequeRepository.findAll();
        assertThat(terjemThequeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
