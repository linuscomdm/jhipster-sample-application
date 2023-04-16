package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Prestataire;
import com.mycompany.myapp.domain.enumeration.Civilite;
import com.mycompany.myapp.domain.enumeration.EtatPrestataire;
import com.mycompany.myapp.domain.enumeration.TypeIdentiteProfessionnelle;
import com.mycompany.myapp.repository.PrestataireRepository;
import com.mycompany.myapp.service.PrestataireService;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PrestataireResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PrestataireResourceIT {

    private static final Civilite DEFAULT_CIVILITE = Civilite.Madame;
    private static final Civilite UPDATED_CIVILITE = Civilite.Monsieur;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COMMERCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COMMERCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_TRAVAIL = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_TRAVAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "8nPs6@qF00o.8iy.4EbL.3_dN";
    private static final String UPDATED_EMAIL = "J7CJ@h.SPC";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO_DE_PROFIL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_DE_PROFIL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_DE_PROFIL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_DE_PROFIL_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NUMERO_PIECE_IDENTITE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PIECE_IDENTITE = "BBBBBBBBBB";

    private static final TypeIdentiteProfessionnelle DEFAULT_TYPE_IDENTITE_PROFESSIONNELLE = TypeIdentiteProfessionnelle.MATRIULEFISCAL;
    private static final TypeIdentiteProfessionnelle UPDATED_TYPE_IDENTITE_PROFESSIONNELLE = TypeIdentiteProfessionnelle.CARTEPROFESSIONNEL;

    private static final byte[] DEFAULT_RATTACH_IDENTITE_PRO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RATTACH_IDENTITE_PRO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RATTACH_IDENTITE_PRO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RATTACH_IDENTITE_PRO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_COORDONNEES_BANCAIRES = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COORDONNEES_BANCAIRES = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COORDONNEES_BANCAIRES_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COORDONNEES_BANCAIRES_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TITULAIRE_DU_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_TITULAIRE_DU_COMPTE = "BBBBBBBBBB";

    private static final String DEFAULT_RIB_OU_RIP = "AAAAAAAAAA";
    private static final String UPDATED_RIB_OU_RIP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final EtatPrestataire DEFAULT_ETAT = EtatPrestataire.Actif;
    private static final EtatPrestataire UPDATED_ETAT = EtatPrestataire.Inactif;

    private static final String ENTITY_API_URL = "/api/prestataires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrestataireRepository prestataireRepository;

    @Mock
    private PrestataireRepository prestataireRepositoryMock;

    @Mock
    private PrestataireService prestataireServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrestataireMockMvc;

    private Prestataire prestataire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prestataire createEntity(EntityManager em) {
        Prestataire prestataire = new Prestataire()
            .civilite(DEFAULT_CIVILITE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .nomCommercial(DEFAULT_NOM_COMMERCIAL)
            .telephoneTravail(DEFAULT_TELEPHONE_TRAVAIL)
            .telephoneMobile(DEFAULT_TELEPHONE_MOBILE)
            .email(DEFAULT_EMAIL)
            .adresse(DEFAULT_ADRESSE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .photoDeProfil(DEFAULT_PHOTO_DE_PROFIL)
            .photoDeProfilContentType(DEFAULT_PHOTO_DE_PROFIL_CONTENT_TYPE)
            .numeroPieceIdentite(DEFAULT_NUMERO_PIECE_IDENTITE)
            .typeIdentiteProfessionnelle(DEFAULT_TYPE_IDENTITE_PROFESSIONNELLE)
            .rattachIdentitePro(DEFAULT_RATTACH_IDENTITE_PRO)
            .rattachIdentiteProContentType(DEFAULT_RATTACH_IDENTITE_PRO_CONTENT_TYPE)
            .coordonneesBancaires(DEFAULT_COORDONNEES_BANCAIRES)
            .coordonneesBancairesContentType(DEFAULT_COORDONNEES_BANCAIRES_CONTENT_TYPE)
            .titulaireDuCompte(DEFAULT_TITULAIRE_DU_COMPTE)
            .ribOuRip(DEFAULT_RIB_OU_RIP)
            .dateCreation(DEFAULT_DATE_CREATION)
            .etat(DEFAULT_ETAT);
        return prestataire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prestataire createUpdatedEntity(EntityManager em) {
        Prestataire prestataire = new Prestataire()
            .civilite(UPDATED_CIVILITE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .nomCommercial(UPDATED_NOM_COMMERCIAL)
            .telephoneTravail(UPDATED_TELEPHONE_TRAVAIL)
            .telephoneMobile(UPDATED_TELEPHONE_MOBILE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .photoDeProfil(UPDATED_PHOTO_DE_PROFIL)
            .photoDeProfilContentType(UPDATED_PHOTO_DE_PROFIL_CONTENT_TYPE)
            .numeroPieceIdentite(UPDATED_NUMERO_PIECE_IDENTITE)
            .typeIdentiteProfessionnelle(UPDATED_TYPE_IDENTITE_PROFESSIONNELLE)
            .rattachIdentitePro(UPDATED_RATTACH_IDENTITE_PRO)
            .rattachIdentiteProContentType(UPDATED_RATTACH_IDENTITE_PRO_CONTENT_TYPE)
            .coordonneesBancaires(UPDATED_COORDONNEES_BANCAIRES)
            .coordonneesBancairesContentType(UPDATED_COORDONNEES_BANCAIRES_CONTENT_TYPE)
            .titulaireDuCompte(UPDATED_TITULAIRE_DU_COMPTE)
            .ribOuRip(UPDATED_RIB_OU_RIP)
            .dateCreation(UPDATED_DATE_CREATION)
            .etat(UPDATED_ETAT);
        return prestataire;
    }

    @BeforeEach
    public void initTest() {
        prestataire = createEntity(em);
    }

    @Test
    @Transactional
    void createPrestataire() throws Exception {
        int databaseSizeBeforeCreate = prestataireRepository.findAll().size();
        // Create the Prestataire
        restPrestataireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isCreated());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeCreate + 1);
        Prestataire testPrestataire = prestataireList.get(prestataireList.size() - 1);
        assertThat(testPrestataire.getCivilite()).isEqualTo(DEFAULT_CIVILITE);
        assertThat(testPrestataire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPrestataire.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPrestataire.getNomCommercial()).isEqualTo(DEFAULT_NOM_COMMERCIAL);
        assertThat(testPrestataire.getTelephoneTravail()).isEqualTo(DEFAULT_TELEPHONE_TRAVAIL);
        assertThat(testPrestataire.getTelephoneMobile()).isEqualTo(DEFAULT_TELEPHONE_MOBILE);
        assertThat(testPrestataire.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPrestataire.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testPrestataire.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testPrestataire.getPhotoDeProfil()).isEqualTo(DEFAULT_PHOTO_DE_PROFIL);
        assertThat(testPrestataire.getPhotoDeProfilContentType()).isEqualTo(DEFAULT_PHOTO_DE_PROFIL_CONTENT_TYPE);
        assertThat(testPrestataire.getNumeroPieceIdentite()).isEqualTo(DEFAULT_NUMERO_PIECE_IDENTITE);
        assertThat(testPrestataire.getTypeIdentiteProfessionnelle()).isEqualTo(DEFAULT_TYPE_IDENTITE_PROFESSIONNELLE);
        assertThat(testPrestataire.getRattachIdentitePro()).isEqualTo(DEFAULT_RATTACH_IDENTITE_PRO);
        assertThat(testPrestataire.getRattachIdentiteProContentType()).isEqualTo(DEFAULT_RATTACH_IDENTITE_PRO_CONTENT_TYPE);
        assertThat(testPrestataire.getCoordonneesBancaires()).isEqualTo(DEFAULT_COORDONNEES_BANCAIRES);
        assertThat(testPrestataire.getCoordonneesBancairesContentType()).isEqualTo(DEFAULT_COORDONNEES_BANCAIRES_CONTENT_TYPE);
        assertThat(testPrestataire.getTitulaireDuCompte()).isEqualTo(DEFAULT_TITULAIRE_DU_COMPTE);
        assertThat(testPrestataire.getRibOuRip()).isEqualTo(DEFAULT_RIB_OU_RIP);
        assertThat(testPrestataire.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testPrestataire.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void createPrestataireWithExistingId() throws Exception {
        // Create the Prestataire with an existing ID
        prestataire.setId(1L);

        int databaseSizeBeforeCreate = prestataireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrestataireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isBadRequest());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestataireRepository.findAll().size();
        // set the field null
        prestataire.setNom(null);

        // Create the Prestataire, which fails.

        restPrestataireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isBadRequest());

        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestataireRepository.findAll().size();
        // set the field null
        prestataire.setPrenom(null);

        // Create the Prestataire, which fails.

        restPrestataireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isBadRequest());

        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelephoneMobileIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestataireRepository.findAll().size();
        // set the field null
        prestataire.setTelephoneMobile(null);

        // Create the Prestataire, which fails.

        restPrestataireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isBadRequest());

        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestataireRepository.findAll().size();
        // set the field null
        prestataire.setEmail(null);

        // Create the Prestataire, which fails.

        restPrestataireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isBadRequest());

        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroPieceIdentiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestataireRepository.findAll().size();
        // set the field null
        prestataire.setNumeroPieceIdentite(null);

        // Create the Prestataire, which fails.

        restPrestataireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isBadRequest());

        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPrestataires() throws Exception {
        // Initialize the database
        prestataireRepository.saveAndFlush(prestataire);

        // Get all the prestataireList
        restPrestataireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestataire.getId().intValue())))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nomCommercial").value(hasItem(DEFAULT_NOM_COMMERCIAL)))
            .andExpect(jsonPath("$.[*].telephoneTravail").value(hasItem(DEFAULT_TELEPHONE_TRAVAIL)))
            .andExpect(jsonPath("$.[*].telephoneMobile").value(hasItem(DEFAULT_TELEPHONE_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].photoDeProfilContentType").value(hasItem(DEFAULT_PHOTO_DE_PROFIL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photoDeProfil").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_DE_PROFIL))))
            .andExpect(jsonPath("$.[*].numeroPieceIdentite").value(hasItem(DEFAULT_NUMERO_PIECE_IDENTITE)))
            .andExpect(jsonPath("$.[*].typeIdentiteProfessionnelle").value(hasItem(DEFAULT_TYPE_IDENTITE_PROFESSIONNELLE.toString())))
            .andExpect(jsonPath("$.[*].rattachIdentiteProContentType").value(hasItem(DEFAULT_RATTACH_IDENTITE_PRO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rattachIdentitePro").value(hasItem(Base64Utils.encodeToString(DEFAULT_RATTACH_IDENTITE_PRO))))
            .andExpect(jsonPath("$.[*].coordonneesBancairesContentType").value(hasItem(DEFAULT_COORDONNEES_BANCAIRES_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].coordonneesBancaires").value(hasItem(Base64Utils.encodeToString(DEFAULT_COORDONNEES_BANCAIRES))))
            .andExpect(jsonPath("$.[*].titulaireDuCompte").value(hasItem(DEFAULT_TITULAIRE_DU_COMPTE)))
            .andExpect(jsonPath("$.[*].ribOuRip").value(hasItem(DEFAULT_RIB_OU_RIP)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPrestatairesWithEagerRelationshipsIsEnabled() throws Exception {
        when(prestataireServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPrestataireMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(prestataireServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPrestatairesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(prestataireServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPrestataireMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(prestataireRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPrestataire() throws Exception {
        // Initialize the database
        prestataireRepository.saveAndFlush(prestataire);

        // Get the prestataire
        restPrestataireMockMvc
            .perform(get(ENTITY_API_URL_ID, prestataire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prestataire.getId().intValue()))
            .andExpect(jsonPath("$.civilite").value(DEFAULT_CIVILITE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nomCommercial").value(DEFAULT_NOM_COMMERCIAL))
            .andExpect(jsonPath("$.telephoneTravail").value(DEFAULT_TELEPHONE_TRAVAIL))
            .andExpect(jsonPath("$.telephoneMobile").value(DEFAULT_TELEPHONE_MOBILE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.photoDeProfilContentType").value(DEFAULT_PHOTO_DE_PROFIL_CONTENT_TYPE))
            .andExpect(jsonPath("$.photoDeProfil").value(Base64Utils.encodeToString(DEFAULT_PHOTO_DE_PROFIL)))
            .andExpect(jsonPath("$.numeroPieceIdentite").value(DEFAULT_NUMERO_PIECE_IDENTITE))
            .andExpect(jsonPath("$.typeIdentiteProfessionnelle").value(DEFAULT_TYPE_IDENTITE_PROFESSIONNELLE.toString()))
            .andExpect(jsonPath("$.rattachIdentiteProContentType").value(DEFAULT_RATTACH_IDENTITE_PRO_CONTENT_TYPE))
            .andExpect(jsonPath("$.rattachIdentitePro").value(Base64Utils.encodeToString(DEFAULT_RATTACH_IDENTITE_PRO)))
            .andExpect(jsonPath("$.coordonneesBancairesContentType").value(DEFAULT_COORDONNEES_BANCAIRES_CONTENT_TYPE))
            .andExpect(jsonPath("$.coordonneesBancaires").value(Base64Utils.encodeToString(DEFAULT_COORDONNEES_BANCAIRES)))
            .andExpect(jsonPath("$.titulaireDuCompte").value(DEFAULT_TITULAIRE_DU_COMPTE))
            .andExpect(jsonPath("$.ribOuRip").value(DEFAULT_RIB_OU_RIP))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPrestataire() throws Exception {
        // Get the prestataire
        restPrestataireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrestataire() throws Exception {
        // Initialize the database
        prestataireRepository.saveAndFlush(prestataire);

        int databaseSizeBeforeUpdate = prestataireRepository.findAll().size();

        // Update the prestataire
        Prestataire updatedPrestataire = prestataireRepository.findById(prestataire.getId()).get();
        // Disconnect from session so that the updates on updatedPrestataire are not directly saved in db
        em.detach(updatedPrestataire);
        updatedPrestataire
            .civilite(UPDATED_CIVILITE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .nomCommercial(UPDATED_NOM_COMMERCIAL)
            .telephoneTravail(UPDATED_TELEPHONE_TRAVAIL)
            .telephoneMobile(UPDATED_TELEPHONE_MOBILE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .photoDeProfil(UPDATED_PHOTO_DE_PROFIL)
            .photoDeProfilContentType(UPDATED_PHOTO_DE_PROFIL_CONTENT_TYPE)
            .numeroPieceIdentite(UPDATED_NUMERO_PIECE_IDENTITE)
            .typeIdentiteProfessionnelle(UPDATED_TYPE_IDENTITE_PROFESSIONNELLE)
            .rattachIdentitePro(UPDATED_RATTACH_IDENTITE_PRO)
            .rattachIdentiteProContentType(UPDATED_RATTACH_IDENTITE_PRO_CONTENT_TYPE)
            .coordonneesBancaires(UPDATED_COORDONNEES_BANCAIRES)
            .coordonneesBancairesContentType(UPDATED_COORDONNEES_BANCAIRES_CONTENT_TYPE)
            .titulaireDuCompte(UPDATED_TITULAIRE_DU_COMPTE)
            .ribOuRip(UPDATED_RIB_OU_RIP)
            .dateCreation(UPDATED_DATE_CREATION)
            .etat(UPDATED_ETAT);

        restPrestataireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrestataire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrestataire))
            )
            .andExpect(status().isOk());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeUpdate);
        Prestataire testPrestataire = prestataireList.get(prestataireList.size() - 1);
        assertThat(testPrestataire.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testPrestataire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPrestataire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPrestataire.getNomCommercial()).isEqualTo(UPDATED_NOM_COMMERCIAL);
        assertThat(testPrestataire.getTelephoneTravail()).isEqualTo(UPDATED_TELEPHONE_TRAVAIL);
        assertThat(testPrestataire.getTelephoneMobile()).isEqualTo(UPDATED_TELEPHONE_MOBILE);
        assertThat(testPrestataire.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPrestataire.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPrestataire.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testPrestataire.getPhotoDeProfil()).isEqualTo(UPDATED_PHOTO_DE_PROFIL);
        assertThat(testPrestataire.getPhotoDeProfilContentType()).isEqualTo(UPDATED_PHOTO_DE_PROFIL_CONTENT_TYPE);
        assertThat(testPrestataire.getNumeroPieceIdentite()).isEqualTo(UPDATED_NUMERO_PIECE_IDENTITE);
        assertThat(testPrestataire.getTypeIdentiteProfessionnelle()).isEqualTo(UPDATED_TYPE_IDENTITE_PROFESSIONNELLE);
        assertThat(testPrestataire.getRattachIdentitePro()).isEqualTo(UPDATED_RATTACH_IDENTITE_PRO);
        assertThat(testPrestataire.getRattachIdentiteProContentType()).isEqualTo(UPDATED_RATTACH_IDENTITE_PRO_CONTENT_TYPE);
        assertThat(testPrestataire.getCoordonneesBancaires()).isEqualTo(UPDATED_COORDONNEES_BANCAIRES);
        assertThat(testPrestataire.getCoordonneesBancairesContentType()).isEqualTo(UPDATED_COORDONNEES_BANCAIRES_CONTENT_TYPE);
        assertThat(testPrestataire.getTitulaireDuCompte()).isEqualTo(UPDATED_TITULAIRE_DU_COMPTE);
        assertThat(testPrestataire.getRibOuRip()).isEqualTo(UPDATED_RIB_OU_RIP);
        assertThat(testPrestataire.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testPrestataire.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void putNonExistingPrestataire() throws Exception {
        int databaseSizeBeforeUpdate = prestataireRepository.findAll().size();
        prestataire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrestataireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prestataire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prestataire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrestataire() throws Exception {
        int databaseSizeBeforeUpdate = prestataireRepository.findAll().size();
        prestataire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrestataireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prestataire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrestataire() throws Exception {
        int databaseSizeBeforeUpdate = prestataireRepository.findAll().size();
        prestataire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrestataireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prestataire)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrestataireWithPatch() throws Exception {
        // Initialize the database
        prestataireRepository.saveAndFlush(prestataire);

        int databaseSizeBeforeUpdate = prestataireRepository.findAll().size();

        // Update the prestataire using partial update
        Prestataire partialUpdatedPrestataire = new Prestataire();
        partialUpdatedPrestataire.setId(prestataire.getId());

        partialUpdatedPrestataire
            .civilite(UPDATED_CIVILITE)
            .prenom(UPDATED_PRENOM)
            .nomCommercial(UPDATED_NOM_COMMERCIAL)
            .telephoneTravail(UPDATED_TELEPHONE_TRAVAIL)
            .adresse(UPDATED_ADRESSE)
            .photoDeProfil(UPDATED_PHOTO_DE_PROFIL)
            .photoDeProfilContentType(UPDATED_PHOTO_DE_PROFIL_CONTENT_TYPE)
            .numeroPieceIdentite(UPDATED_NUMERO_PIECE_IDENTITE)
            .rattachIdentitePro(UPDATED_RATTACH_IDENTITE_PRO)
            .rattachIdentiteProContentType(UPDATED_RATTACH_IDENTITE_PRO_CONTENT_TYPE)
            .coordonneesBancaires(UPDATED_COORDONNEES_BANCAIRES)
            .coordonneesBancairesContentType(UPDATED_COORDONNEES_BANCAIRES_CONTENT_TYPE)
            .titulaireDuCompte(UPDATED_TITULAIRE_DU_COMPTE)
            .ribOuRip(UPDATED_RIB_OU_RIP)
            .dateCreation(UPDATED_DATE_CREATION);

        restPrestataireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrestataire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrestataire))
            )
            .andExpect(status().isOk());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeUpdate);
        Prestataire testPrestataire = prestataireList.get(prestataireList.size() - 1);
        assertThat(testPrestataire.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testPrestataire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPrestataire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPrestataire.getNomCommercial()).isEqualTo(UPDATED_NOM_COMMERCIAL);
        assertThat(testPrestataire.getTelephoneTravail()).isEqualTo(UPDATED_TELEPHONE_TRAVAIL);
        assertThat(testPrestataire.getTelephoneMobile()).isEqualTo(DEFAULT_TELEPHONE_MOBILE);
        assertThat(testPrestataire.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPrestataire.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPrestataire.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testPrestataire.getPhotoDeProfil()).isEqualTo(UPDATED_PHOTO_DE_PROFIL);
        assertThat(testPrestataire.getPhotoDeProfilContentType()).isEqualTo(UPDATED_PHOTO_DE_PROFIL_CONTENT_TYPE);
        assertThat(testPrestataire.getNumeroPieceIdentite()).isEqualTo(UPDATED_NUMERO_PIECE_IDENTITE);
        assertThat(testPrestataire.getTypeIdentiteProfessionnelle()).isEqualTo(DEFAULT_TYPE_IDENTITE_PROFESSIONNELLE);
        assertThat(testPrestataire.getRattachIdentitePro()).isEqualTo(UPDATED_RATTACH_IDENTITE_PRO);
        assertThat(testPrestataire.getRattachIdentiteProContentType()).isEqualTo(UPDATED_RATTACH_IDENTITE_PRO_CONTENT_TYPE);
        assertThat(testPrestataire.getCoordonneesBancaires()).isEqualTo(UPDATED_COORDONNEES_BANCAIRES);
        assertThat(testPrestataire.getCoordonneesBancairesContentType()).isEqualTo(UPDATED_COORDONNEES_BANCAIRES_CONTENT_TYPE);
        assertThat(testPrestataire.getTitulaireDuCompte()).isEqualTo(UPDATED_TITULAIRE_DU_COMPTE);
        assertThat(testPrestataire.getRibOuRip()).isEqualTo(UPDATED_RIB_OU_RIP);
        assertThat(testPrestataire.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testPrestataire.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void fullUpdatePrestataireWithPatch() throws Exception {
        // Initialize the database
        prestataireRepository.saveAndFlush(prestataire);

        int databaseSizeBeforeUpdate = prestataireRepository.findAll().size();

        // Update the prestataire using partial update
        Prestataire partialUpdatedPrestataire = new Prestataire();
        partialUpdatedPrestataire.setId(prestataire.getId());

        partialUpdatedPrestataire
            .civilite(UPDATED_CIVILITE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .nomCommercial(UPDATED_NOM_COMMERCIAL)
            .telephoneTravail(UPDATED_TELEPHONE_TRAVAIL)
            .telephoneMobile(UPDATED_TELEPHONE_MOBILE)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .photoDeProfil(UPDATED_PHOTO_DE_PROFIL)
            .photoDeProfilContentType(UPDATED_PHOTO_DE_PROFIL_CONTENT_TYPE)
            .numeroPieceIdentite(UPDATED_NUMERO_PIECE_IDENTITE)
            .typeIdentiteProfessionnelle(UPDATED_TYPE_IDENTITE_PROFESSIONNELLE)
            .rattachIdentitePro(UPDATED_RATTACH_IDENTITE_PRO)
            .rattachIdentiteProContentType(UPDATED_RATTACH_IDENTITE_PRO_CONTENT_TYPE)
            .coordonneesBancaires(UPDATED_COORDONNEES_BANCAIRES)
            .coordonneesBancairesContentType(UPDATED_COORDONNEES_BANCAIRES_CONTENT_TYPE)
            .titulaireDuCompte(UPDATED_TITULAIRE_DU_COMPTE)
            .ribOuRip(UPDATED_RIB_OU_RIP)
            .dateCreation(UPDATED_DATE_CREATION)
            .etat(UPDATED_ETAT);

        restPrestataireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrestataire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrestataire))
            )
            .andExpect(status().isOk());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeUpdate);
        Prestataire testPrestataire = prestataireList.get(prestataireList.size() - 1);
        assertThat(testPrestataire.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testPrestataire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPrestataire.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPrestataire.getNomCommercial()).isEqualTo(UPDATED_NOM_COMMERCIAL);
        assertThat(testPrestataire.getTelephoneTravail()).isEqualTo(UPDATED_TELEPHONE_TRAVAIL);
        assertThat(testPrestataire.getTelephoneMobile()).isEqualTo(UPDATED_TELEPHONE_MOBILE);
        assertThat(testPrestataire.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPrestataire.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testPrestataire.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testPrestataire.getPhotoDeProfil()).isEqualTo(UPDATED_PHOTO_DE_PROFIL);
        assertThat(testPrestataire.getPhotoDeProfilContentType()).isEqualTo(UPDATED_PHOTO_DE_PROFIL_CONTENT_TYPE);
        assertThat(testPrestataire.getNumeroPieceIdentite()).isEqualTo(UPDATED_NUMERO_PIECE_IDENTITE);
        assertThat(testPrestataire.getTypeIdentiteProfessionnelle()).isEqualTo(UPDATED_TYPE_IDENTITE_PROFESSIONNELLE);
        assertThat(testPrestataire.getRattachIdentitePro()).isEqualTo(UPDATED_RATTACH_IDENTITE_PRO);
        assertThat(testPrestataire.getRattachIdentiteProContentType()).isEqualTo(UPDATED_RATTACH_IDENTITE_PRO_CONTENT_TYPE);
        assertThat(testPrestataire.getCoordonneesBancaires()).isEqualTo(UPDATED_COORDONNEES_BANCAIRES);
        assertThat(testPrestataire.getCoordonneesBancairesContentType()).isEqualTo(UPDATED_COORDONNEES_BANCAIRES_CONTENT_TYPE);
        assertThat(testPrestataire.getTitulaireDuCompte()).isEqualTo(UPDATED_TITULAIRE_DU_COMPTE);
        assertThat(testPrestataire.getRibOuRip()).isEqualTo(UPDATED_RIB_OU_RIP);
        assertThat(testPrestataire.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testPrestataire.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void patchNonExistingPrestataire() throws Exception {
        int databaseSizeBeforeUpdate = prestataireRepository.findAll().size();
        prestataire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrestataireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prestataire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prestataire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrestataire() throws Exception {
        int databaseSizeBeforeUpdate = prestataireRepository.findAll().size();
        prestataire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrestataireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prestataire))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrestataire() throws Exception {
        int databaseSizeBeforeUpdate = prestataireRepository.findAll().size();
        prestataire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrestataireMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(prestataire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prestataire in the database
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrestataire() throws Exception {
        // Initialize the database
        prestataireRepository.saveAndFlush(prestataire);

        int databaseSizeBeforeDelete = prestataireRepository.findAll().size();

        // Delete the prestataire
        restPrestataireMockMvc
            .perform(delete(ENTITY_API_URL_ID, prestataire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prestataire> prestataireList = prestataireRepository.findAll();
        assertThat(prestataireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
