package org.devine.aberrant.repository;

import org.devine.aberrant.model.Quality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualityRepository extends JpaRepository<Quality, Long> {
}
