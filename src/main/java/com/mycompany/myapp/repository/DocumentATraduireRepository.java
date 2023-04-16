package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DocumentATraduire;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentATraduire entity.
 */
@Repository
public interface DocumentATraduireRepository extends JpaRepository<DocumentATraduire, Long> {
    default Optional<DocumentATraduire> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DocumentATraduire> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DocumentATraduire> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct documentATraduire from DocumentATraduire documentATraduire left join fetch documentATraduire.langueDestination left join fetch documentATraduire.typeDocument left join fetch documentATraduire.demandeTraductions",
        countQuery = "select count(distinct documentATraduire) from DocumentATraduire documentATraduire"
    )
    Page<DocumentATraduire> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct documentATraduire from DocumentATraduire documentATraduire left join fetch documentATraduire.langueDestination left join fetch documentATraduire.typeDocument left join fetch documentATraduire.demandeTraductions"
    )
    List<DocumentATraduire> findAllWithToOneRelationships();

    @Query(
        "select documentATraduire from DocumentATraduire documentATraduire left join fetch documentATraduire.langueDestination left join fetch documentATraduire.typeDocument left join fetch documentATraduire.demandeTraductions where documentATraduire.id =:id"
    )
    Optional<DocumentATraduire> findOneWithToOneRelationships(@Param("id") Long id);
}
