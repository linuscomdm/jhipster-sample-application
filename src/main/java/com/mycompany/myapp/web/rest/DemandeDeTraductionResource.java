package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DemandeDeTraduction;
import com.mycompany.myapp.repository.DemandeDeTraductionRepository;
import com.mycompany.myapp.service.DemandeDeTraductionService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DemandeDeTraduction}.
 */
@RestController
@RequestMapping("/api")
public class DemandeDeTraductionResource {

    private final Logger log = LoggerFactory.getLogger(DemandeDeTraductionResource.class);

    private static final String ENTITY_NAME = "demandeDeTraduction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeDeTraductionService demandeDeTraductionService;

    private final DemandeDeTraductionRepository demandeDeTraductionRepository;

    public DemandeDeTraductionResource(
        DemandeDeTraductionService demandeDeTraductionService,
        DemandeDeTraductionRepository demandeDeTraductionRepository
    ) {
        this.demandeDeTraductionService = demandeDeTraductionService;
        this.demandeDeTraductionRepository = demandeDeTraductionRepository;
    }

    /**
     * {@code POST  /demande-de-traductions} : Create a new demandeDeTraduction.
     *
     * @param demandeDeTraduction the demandeDeTraduction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeDeTraduction, or with status {@code 400 (Bad Request)} if the demandeDeTraduction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demande-de-traductions")
    public ResponseEntity<DemandeDeTraduction> createDemandeDeTraduction(@Valid @RequestBody DemandeDeTraduction demandeDeTraduction)
        throws URISyntaxException {
        log.debug("REST request to save DemandeDeTraduction : {}", demandeDeTraduction);
        if (demandeDeTraduction.getId() != null) {
            throw new BadRequestAlertException("A new demandeDeTraduction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeDeTraduction result = demandeDeTraductionService.save(demandeDeTraduction);
        return ResponseEntity
            .created(new URI("/api/demande-de-traductions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demande-de-traductions/:id} : Updates an existing demandeDeTraduction.
     *
     * @param id the id of the demandeDeTraduction to save.
     * @param demandeDeTraduction the demandeDeTraduction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeDeTraduction,
     * or with status {@code 400 (Bad Request)} if the demandeDeTraduction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeDeTraduction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demande-de-traductions/{id}")
    public ResponseEntity<DemandeDeTraduction> updateDemandeDeTraduction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DemandeDeTraduction demandeDeTraduction
    ) throws URISyntaxException {
        log.debug("REST request to update DemandeDeTraduction : {}, {}", id, demandeDeTraduction);
        if (demandeDeTraduction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeDeTraduction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeDeTraductionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandeDeTraduction result = demandeDeTraductionService.update(demandeDeTraduction);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeDeTraduction.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demande-de-traductions/:id} : Partial updates given fields of an existing demandeDeTraduction, field will ignore if it is null
     *
     * @param id the id of the demandeDeTraduction to save.
     * @param demandeDeTraduction the demandeDeTraduction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeDeTraduction,
     * or with status {@code 400 (Bad Request)} if the demandeDeTraduction is not valid,
     * or with status {@code 404 (Not Found)} if the demandeDeTraduction is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeDeTraduction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demande-de-traductions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandeDeTraduction> partialUpdateDemandeDeTraduction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DemandeDeTraduction demandeDeTraduction
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandeDeTraduction partially : {}, {}", id, demandeDeTraduction);
        if (demandeDeTraduction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeDeTraduction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeDeTraductionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeDeTraduction> result = demandeDeTraductionService.partialUpdate(demandeDeTraduction);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeDeTraduction.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-de-traductions} : get all the demandeDeTraductions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeDeTraductions in body.
     */
    @GetMapping("/demande-de-traductions")
    public ResponseEntity<List<DemandeDeTraduction>> getAllDemandeDeTraductions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of DemandeDeTraductions");
        Page<DemandeDeTraduction> page;
        if (eagerload) {
            page = demandeDeTraductionService.findAllWithEagerRelationships(pageable);
        } else {
            page = demandeDeTraductionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demande-de-traductions/:id} : get the "id" demandeDeTraduction.
     *
     * @param id the id of the demandeDeTraduction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeDeTraduction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demande-de-traductions/{id}")
    public ResponseEntity<DemandeDeTraduction> getDemandeDeTraduction(@PathVariable Long id) {
        log.debug("REST request to get DemandeDeTraduction : {}", id);
        Optional<DemandeDeTraduction> demandeDeTraduction = demandeDeTraductionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeDeTraduction);
    }

    /**
     * {@code DELETE  /demande-de-traductions/:id} : delete the "id" demandeDeTraduction.
     *
     * @param id the id of the demandeDeTraduction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demande-de-traductions/{id}")
    public ResponseEntity<Void> deleteDemandeDeTraduction(@PathVariable Long id) {
        log.debug("REST request to delete DemandeDeTraduction : {}", id);
        demandeDeTraductionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
