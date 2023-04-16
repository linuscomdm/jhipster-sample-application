package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Prestataire;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prestataire entity.
 */
@Repository
public interface PrestataireRepository extends JpaRepository<Prestataire, Long> {
    default Optional<Prestataire> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Prestataire> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Prestataire> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct prestataire from Prestataire prestataire left join fetch prestataire.user left join fetch prestataire.banque left join fetch prestataire.ville left join fetch prestataire.prestaDdeTraductions",
        countQuery = "select count(distinct prestataire) from Prestataire prestataire"
    )
    Page<Prestataire> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct prestataire from Prestataire prestataire left join fetch prestataire.user left join fetch prestataire.banque left join fetch prestataire.ville left join fetch prestataire.prestaDdeTraductions"
    )
    List<Prestataire> findAllWithToOneRelationships();

    @Query(
        "select prestataire from Prestataire prestataire left join fetch prestataire.user left join fetch prestataire.banque left join fetch prestataire.ville left join fetch prestataire.prestaDdeTraductions where prestataire.id =:id"
    )
    Optional<Prestataire> findOneWithToOneRelationships(@Param("id") Long id);
}
