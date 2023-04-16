package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Devis;
import com.mycompany.myapp.repository.DevisRepository;
import com.mycompany.myapp.service.DevisService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Devis}.
 */
@RestController
@RequestMapping("/api")
public class DevisResource {

    private final Logger log = LoggerFactory.getLogger(DevisResource.class);

    private static final String ENTITY_NAME = "devis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DevisService devisService;

    private final DevisRepository devisRepository;

    public DevisResource(DevisService devisService, DevisRepository devisRepository) {
        this.devisService = devisService;
        this.devisRepository = devisRepository;
    }

    /**
     * {@code POST  /devis} : Create a new devis.
     *
     * @param devis the devis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new devis, or with status {@code 400 (Bad Request)} if the devis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/devis")
    public ResponseEntity<Devis> createDevis(@Valid @RequestBody Devis devis) throws URISyntaxException {
        log.debug("REST request to save Devis : {}", devis);
        if (devis.getId() != null) {
            throw new BadRequestAlertException("A new devis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Devis result = devisService.save(devis);
        return ResponseEntity
            .created(new URI("/api/devis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /devis/:id} : Updates an existing devis.
     *
     * @param id the id of the devis to save.
     * @param devis the devis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated devis,
     * or with status {@code 400 (Bad Request)} if the devis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the devis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/devis/{id}")
    public ResponseEntity<Devis> updateDevis(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Devis devis)
        throws URISyntaxException {
        log.debug("REST request to update Devis : {}, {}", id, devis);
        if (devis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, devis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!devisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Devis result = devisService.update(devis);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, devis.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /devis/:id} : Partial updates given fields of an existing devis, field will ignore if it is null
     *
     * @param id the id of the devis to save.
     * @param devis the devis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated devis,
     * or with status {@code 400 (Bad Request)} if the devis is not valid,
     * or with status {@code 404 (Not Found)} if the devis is not found,
     * or with status {@code 500 (Internal Server Error)} if the devis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/devis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Devis> partialUpdateDevis(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Devis devis
    ) throws URISyntaxException {
        log.debug("REST request to partial update Devis partially : {}, {}", id, devis);
        if (devis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, devis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!devisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Devis> result = devisService.partialUpdate(devis);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, devis.getId().toString())
        );
    }

    /**
     * {@code GET  /devis} : get all the devis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of devis in body.
     */
    @GetMapping("/devis")
    public ResponseEntity<List<Devis>> getAllDevis(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Devis");
        Page<Devis> page = devisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /devis/:id} : get the "id" devis.
     *
     * @param id the id of the devis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the devis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/devis/{id}")
    public ResponseEntity<Devis> getDevis(@PathVariable Long id) {
        log.debug("REST request to get Devis : {}", id);
        Optional<Devis> devis = devisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(devis);
    }

    /**
     * {@code DELETE  /devis/:id} : delete the "id" devis.
     *
     * @param id the id of the devis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/devis/{id}")
    public ResponseEntity<Void> deleteDevis(@PathVariable Long id) {
        log.debug("REST request to delete Devis : {}", id);
        devisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
