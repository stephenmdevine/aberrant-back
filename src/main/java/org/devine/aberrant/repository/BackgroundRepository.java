package org.devine.aberrant.repository;

import org.devine.aberrant.model.Background;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackgroundRepository extends JpaRepository<Background, Long> {
}
