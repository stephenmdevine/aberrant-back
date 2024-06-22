package org.devine.aberrant.repository;

import org.devine.aberrant.model.Merit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeritRepository extends JpaRepository<Merit, Long> {
}
