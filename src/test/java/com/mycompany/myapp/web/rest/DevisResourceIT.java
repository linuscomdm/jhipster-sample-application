package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Devis;
import com.mycompany.myapp.repository.DevisRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link DevisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DevisResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PRIX_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX_TOTAL = new BigDecimal(2);

    private static final Long DEFAULT_ETAT = 1L;
    private static final Long UPDATED_ETAT = 2L;

    private static final String ENTITY_API_URL = "/api/devis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DevisRepository devisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDevisMockMvc;

    private Devis devis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devis createEntity(EntityManager em) {
        Devis devis = new Devis().numero(DEFAULT_NUMERO).date(DEFAULT_DATE).prixTotal(DEFAULT_PRIX_TOTAL).etat(DEFAULT_ETAT);
        return devis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devis createUpdatedEntity(EntityManager em) {
        Devis devis = new Devis().numero(UPDATED_NUMERO).date(UPDATED_DATE).prixTotal(UPDATED_PRIX_TOTAL).etat(UPDATED_ETAT);
        return devis;
    }

    @BeforeEach
    public void initTest() {
        devis = createEntity(em);
    }

    @Test
    @Transactional
    void createDevis() throws Exception {
        int databaseSizeBeforeCreate = devisRepository.findAll().size();
        // Create the Devis
        restDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devis)))
            .andExpect(status().isCreated());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeCreate + 1);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDevis.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDevis.getPrixTotal()).isEqualByComparingTo(DEFAULT_PRIX_TOTAL);
        assertThat(testDevis.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void createDevisWithExistingId() throws Exception {
        // Create the Devis with an existing ID
        devis.setId(1L);

        int databaseSizeBeforeCreate = devisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devis)))
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = devisRepository.findAll().size();
        // set the field null
        devis.setNumero(null);

        // Create the Devis, which fails.

        restDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devis)))
            .andExpect(status().isBadRequest());

        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = devisRepository.findAll().size();
        // set the field null
        devis.setDate(null);

        // Create the Devis, which fails.

        restDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devis)))
            .andExpect(status().isBadRequest());

        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = devisRepository.findAll().size();
        // set the field null
        devis.setPrixTotal(null);

        // Create the Devis, which fails.

        restDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devis)))
            .andExpect(status().isBadRequest());

        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = devisRepository.findAll().size();
        // set the field null
        devis.setEtat(null);

        // Create the Devis, which fails.

        restDevisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devis)))
            .andExpect(status().isBadRequest());

        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get all the devisList
        restDevisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devis.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].prixTotal").value(hasItem(sameNumber(DEFAULT_PRIX_TOTAL))))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.intValue())));
    }

    @Test
    @Transactional
    void getDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        // Get the devis
        restDevisMockMvc
            .perform(get(ENTITY_API_URL_ID, devis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(devis.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.prixTotal").value(sameNumber(DEFAULT_PRIX_TOTAL)))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDevis() throws Exception {
        // Get the devis
        restDevisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        int databaseSizeBeforeUpdate = devisRepository.findAll().size();

        // Update the devis
        Devis updatedDevis = devisRepository.findById(devis.getId()).get();
        // Disconnect from session so that the updates on updatedDevis are not directly saved in db
        em.detach(updatedDevis);
        updatedDevis.numero(UPDATED_NUMERO).date(UPDATED_DATE).prixTotal(UPDATED_PRIX_TOTAL).etat(UPDATED_ETAT);

        restDevisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDevis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDevis))
            )
            .andExpect(status().isOk());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDevis.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDevis.getPrixTotal()).isEqualByComparingTo(UPDATED_PRIX_TOTAL);
        assertThat(testDevis.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void putNonExistingDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, devis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDevisWithPatch() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        int databaseSizeBeforeUpdate = devisRepository.findAll().size();

        // Update the devis using partial update
        Devis partialUpdatedDevis = new Devis();
        partialUpdatedDevis.setId(devis.getId());

        partialUpdatedDevis.numero(UPDATED_NUMERO).date(UPDATED_DATE);

        restDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevis))
            )
            .andExpect(status().isOk());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDevis.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDevis.getPrixTotal()).isEqualByComparingTo(DEFAULT_PRIX_TOTAL);
        assertThat(testDevis.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void fullUpdateDevisWithPatch() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        int databaseSizeBeforeUpdate = devisRepository.findAll().size();

        // Update the devis using partial update
        Devis partialUpdatedDevis = new Devis();
        partialUpdatedDevis.setId(devis.getId());

        partialUpdatedDevis.numero(UPDATED_NUMERO).date(UPDATED_DATE).prixTotal(UPDATED_PRIX_TOTAL).etat(UPDATED_ETAT);

        restDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevis))
            )
            .andExpect(status().isOk());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
        Devis testDevis = devisList.get(devisList.size() - 1);
        assertThat(testDevis.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDevis.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDevis.getPrixTotal()).isEqualByComparingTo(UPDATED_PRIX_TOTAL);
        assertThat(testDevis.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void patchNonExistingDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, devis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(devis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(devis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDevis() throws Exception {
        int databaseSizeBeforeUpdate = devisRepository.findAll().size();
        devis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDevisMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(devis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Devis in the database
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDevis() throws Exception {
        // Initialize the database
        devisRepository.saveAndFlush(devis);

        int databaseSizeBeforeDelete = devisRepository.findAll().size();

        // Delete the devis
        restDevisMockMvc
            .perform(delete(ENTITY_API_URL_ID, devis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Devis> devisList = devisRepository.findAll();
        assertThat(devisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
