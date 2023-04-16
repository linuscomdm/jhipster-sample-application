package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DetailDevis;
import com.mycompany.myapp.repository.DetailDevisRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link DetailDevisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetailDevisResourceIT {

    private static final Long DEFAULT_QTE = 1L;
    private static final Long UPDATED_QTE = 2L;

    private static final BigDecimal DEFAULT_PRIX_UNITAIRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX_UNITAIRE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRIX_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX_TOTAL = new BigDecimal(2);

    private static final Long DEFAULT_ETAT = 1L;
    private static final Long UPDATED_ETAT = 2L;

    private static final String ENTITY_API_URL = "/api/detail-devis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetailDevisRepository detailDevisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetailDevisMockMvc;

    private DetailDevis detailDevis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailDevis createEntity(EntityManager em) {
        DetailDevis detailDevis = new DetailDevis()
            .qte(DEFAULT_QTE)
            .prixUnitaire(DEFAULT_PRIX_UNITAIRE)
            .prixTotal(DEFAULT_PRIX_TOTAL)
            .etat(DEFAULT_ETAT);
        return detailDevis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailDevis createUpdatedEntity(EntityManager em) {
        DetailDevis detailDevis = new DetailDevis()
            .qte(UPDATED_QTE)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .prixTotal(UPDATED_PRIX_TOTAL)
            .etat(UPDATED_ETAT);
        return detailDevis;
    }

    @BeforeEach
    public void initTest() {
        detailDevis = createEntity(em);
    }

    @Test
    @Transactional
    void createDetailDevis() throws Exception {
        int databaseSizeBeforeCreate = detailDevisRepository.findAll().size();
        // Create the DetailDevis
        restDetailDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDevis)))
            .andExpect(status().isCreated());

        // Validate the DetailDevis in the database
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeCreate + 1);
        DetailDevis testDetailDevis = detailDevisList.get(detailDevisList.size() - 1);
        assertThat(testDetailDevis.getQte()).isEqualTo(DEFAULT_QTE);
        assertThat(testDetailDevis.getPrixUnitaire()).isEqualByComparingTo(DEFAULT_PRIX_UNITAIRE);
        assertThat(testDetailDevis.getPrixTotal()).isEqualByComparingTo(DEFAULT_PRIX_TOTAL);
        assertThat(testDetailDevis.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void createDetailDevisWithExistingId() throws Exception {
        // Create the DetailDevis with an existing ID
        detailDevis.setId(1L);

        int databaseSizeBeforeCreate = detailDevisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDevis)))
            .andExpect(status().isBadRequest());

        // Validate the DetailDevis in the database
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQteIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailDevisRepository.findAll().size();
        // set the field null
        detailDevis.setQte(null);

        // Create the DetailDevis, which fails.

        restDetailDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDevis)))
            .andExpect(status().isBadRequest());

        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixUnitaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailDevisRepository.findAll().size();
        // set the field null
        detailDevis.setPrixUnitaire(null);

        // Create the DetailDevis, which fails.

        restDetailDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDevis)))
            .andExpect(status().isBadRequest());

        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailDevisRepository.findAll().size();
        // set the field null
        detailDevis.setPrixTotal(null);

        // Create the DetailDevis, which fails.

        restDetailDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDevis)))
            .andExpect(status().isBadRequest());

        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailDevisRepository.findAll().size();
        // set the field null
        detailDevis.setEtat(null);

        // Create the DetailDevis, which fails.

        restDetailDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDevis)))
            .andExpect(status().isBadRequest());

        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDetailDevis() throws Exception {
        // Initialize the database
        detailDevisRepository.saveAndFlush(detailDevis);

        // Get all the detailDevisList
        restDetailDevisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailDevis.getId().intValue())))
            .andExpect(jsonPath("$.[*].qte").value(hasItem(DEFAULT_QTE.intValue())))
            .andExpect(jsonPath("$.[*].prixUnitaire").value(hasItem(sameNumber(DEFAULT_PRIX_UNITAIRE))))
            .andExpect(jsonPath("$.[*].prixTotal").value(hasItem(sameNumber(DEFAULT_PRIX_TOTAL))))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.intValue())));
    }

    @Test
    @Transactional
    void getDetailDevis() throws Exception {
        // Initialize the database
        detailDevisRepository.saveAndFlush(detailDevis);

        // Get the detailDevis
        restDetailDevisMockMvc
            .perform(get(ENTITY_API_URL_ID, detailDevis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detailDevis.getId().intValue()))
            .andExpect(jsonPath("$.qte").value(DEFAULT_QTE.intValue()))
            .andExpect(jsonPath("$.prixUnitaire").value(sameNumber(DEFAULT_PRIX_UNITAIRE)))
            .andExpect(jsonPath("$.prixTotal").value(sameNumber(DEFAULT_PRIX_TOTAL)))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDetailDevis() throws Exception {
        // Get the detailDevis
        restDetailDevisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetailDevis() throws Exception {
        // Initialize the database
        detailDevisRepository.saveAndFlush(detailDevis);

        int databaseSizeBeforeUpdate = detailDevisRepository.findAll().size();

        // Update the detailDevis
        DetailDevis updatedDetailDevis = detailDevisRepository.findById(detailDevis.getId()).get();
        // Disconnect from session so that the updates on updatedDetailDevis are not directly saved in db
        em.detach(updatedDetailDevis);
        updatedDetailDevis.qte(UPDATED_QTE).prixUnitaire(UPDATED_PRIX_UNITAIRE).prixTotal(UPDATED_PRIX_TOTAL).etat(UPDATED_ETAT);

        restDetailDevisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDetailDevis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDetailDevis))
            )
            .andExpect(status().isOk());

        // Validate the DetailDevis in the database
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeUpdate);
        DetailDevis testDetailDevis = detailDevisList.get(detailDevisList.size() - 1);
        assertThat(testDetailDevis.getQte()).isEqualTo(UPDATED_QTE);
        assertThat(testDetailDevis.getPrixUnitaire()).isEqualByComparingTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testDetailDevis.getPrixTotal()).isEqualByComparingTo(UPDATED_PRIX_TOTAL);
        assertThat(testDetailDevis.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void putNonExistingDetailDevis() throws Exception {
        int databaseSizeBeforeUpdate = detailDevisRepository.findAll().size();
        detailDevis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailDevisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailDevis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailDevis))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDevis in the database
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetailDevis() throws Exception {
        int databaseSizeBeforeUpdate = detailDevisRepository.findAll().size();
        detailDevis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDevisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailDevis))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDevis in the database
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetailDevis() throws Exception {
        int databaseSizeBeforeUpdate = detailDevisRepository.findAll().size();
        detailDevis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDevisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailDevis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailDevis in the database
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetailDevisWithPatch() throws Exception {
        // Initialize the database
        detailDevisRepository.saveAndFlush(detailDevis);

        int databaseSizeBeforeUpdate = detailDevisRepository.findAll().size();

        // Update the detailDevis using partial update
        DetailDevis partialUpdatedDetailDevis = new DetailDevis();
        partialUpdatedDetailDevis.setId(detailDevis.getId());

        partialUpdatedDetailDevis.prixUnitaire(UPDATED_PRIX_UNITAIRE);

        restDetailDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailDevis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailDevis))
            )
            .andExpect(status().isOk());

        // Validate the DetailDevis in the database
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeUpdate);
        DetailDevis testDetailDevis = detailDevisList.get(detailDevisList.size() - 1);
        assertThat(testDetailDevis.getQte()).isEqualTo(DEFAULT_QTE);
        assertThat(testDetailDevis.getPrixUnitaire()).isEqualByComparingTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testDetailDevis.getPrixTotal()).isEqualByComparingTo(DEFAULT_PRIX_TOTAL);
        assertThat(testDetailDevis.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void fullUpdateDetailDevisWithPatch() throws Exception {
        // Initialize the database
        detailDevisRepository.saveAndFlush(detailDevis);

        int databaseSizeBeforeUpdate = detailDevisRepository.findAll().size();

        // Update the detailDevis using partial update
        DetailDevis partialUpdatedDetailDevis = new DetailDevis();
        partialUpdatedDetailDevis.setId(detailDevis.getId());

        partialUpdatedDetailDevis.qte(UPDATED_QTE).prixUnitaire(UPDATED_PRIX_UNITAIRE).prixTotal(UPDATED_PRIX_TOTAL).etat(UPDATED_ETAT);

        restDetailDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailDevis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailDevis))
            )
            .andExpect(status().isOk());

        // Validate the DetailDevis in the database
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeUpdate);
        DetailDevis testDetailDevis = detailDevisList.get(detailDevisList.size() - 1);
        assertThat(testDetailDevis.getQte()).isEqualTo(UPDATED_QTE);
        assertThat(testDetailDevis.getPrixUnitaire()).isEqualByComparingTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testDetailDevis.getPrixTotal()).isEqualByComparingTo(UPDATED_PRIX_TOTAL);
        assertThat(testDetailDevis.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void patchNonExistingDetailDevis() throws Exception {
        int databaseSizeBeforeUpdate = detailDevisRepository.findAll().size();
        detailDevis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detailDevis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailDevis))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDevis in the database
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetailDevis() throws Exception {
        int databaseSizeBeforeUpdate = detailDevisRepository.findAll().size();
        detailDevis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailDevis))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailDevis in the database
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetailDevis() throws Exception {
        int databaseSizeBeforeUpdate = detailDevisRepository.findAll().size();
        detailDevis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailDevisMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(detailDevis))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailDevis in the database
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetailDevis() throws Exception {
        // Initialize the database
        detailDevisRepository.saveAndFlush(detailDevis);

        int databaseSizeBeforeDelete = detailDevisRepository.findAll().size();

        // Delete the detailDevis
        restDetailDevisMockMvc
            .perform(delete(ENTITY_API_URL_ID, detailDevis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailDevis> detailDevisList = detailDevisRepository.findAll();
        assertThat(detailDevisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
