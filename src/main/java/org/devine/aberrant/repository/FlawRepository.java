package org.devine.aberrant.repository;

import org.devine.aberrant.model.Flaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlawRepository extends JpaRepository<Flaw, Long> {
}
