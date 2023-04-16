package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DetailDevis;
import com.mycompany.myapp.repository.DetailDevisRepository;
import com.mycompany.myapp.service.DetailDevisService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DetailDevis}.
 */
@RestController
@RequestMapping("/api")
public class DetailDevisResource {

    private final Logger log = LoggerFactory.getLogger(DetailDevisResource.class);

    private static final String ENTITY_NAME = "detailDevis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailDevisService detailDevisService;

    private final DetailDevisRepository detailDevisRepository;

    public DetailDevisResource(DetailDevisService detailDevisService, DetailDevisRepository detailDevisRepository) {
        this.detailDevisService = detailDevisService;
        this.detailDevisRepository = detailDevisRepository;
    }

    /**
     * {@code POST  /detail-devis} : Create a new detailDevis.
     *
     * @param detailDevis the detailDevis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailDevis, or with status {@code 400 (Bad Request)} if the detailDevis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-devis")
    public ResponseEntity<DetailDevis> createDetailDevis(@Valid @RequestBody DetailDevis detailDevis) throws URISyntaxException {
        log.debug("REST request to save DetailDevis : {}", detailDevis);
        if (detailDevis.getId() != null) {
            throw new BadRequestAlertException("A new detailDevis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailDevis result = detailDevisService.save(detailDevis);
        return ResponseEntity
            .created(new URI("/api/detail-devis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-devis/:id} : Updates an existing detailDevis.
     *
     * @param id the id of the detailDevis to save.
     * @param detailDevis the detailDevis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailDevis,
     * or with status {@code 400 (Bad Request)} if the detailDevis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailDevis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-devis/{id}")
    public ResponseEntity<DetailDevis> updateDetailDevis(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetailDevis detailDevis
    ) throws URISyntaxException {
        log.debug("REST request to update DetailDevis : {}, {}", id, detailDevis);
        if (detailDevis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailDevis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailDevisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailDevis result = detailDevisService.update(detailDevis);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailDevis.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detail-devis/:id} : Partial updates given fields of an existing detailDevis, field will ignore if it is null
     *
     * @param id the id of the detailDevis to save.
     * @param detailDevis the detailDevis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailDevis,
     * or with status {@code 400 (Bad Request)} if the detailDevis is not valid,
     * or with status {@code 404 (Not Found)} if the detailDevis is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailDevis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detail-devis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetailDevis> partialUpdateDetailDevis(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetailDevis detailDevis
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetailDevis partially : {}, {}", id, detailDevis);
        if (detailDevis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailDevis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailDevisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailDevis> result = detailDevisService.partialUpdate(detailDevis);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailDevis.getId().toString())
        );
    }

    /**
     * {@code GET  /detail-devis} : get all the detailDevis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailDevis in body.
     */
    @GetMapping("/detail-devis")
    public ResponseEntity<List<DetailDevis>> getAllDetailDevis(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DetailDevis");
        Page<DetailDevis> page = detailDevisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detail-devis/:id} : get the "id" detailDevis.
     *
     * @param id the id of the detailDevis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailDevis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-devis/{id}")
    public ResponseEntity<DetailDevis> getDetailDevis(@PathVariable Long id) {
        log.debug("REST request to get DetailDevis : {}", id);
        Optional<DetailDevis> detailDevis = detailDevisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailDevis);
    }

    /**
     * {@code DELETE  /detail-devis/:id} : delete the "id" detailDevis.
     *
     * @param id the id of the detailDevis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-devis/{id}")
    public ResponseEntity<Void> deleteDetailDevis(@PathVariable Long id) {
        log.debug("REST request to delete DetailDevis : {}", id);
        detailDevisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
