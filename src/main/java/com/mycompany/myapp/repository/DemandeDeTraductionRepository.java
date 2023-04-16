package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DemandeDeTraduction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DemandeDeTraduction entity.
 */
@Repository
public interface DemandeDeTraductionRepository extends JpaRepository<DemandeDeTraduction, Long> {
    default Optional<DemandeDeTraduction> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DemandeDeTraduction> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DemandeDeTraduction> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct demandeDeTraduction from DemandeDeTraduction demandeDeTraduction left join fetch demandeDeTraduction.ville left join fetch demandeDeTraduction.demandeurService",
        countQuery = "select count(distinct demandeDeTraduction) from DemandeDeTraduction demandeDeTraduction"
    )
    Page<DemandeDeTraduction> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct demandeDeTraduction from DemandeDeTraduction demandeDeTraduction left join fetch demandeDeTraduction.ville left join fetch demandeDeTraduction.demandeurService"
    )
    List<DemandeDeTraduction> findAllWithToOneRelationships();

    @Query(
        "select demandeDeTraduction from DemandeDeTraduction demandeDeTraduction left join fetch demandeDeTraduction.ville left join fetch demandeDeTraduction.demandeurService where demandeDeTraduction.id =:id"
    )
    Optional<DemandeDeTraduction> findOneWithToOneRelationships(@Param("id") Long id);
}
