package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Demandeur;
import com.mycompany.myapp.domain.enumeration.Civilite;
import com.mycompany.myapp.domain.enumeration.EtatDemandeur;
import com.mycompany.myapp.repository.DemandeurRepository;
import com.mycompany.myapp.service.DemandeurService;
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
 * Integration tests for the {@link DemandeurResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DemandeurResourceIT {

    private static final Civilite DEFAULT_CIVILITE = Civilite.Madame;
    private static final Civilite UPDATED_CIVILITE = Civilite.Monsieur;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "zyxQVn@aR8c.z1Gr.UQb27.0rpr.pxcxg.2HAD.xJ";
    private static final String UPDATED_EMAIL = "by@EV2UtZ.Udl.qc.s9lGE.S7Iqjp.Sd";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final EtatDemandeur DEFAULT_ETAT = EtatDemandeur.Actif;
    private static final EtatDemandeur UPDATED_ETAT = EtatDemandeur.Inactif;

    private static final String ENTITY_API_URL = "/api/demandeurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeurRepository demandeurRepository;

    @Mock
    private DemandeurRepository demandeurRepositoryMock;

    @Mock
    private DemandeurService demandeurServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeurMockMvc;

    private Demandeur demandeur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demandeur createEntity(EntityManager em) {
        Demandeur demandeur = new Demandeur()
            .civilite(DEFAULT_CIVILITE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL)
            .adresse(DEFAULT_ADRESSE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .etat(DEFAULT_ETAT);
        return demandeur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demandeur createUpdatedEntity(EntityManager em) {
        Demandeur demandeur = new Demandeur()
            .civilite(UPDATED_CIVILITE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .dateCreation(UPDATED_DATE_CREATION)
            .etat(UPDATED_ETAT);
        return demandeur;
    }

    @BeforeEach
    public void initTest() {
        demandeur = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeur() throws Exception {
        int databaseSizeBeforeCreate = demandeurRepository.findAll().size();
        // Create the Demandeur
        restDemandeurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeur)))
            .andExpect(status().isCreated());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeCreate + 1);
        Demandeur testDemandeur = demandeurList.get(demandeurList.size() - 1);
        assertThat(testDemandeur.getCivilite()).isEqualTo(DEFAULT_CIVILITE);
        assertThat(testDemandeur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDemandeur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDemandeur.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testDemandeur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDemandeur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDemandeur.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testDemandeur.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testDemandeur.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void createDemandeurWithExistingId() throws Exception {
        // Create the Demandeur with an existing ID
        demandeur.setId(1L);

        int databaseSizeBeforeCreate = demandeurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeur)))
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeurRepository.findAll().size();
        // set the field null
        demandeur.setNom(null);

        // Create the Demandeur, which fails.

        restDemandeurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeur)))
            .andExpect(status().isBadRequest());

        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeurRepository.findAll().size();
        // set the field null
        demandeur.setPrenom(null);

        // Create the Demandeur, which fails.

        restDemandeurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeur)))
            .andExpect(status().isBadRequest());

        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeurRepository.findAll().size();
        // set the field null
        demandeur.setTelephone(null);

        // Create the Demandeur, which fails.

        restDemandeurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeur)))
            .andExpect(status().isBadRequest());

        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeurRepository.findAll().size();
        // set the field null
        demandeur.setEmail(null);

        // Create the Demandeur, which fails.

        restDemandeurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeur)))
            .andExpect(status().isBadRequest());

        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemandeurs() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        // Get all the demandeurList
        restDemandeurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDemandeursWithEagerRelationshipsIsEnabled() throws Exception {
        when(demandeurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDemandeurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(demandeurServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDemandeursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(demandeurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDemandeurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(demandeurRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDemandeur() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        // Get the demandeur
        restDemandeurMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeur.getId().intValue()))
            .andExpect(jsonPath("$.civilite").value(DEFAULT_CIVILITE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDemandeur() throws Exception {
        // Get the demandeur
        restDemandeurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemandeur() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();

        // Update the demandeur
        Demandeur updatedDemandeur = demandeurRepository.findById(demandeur.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeur are not directly saved in db
        em.detach(updatedDemandeur);
        updatedDemandeur
            .civilite(UPDATED_CIVILITE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .dateCreation(UPDATED_DATE_CREATION)
            .etat(UPDATED_ETAT);

        restDemandeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemandeur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemandeur))
            )
            .andExpect(status().isOk());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
        Demandeur testDemandeur = demandeurList.get(demandeurList.size() - 1);
        assertThat(testDemandeur.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testDemandeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDemandeur.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testDemandeur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDemandeur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDemandeur.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDemandeur.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void putNonExistingDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeurWithPatch() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();

        // Update the demandeur using partial update
        Demandeur partialUpdatedDemandeur = new Demandeur();
        partialUpdatedDemandeur.setId(demandeur.getId());

        partialUpdatedDemandeur.civilite(UPDATED_CIVILITE).telephone(UPDATED_TELEPHONE).adresse(UPDATED_ADRESSE);

        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeur))
            )
            .andExpect(status().isOk());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
        Demandeur testDemandeur = demandeurList.get(demandeurList.size() - 1);
        assertThat(testDemandeur.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testDemandeur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDemandeur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDemandeur.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testDemandeur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDemandeur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDemandeur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDemandeur.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testDemandeur.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void fullUpdateDemandeurWithPatch() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();

        // Update the demandeur using partial update
        Demandeur partialUpdatedDemandeur = new Demandeur();
        partialUpdatedDemandeur.setId(demandeur.getId());

        partialUpdatedDemandeur
            .civilite(UPDATED_CIVILITE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .dateCreation(UPDATED_DATE_CREATION)
            .etat(UPDATED_ETAT);

        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeur))
            )
            .andExpect(status().isOk());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
        Demandeur testDemandeur = demandeurList.get(demandeurList.size() - 1);
        assertThat(testDemandeur.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testDemandeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDemandeur.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testDemandeur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDemandeur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDemandeur.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDemandeur.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeur() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        int databaseSizeBeforeDelete = demandeurRepository.findAll().size();

        // Delete the demandeur
        restDemandeurMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
