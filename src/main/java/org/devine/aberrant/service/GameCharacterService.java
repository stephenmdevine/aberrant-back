package org.devine.aberrant.service;

import org.devine.aberrant.model.GameCharacter;
import org.devine.aberrant.request.AllocateAbilityPointsRequest;
import org.devine.aberrant.request.AllocateAttributePointsRequest;
import org.devine.aberrant.request.AllocateBackgroundPointsRequest;
import org.devine.aberrant.request.GameCharacterCreationRequest;

import java.util.List;

public interface GameCharacterService {

    GameCharacter createCharacter(GameCharacterCreationRequest request);
    List<GameCharacter> findAll();
    GameCharacter findById(Long gameCharacterId);
    GameCharacter saveCharacter(GameCharacter gameCharacter);

    GameCharacter allocateAttributePoints(GameCharacter gameCharacter, AllocateAttributePointsRequest request) throws IllegalArgumentException;
    GameCharacter allocateAbilityPoints(GameCharacter gameCharacter, AllocateAbilityPointsRequest request) throws IllegalArgumentException;
    GameCharacter allocateBackgroundPoints(GameCharacter gameCharacter, AllocateBackgroundPointsRequest request) throws IllegalArgumentException;

    void increaseAttribute(GameCharacter gameCharacter, String attributeName, String qualityName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException;
    void increaseAbility(GameCharacter gameCharacter, String abilityName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException;
    void addAbilitySpecialty(GameCharacter gameCharacter, String abilityName, String specialtyName, Boolean isNewChar) throws IllegalArgumentException;
    void increaseBackground(GameCharacter gameCharacter, String backgroundName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException;
    void increaseWillpower(GameCharacter gameCharacter, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException;
    void increaseQuantum(GameCharacter gameCharacter, Boolean isNewChar, Boolean isNova, Boolean isTainted) throws IllegalArgumentException;
    void increaseInitiative(GameCharacter gameCharacter, Boolean isNewChar) throws IllegalArgumentException;
    void addMerit(GameCharacter gameCharacter, String meritName, int meritValue) throws IllegalArgumentException;
    void addFlaw(GameCharacter gameCharacter, String flawName, int flawValue) throws IllegalArgumentException;
    void increaseMegaAttribute(GameCharacter gameCharacter, String megaAttributeName, String enhancementName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException;
    void addEnhancement(GameCharacter gameCharacter, String megaAttributeName, String enhancementName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException;
    void increasePower(GameCharacter gameCharacter, String powerName, int level, int quantumMinimum, String attributeName,
                       Boolean hasExtra, String extraName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException;
    void increaseQuantumPool(GameCharacter gameCharacter) throws IllegalArgumentException;

}
