package org.devine.aberrant.abilities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbilitiesRepository extends JpaRepository<Abilities, Integer> {
}
