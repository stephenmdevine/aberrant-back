package org.devine.aberrant.repository;

import org.devine.aberrant.model.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharacterRepository extends JpaRepository<GameCharacter, Long> {
}
