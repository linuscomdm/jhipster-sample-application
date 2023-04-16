package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DocumentATraduire;
import com.mycompany.myapp.repository.DocumentATraduireRepository;
import com.mycompany.myapp.service.DocumentATraduireService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DocumentATraduire}.
 */
@RestController
@RequestMapping("/api")
public class DocumentATraduireResource {

    private final Logger log = LoggerFactory.getLogger(DocumentATraduireResource.class);

    private static final String ENTITY_NAME = "documentATraduire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentATraduireService documentATraduireService;

    private final DocumentATraduireRepository documentATraduireRepository;

    public DocumentATraduireResource(
        DocumentATraduireService documentATraduireService,
        DocumentATraduireRepository documentATraduireRepository
    ) {
        this.documentATraduireService = documentATraduireService;
        this.documentATraduireRepository = documentATraduireRepository;
    }

    /**
     * {@code POST  /document-a-traduires} : Create a new documentATraduire.
     *
     * @param documentATraduire the documentATraduire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentATraduire, or with status {@code 400 (Bad Request)} if the documentATraduire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-a-traduires")
    public ResponseEntity<DocumentATraduire> createDocumentATraduire(@Valid @RequestBody DocumentATraduire documentATraduire)
        throws URISyntaxException {
        log.debug("REST request to save DocumentATraduire : {}", documentATraduire);
        if (documentATraduire.getId() != null) {
            throw new BadRequestAlertException("A new documentATraduire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentATraduire result = documentATraduireService.save(documentATraduire);
        return ResponseEntity
            .created(new URI("/api/document-a-traduires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-a-traduires/:id} : Updates an existing documentATraduire.
     *
     * @param id the id of the documentATraduire to save.
     * @param documentATraduire the documentATraduire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentATraduire,
     * or with status {@code 400 (Bad Request)} if the documentATraduire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentATraduire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-a-traduires/{id}")
    public ResponseEntity<DocumentATraduire> updateDocumentATraduire(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocumentATraduire documentATraduire
    ) throws URISyntaxException {
        log.debug("REST request to update DocumentATraduire : {}, {}", id, documentATraduire);
        if (documentATraduire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentATraduire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentATraduireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocumentATraduire result = documentATraduireService.update(documentATraduire);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentATraduire.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /document-a-traduires/:id} : Partial updates given fields of an existing documentATraduire, field will ignore if it is null
     *
     * @param id the id of the documentATraduire to save.
     * @param documentATraduire the documentATraduire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentATraduire,
     * or with status {@code 400 (Bad Request)} if the documentATraduire is not valid,
     * or with status {@code 404 (Not Found)} if the documentATraduire is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentATraduire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/document-a-traduires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentATraduire> partialUpdateDocumentATraduire(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocumentATraduire documentATraduire
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumentATraduire partially : {}, {}", id, documentATraduire);
        if (documentATraduire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentATraduire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentATraduireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentATraduire> result = documentATraduireService.partialUpdate(documentATraduire);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentATraduire.getId().toString())
        );
    }

    /**
     * {@code GET  /document-a-traduires} : get all the documentATraduires.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentATraduires in body.
     */
    @GetMapping("/document-a-traduires")
    public ResponseEntity<List<DocumentATraduire>> getAllDocumentATraduires(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of DocumentATraduires");
        Page<DocumentATraduire> page;
        if (eagerload) {
            page = documentATraduireService.findAllWithEagerRelationships(pageable);
        } else {
            page = documentATraduireService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-a-traduires/:id} : get the "id" documentATraduire.
     *
     * @param id the id of the documentATraduire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentATraduire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-a-traduires/{id}")
    public ResponseEntity<DocumentATraduire> getDocumentATraduire(@PathVariable Long id) {
        log.debug("REST request to get DocumentATraduire : {}", id);
        Optional<DocumentATraduire> documentATraduire = documentATraduireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentATraduire);
    }

    /**
     * {@code DELETE  /document-a-traduires/:id} : delete the "id" documentATraduire.
     *
     * @param id the id of the documentATraduire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-a-traduires/{id}")
    public ResponseEntity<Void> deleteDocumentATraduire(@PathVariable Long id) {
        log.debug("REST request to delete DocumentATraduire : {}", id);
        documentATraduireService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
