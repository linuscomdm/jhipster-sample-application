package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Langue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Langue entity.
 */
@Repository
public interface LangueRepository extends JpaRepository<Langue, Long> {
    default Optional<Langue> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Langue> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Langue> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct langue from Langue langue left join fetch langue.docTraductions",
        countQuery = "select count(distinct langue) from Langue langue"
    )
    Page<Langue> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct langue from Langue langue left join fetch langue.docTraductions")
    List<Langue> findAllWithToOneRelationships();

    @Query("select langue from Langue langue left join fetch langue.docTraductions where langue.id =:id")
    Optional<Langue> findOneWithToOneRelationships(@Param("id") Long id);
}
