package org.devine.aberrant.powers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowersRepository extends JpaRepository<Powers, Integer> {
}
