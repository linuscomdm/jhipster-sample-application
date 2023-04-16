package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DemandeDeTraduction;
import com.mycompany.myapp.domain.enumeration.EtatDemande;
import com.mycompany.myapp.domain.enumeration.ModeEnvoi;
import com.mycompany.myapp.domain.enumeration.ModeLivraison;
import com.mycompany.myapp.repository.DemandeDeTraductionRepository;
import com.mycompany.myapp.service.DemandeDeTraductionService;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link DemandeDeTraductionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DemandeDeTraductionResourceIT {

    private static final ModeEnvoi DEFAULT_MODE_ENVOI_PRECONISE = ModeEnvoi.COURRIERPOSTAL;
    private static final ModeEnvoi UPDATED_MODE_ENVOI_PRECONISE = ModeEnvoi.EMAIL;

    private static final ModeLivraison DEFAULT_MODE_LIVRAISON_EXIGE = ModeLivraison.COURRIERPOSTAL;
    private static final ModeLivraison UPDATED_MODE_LIVRAISON_EXIGE = ModeLivraison.EMAIL;

    private static final Integer DEFAULT_DELAI_DE_TRAITEMEN_SOUHAITE = 1;
    private static final Integer UPDATED_DELAI_DE_TRAITEMEN_SOUHAITE = 2;

    private static final String DEFAULT_ADRESSE_LIVRAISON = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_LIVRAISON = "BBBBBBBBBB";

    private static final Integer DEFAULT_DELAI_DE_TRAITEMEN_PRESTATAIRE = 1;
    private static final Integer UPDATED_DELAI_DE_TRAITEMEN_PRESTATAIRE = 2;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CLOTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CLOTURE = LocalDate.now(ZoneId.systemDefault());

    private static final EtatDemande DEFAULT_ETAT = EtatDemande.Initiee;
    private static final EtatDemande UPDATED_ETAT = EtatDemande.Devis;

    private static final String ENTITY_API_URL = "/api/demande-de-traductions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeDeTraductionRepository demandeDeTraductionRepository;

    @Mock
    private DemandeDeTraductionRepository demandeDeTraductionRepositoryMock;

    @Mock
    private DemandeDeTraductionService demandeDeTraductionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeDeTraductionMockMvc;

    private DemandeDeTraduction demandeDeTraduction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeDeTraduction createEntity(EntityManager em) {
        DemandeDeTraduction demandeDeTraduction = new DemandeDeTraduction()
            .modeEnvoiPreconise(DEFAULT_MODE_ENVOI_PRECONISE)
            .modeLivraisonExige(DEFAULT_MODE_LIVRAISON_EXIGE)
            .delaiDeTraitemenSouhaite(DEFAULT_DELAI_DE_TRAITEMEN_SOUHAITE)
            .adresseLivraison(DEFAULT_ADRESSE_LIVRAISON)
            .delaiDeTraitemenPrestataire(DEFAULT_DELAI_DE_TRAITEMEN_PRESTATAIRE)
            .observation(DEFAULT_OBSERVATION)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateCloture(DEFAULT_DATE_CLOTURE)
            .etat(DEFAULT_ETAT);
        return demandeDeTraduction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeDeTraduction createUpdatedEntity(EntityManager em) {
        DemandeDeTraduction demandeDeTraduction = new DemandeDeTraduction()
            .modeEnvoiPreconise(UPDATED_MODE_ENVOI_PRECONISE)
            .modeLivraisonExige(UPDATED_MODE_LIVRAISON_EXIGE)
            .delaiDeTraitemenSouhaite(UPDATED_DELAI_DE_TRAITEMEN_SOUHAITE)
            .adresseLivraison(UPDATED_ADRESSE_LIVRAISON)
            .delaiDeTraitemenPrestataire(UPDATED_DELAI_DE_TRAITEMEN_PRESTATAIRE)
            .observation(UPDATED_OBSERVATION)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .etat(UPDATED_ETAT);
        return demandeDeTraduction;
    }

    @BeforeEach
    public void initTest() {
        demandeDeTraduction = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeDeTraduction() throws Exception {
        int databaseSizeBeforeCreate = demandeDeTraductionRepository.findAll().size();
        // Create the DemandeDeTraduction
        restDemandeDeTraductionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDeTraduction))
            )
            .andExpect(status().isCreated());

        // Validate the DemandeDeTraduction in the database
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeDeTraduction testDemandeDeTraduction = demandeDeTraductionList.get(demandeDeTraductionList.size() - 1);
        assertThat(testDemandeDeTraduction.getModeEnvoiPreconise()).isEqualTo(DEFAULT_MODE_ENVOI_PRECONISE);
        assertThat(testDemandeDeTraduction.getModeLivraisonExige()).isEqualTo(DEFAULT_MODE_LIVRAISON_EXIGE);
        assertThat(testDemandeDeTraduction.getDelaiDeTraitemenSouhaite()).isEqualTo(DEFAULT_DELAI_DE_TRAITEMEN_SOUHAITE);
        assertThat(testDemandeDeTraduction.getAdresseLivraison()).isEqualTo(DEFAULT_ADRESSE_LIVRAISON);
        assertThat(testDemandeDeTraduction.getDelaiDeTraitemenPrestataire()).isEqualTo(DEFAULT_DELAI_DE_TRAITEMEN_PRESTATAIRE);
        assertThat(testDemandeDeTraduction.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testDemandeDeTraduction.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testDemandeDeTraduction.getDateCloture()).isEqualTo(DEFAULT_DATE_CLOTURE);
        assertThat(testDemandeDeTraduction.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void createDemandeDeTraductionWithExistingId() throws Exception {
        // Create the DemandeDeTraduction with an existing ID
        demandeDeTraduction.setId(1L);

        int databaseSizeBeforeCreate = demandeDeTraductionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeDeTraductionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDeTraduction))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeDeTraduction in the database
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemandeDeTraductions() throws Exception {
        // Initialize the database
        demandeDeTraductionRepository.saveAndFlush(demandeDeTraduction);

        // Get all the demandeDeTraductionList
        restDemandeDeTraductionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeDeTraduction.getId().intValue())))
            .andExpect(jsonPath("$.[*].modeEnvoiPreconise").value(hasItem(DEFAULT_MODE_ENVOI_PRECONISE.toString())))
            .andExpect(jsonPath("$.[*].modeLivraisonExige").value(hasItem(DEFAULT_MODE_LIVRAISON_EXIGE.toString())))
            .andExpect(jsonPath("$.[*].delaiDeTraitemenSouhaite").value(hasItem(DEFAULT_DELAI_DE_TRAITEMEN_SOUHAITE)))
            .andExpect(jsonPath("$.[*].adresseLivraison").value(hasItem(DEFAULT_ADRESSE_LIVRAISON)))
            .andExpect(jsonPath("$.[*].delaiDeTraitemenPrestataire").value(hasItem(DEFAULT_DELAI_DE_TRAITEMEN_PRESTATAIRE)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateCloture").value(hasItem(DEFAULT_DATE_CLOTURE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDemandeDeTraductionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(demandeDeTraductionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDemandeDeTraductionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(demandeDeTraductionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDemandeDeTraductionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(demandeDeTraductionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDemandeDeTraductionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(demandeDeTraductionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDemandeDeTraduction() throws Exception {
        // Initialize the database
        demandeDeTraductionRepository.saveAndFlush(demandeDeTraduction);

        // Get the demandeDeTraduction
        restDemandeDeTraductionMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeDeTraduction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeDeTraduction.getId().intValue()))
            .andExpect(jsonPath("$.modeEnvoiPreconise").value(DEFAULT_MODE_ENVOI_PRECONISE.toString()))
            .andExpect(jsonPath("$.modeLivraisonExige").value(DEFAULT_MODE_LIVRAISON_EXIGE.toString()))
            .andExpect(jsonPath("$.delaiDeTraitemenSouhaite").value(DEFAULT_DELAI_DE_TRAITEMEN_SOUHAITE))
            .andExpect(jsonPath("$.adresseLivraison").value(DEFAULT_ADRESSE_LIVRAISON))
            .andExpect(jsonPath("$.delaiDeTraitemenPrestataire").value(DEFAULT_DELAI_DE_TRAITEMEN_PRESTATAIRE))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateCloture").value(DEFAULT_DATE_CLOTURE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDemandeDeTraduction() throws Exception {
        // Get the demandeDeTraduction
        restDemandeDeTraductionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemandeDeTraduction() throws Exception {
        // Initialize the database
        demandeDeTraductionRepository.saveAndFlush(demandeDeTraduction);

        int databaseSizeBeforeUpdate = demandeDeTraductionRepository.findAll().size();

        // Update the demandeDeTraduction
        DemandeDeTraduction updatedDemandeDeTraduction = demandeDeTraductionRepository.findById(demandeDeTraduction.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeDeTraduction are not directly saved in db
        em.detach(updatedDemandeDeTraduction);
        updatedDemandeDeTraduction
            .modeEnvoiPreconise(UPDATED_MODE_ENVOI_PRECONISE)
            .modeLivraisonExige(UPDATED_MODE_LIVRAISON_EXIGE)
            .delaiDeTraitemenSouhaite(UPDATED_DELAI_DE_TRAITEMEN_SOUHAITE)
            .adresseLivraison(UPDATED_ADRESSE_LIVRAISON)
            .delaiDeTraitemenPrestataire(UPDATED_DELAI_DE_TRAITEMEN_PRESTATAIRE)
            .observation(UPDATED_OBSERVATION)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .etat(UPDATED_ETAT);

        restDemandeDeTraductionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemandeDeTraduction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemandeDeTraduction))
            )
            .andExpect(status().isOk());

        // Validate the DemandeDeTraduction in the database
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeUpdate);
        DemandeDeTraduction testDemandeDeTraduction = demandeDeTraductionList.get(demandeDeTraductionList.size() - 1);
        assertThat(testDemandeDeTraduction.getModeEnvoiPreconise()).isEqualTo(UPDATED_MODE_ENVOI_PRECONISE);
        assertThat(testDemandeDeTraduction.getModeLivraisonExige()).isEqualTo(UPDATED_MODE_LIVRAISON_EXIGE);
        assertThat(testDemandeDeTraduction.getDelaiDeTraitemenSouhaite()).isEqualTo(UPDATED_DELAI_DE_TRAITEMEN_SOUHAITE);
        assertThat(testDemandeDeTraduction.getAdresseLivraison()).isEqualTo(UPDATED_ADRESSE_LIVRAISON);
        assertThat(testDemandeDeTraduction.getDelaiDeTraitemenPrestataire()).isEqualTo(UPDATED_DELAI_DE_TRAITEMEN_PRESTATAIRE);
        assertThat(testDemandeDeTraduction.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testDemandeDeTraduction.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDemandeDeTraduction.getDateCloture()).isEqualTo(UPDATED_DATE_CLOTURE);
        assertThat(testDemandeDeTraduction.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void putNonExistingDemandeDeTraduction() throws Exception {
        int databaseSizeBeforeUpdate = demandeDeTraductionRepository.findAll().size();
        demandeDeTraduction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeDeTraductionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeDeTraduction.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeDeTraduction))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeDeTraduction in the database
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeDeTraduction() throws Exception {
        int databaseSizeBeforeUpdate = demandeDeTraductionRepository.findAll().size();
        demandeDeTraduction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeDeTraductionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeDeTraduction))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeDeTraduction in the database
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeDeTraduction() throws Exception {
        int databaseSizeBeforeUpdate = demandeDeTraductionRepository.findAll().size();
        demandeDeTraduction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeDeTraductionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeDeTraduction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeDeTraduction in the database
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeDeTraductionWithPatch() throws Exception {
        // Initialize the database
        demandeDeTraductionRepository.saveAndFlush(demandeDeTraduction);

        int databaseSizeBeforeUpdate = demandeDeTraductionRepository.findAll().size();

        // Update the demandeDeTraduction using partial update
        DemandeDeTraduction partialUpdatedDemandeDeTraduction = new DemandeDeTraduction();
        partialUpdatedDemandeDeTraduction.setId(demandeDeTraduction.getId());

        partialUpdatedDemandeDeTraduction
            .modeEnvoiPreconise(UPDATED_MODE_ENVOI_PRECONISE)
            .modeLivraisonExige(UPDATED_MODE_LIVRAISON_EXIGE)
            .delaiDeTraitemenSouhaite(UPDATED_DELAI_DE_TRAITEMEN_SOUHAITE)
            .delaiDeTraitemenPrestataire(UPDATED_DELAI_DE_TRAITEMEN_PRESTATAIRE)
            .dateCreation(UPDATED_DATE_CREATION)
            .etat(UPDATED_ETAT);

        restDemandeDeTraductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeDeTraduction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeDeTraduction))
            )
            .andExpect(status().isOk());

        // Validate the DemandeDeTraduction in the database
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeUpdate);
        DemandeDeTraduction testDemandeDeTraduction = demandeDeTraductionList.get(demandeDeTraductionList.size() - 1);
        assertThat(testDemandeDeTraduction.getModeEnvoiPreconise()).isEqualTo(UPDATED_MODE_ENVOI_PRECONISE);
        assertThat(testDemandeDeTraduction.getModeLivraisonExige()).isEqualTo(UPDATED_MODE_LIVRAISON_EXIGE);
        assertThat(testDemandeDeTraduction.getDelaiDeTraitemenSouhaite()).isEqualTo(UPDATED_DELAI_DE_TRAITEMEN_SOUHAITE);
        assertThat(testDemandeDeTraduction.getAdresseLivraison()).isEqualTo(DEFAULT_ADRESSE_LIVRAISON);
        assertThat(testDemandeDeTraduction.getDelaiDeTraitemenPrestataire()).isEqualTo(UPDATED_DELAI_DE_TRAITEMEN_PRESTATAIRE);
        assertThat(testDemandeDeTraduction.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testDemandeDeTraduction.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDemandeDeTraduction.getDateCloture()).isEqualTo(DEFAULT_DATE_CLOTURE);
        assertThat(testDemandeDeTraduction.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void fullUpdateDemandeDeTraductionWithPatch() throws Exception {
        // Initialize the database
        demandeDeTraductionRepository.saveAndFlush(demandeDeTraduction);

        int databaseSizeBeforeUpdate = demandeDeTraductionRepository.findAll().size();

        // Update the demandeDeTraduction using partial update
        DemandeDeTraduction partialUpdatedDemandeDeTraduction = new DemandeDeTraduction();
        partialUpdatedDemandeDeTraduction.setId(demandeDeTraduction.getId());

        partialUpdatedDemandeDeTraduction
            .modeEnvoiPreconise(UPDATED_MODE_ENVOI_PRECONISE)
            .modeLivraisonExige(UPDATED_MODE_LIVRAISON_EXIGE)
            .delaiDeTraitemenSouhaite(UPDATED_DELAI_DE_TRAITEMEN_SOUHAITE)
            .adresseLivraison(UPDATED_ADRESSE_LIVRAISON)
            .delaiDeTraitemenPrestataire(UPDATED_DELAI_DE_TRAITEMEN_PRESTATAIRE)
            .observation(UPDATED_OBSERVATION)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .etat(UPDATED_ETAT);

        restDemandeDeTraductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeDeTraduction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeDeTraduction))
            )
            .andExpect(status().isOk());

        // Validate the DemandeDeTraduction in the database
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeUpdate);
        DemandeDeTraduction testDemandeDeTraduction = demandeDeTraductionList.get(demandeDeTraductionList.size() - 1);
        assertThat(testDemandeDeTraduction.getModeEnvoiPreconise()).isEqualTo(UPDATED_MODE_ENVOI_PRECONISE);
        assertThat(testDemandeDeTraduction.getModeLivraisonExige()).isEqualTo(UPDATED_MODE_LIVRAISON_EXIGE);
        assertThat(testDemandeDeTraduction.getDelaiDeTraitemenSouhaite()).isEqualTo(UPDATED_DELAI_DE_TRAITEMEN_SOUHAITE);
        assertThat(testDemandeDeTraduction.getAdresseLivraison()).isEqualTo(UPDATED_ADRESSE_LIVRAISON);
        assertThat(testDemandeDeTraduction.getDelaiDeTraitemenPrestataire()).isEqualTo(UPDATED_DELAI_DE_TRAITEMEN_PRESTATAIRE);
        assertThat(testDemandeDeTraduction.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testDemandeDeTraduction.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDemandeDeTraduction.getDateCloture()).isEqualTo(UPDATED_DATE_CLOTURE);
        assertThat(testDemandeDeTraduction.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeDeTraduction() throws Exception {
        int databaseSizeBeforeUpdate = demandeDeTraductionRepository.findAll().size();
        demandeDeTraduction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeDeTraductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeDeTraduction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeDeTraduction))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeDeTraduction in the database
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeDeTraduction() throws Exception {
        int databaseSizeBeforeUpdate = demandeDeTraductionRepository.findAll().size();
        demandeDeTraduction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeDeTraductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeDeTraduction))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeDeTraduction in the database
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeDeTraduction() throws Exception {
        int databaseSizeBeforeUpdate = demandeDeTraductionRepository.findAll().size();
        demandeDeTraduction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeDeTraductionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeDeTraduction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeDeTraduction in the database
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeDeTraduction() throws Exception {
        // Initialize the database
        demandeDeTraductionRepository.saveAndFlush(demandeDeTraduction);

        int databaseSizeBeforeDelete = demandeDeTraductionRepository.findAll().size();

        // Delete the demandeDeTraduction
        restDemandeDeTraductionMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeDeTraduction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeDeTraduction> demandeDeTraductionList = demandeDeTraductionRepository.findAll();
        assertThat(demandeDeTraductionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
