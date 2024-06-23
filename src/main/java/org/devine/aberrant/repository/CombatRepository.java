package org.devine.aberrant.repository;

import org.devine.aberrant.model.Combat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombatRepository extends JpaRepository<Combat, Long> {
}
