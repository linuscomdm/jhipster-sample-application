package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.NatureDocumentATraduire;
import com.mycompany.myapp.repository.NatureDocumentATraduireRepository;
import com.mycompany.myapp.service.NatureDocumentATraduireService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.NatureDocumentATraduire}.
 */
@RestController
@RequestMapping("/api")
public class NatureDocumentATraduireResource {

    private final Logger log = LoggerFactory.getLogger(NatureDocumentATraduireResource.class);

    private static final String ENTITY_NAME = "natureDocumentATraduire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureDocumentATraduireService natureDocumentATraduireService;

    private final NatureDocumentATraduireRepository natureDocumentATraduireRepository;

    public NatureDocumentATraduireResource(
        NatureDocumentATraduireService natureDocumentATraduireService,
        NatureDocumentATraduireRepository natureDocumentATraduireRepository
    ) {
        this.natureDocumentATraduireService = natureDocumentATraduireService;
        this.natureDocumentATraduireRepository = natureDocumentATraduireRepository;
    }

    /**
     * {@code POST  /nature-document-a-traduires} : Create a new natureDocumentATraduire.
     *
     * @param natureDocumentATraduire the natureDocumentATraduire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureDocumentATraduire, or with status {@code 400 (Bad Request)} if the natureDocumentATraduire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nature-document-a-traduires")
    public ResponseEntity<NatureDocumentATraduire> createNatureDocumentATraduire(
        @RequestBody NatureDocumentATraduire natureDocumentATraduire
    ) throws URISyntaxException {
        log.debug("REST request to save NatureDocumentATraduire : {}", natureDocumentATraduire);
        if (natureDocumentATraduire.getId() != null) {
            throw new BadRequestAlertException("A new natureDocumentATraduire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureDocumentATraduire result = natureDocumentATraduireService.save(natureDocumentATraduire);
        return ResponseEntity
            .created(new URI("/api/nature-document-a-traduires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nature-document-a-traduires/:id} : Updates an existing natureDocumentATraduire.
     *
     * @param id the id of the natureDocumentATraduire to save.
     * @param natureDocumentATraduire the natureDocumentATraduire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureDocumentATraduire,
     * or with status {@code 400 (Bad Request)} if the natureDocumentATraduire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureDocumentATraduire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nature-document-a-traduires/{id}")
    public ResponseEntity<NatureDocumentATraduire> updateNatureDocumentATraduire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureDocumentATraduire natureDocumentATraduire
    ) throws URISyntaxException {
        log.debug("REST request to update NatureDocumentATraduire : {}, {}", id, natureDocumentATraduire);
        if (natureDocumentATraduire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureDocumentATraduire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureDocumentATraduireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NatureDocumentATraduire result = natureDocumentATraduireService.update(natureDocumentATraduire);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureDocumentATraduire.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nature-document-a-traduires/:id} : Partial updates given fields of an existing natureDocumentATraduire, field will ignore if it is null
     *
     * @param id the id of the natureDocumentATraduire to save.
     * @param natureDocumentATraduire the natureDocumentATraduire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureDocumentATraduire,
     * or with status {@code 400 (Bad Request)} if the natureDocumentATraduire is not valid,
     * or with status {@code 404 (Not Found)} if the natureDocumentATraduire is not found,
     * or with status {@code 500 (Internal Server Error)} if the natureDocumentATraduire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nature-document-a-traduires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NatureDocumentATraduire> partialUpdateNatureDocumentATraduire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureDocumentATraduire natureDocumentATraduire
    ) throws URISyntaxException {
        log.debug("REST request to partial update NatureDocumentATraduire partially : {}, {}", id, natureDocumentATraduire);
        if (natureDocumentATraduire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureDocumentATraduire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureDocumentATraduireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NatureDocumentATraduire> result = natureDocumentATraduireService.partialUpdate(natureDocumentATraduire);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureDocumentATraduire.getId().toString())
        );
    }

    /**
     * {@code GET  /nature-document-a-traduires} : get all the natureDocumentATraduires.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natureDocumentATraduires in body.
     */
    @GetMapping("/nature-document-a-traduires")
    public ResponseEntity<List<NatureDocumentATraduire>> getAllNatureDocumentATraduires(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of NatureDocumentATraduires");
        Page<NatureDocumentATraduire> page = natureDocumentATraduireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nature-document-a-traduires/:id} : get the "id" natureDocumentATraduire.
     *
     * @param id the id of the natureDocumentATraduire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureDocumentATraduire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nature-document-a-traduires/{id}")
    public ResponseEntity<NatureDocumentATraduire> getNatureDocumentATraduire(@PathVariable Long id) {
        log.debug("REST request to get NatureDocumentATraduire : {}", id);
        Optional<NatureDocumentATraduire> natureDocumentATraduire = natureDocumentATraduireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureDocumentATraduire);
    }

    /**
     * {@code DELETE  /nature-document-a-traduires/:id} : delete the "id" natureDocumentATraduire.
     *
     * @param id the id of the natureDocumentATraduire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nature-document-a-traduires/{id}")
    public ResponseEntity<Void> deleteNatureDocumentATraduire(@PathVariable Long id) {
        log.debug("REST request to delete NatureDocumentATraduire : {}", id);
        natureDocumentATraduireService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
