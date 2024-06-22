/*package org.devine.aberrant.character;

import org.devine.aberrant.ability.AllocateAbilityPointsRequest;
import org.devine.aberrant.attribute.AllocateAttributePointsRequest;

import java.util.List;

public interface CharacterService {

    Character createCharacter(CharacterCreationRequest request);

    List<Character> findAll();

    Character findById(Long characterId);

    Character allocateAttributePoints(Character character, AllocateAttributePointsRequest request) throws IllegalArgumentException;

    Character allocateAbilityPoints(Character character, AllocateAbilityPointsRequest request) throws IllegalArgumentException;

    Character allocateBackgroundPoints(Character character, AllocateBackgroundPointsRequest request) throws IllegalArgumentException;

    void increaseAttribute(Character character, String attributeName, String qualityName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException;

    void increaseAbility(Character character, String abilityName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException;

    void addAbilitySpecialty(Character character, String abilityName, String specialtyName, Boolean isNewChar) throws IllegalArgumentException;

    void increaseBackground(Character character, String backgroundName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException;

    void increaseWillpower(Character character, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException;

    void increaseQuantum(Character character, Boolean isNewChar, Boolean isNova, Boolean isTainted) throws IllegalArgumentException;

    void increaseInitiative(Character character, Boolean isNewChar) throws IllegalArgumentException;

    void addMerit(Character character, String meritName, int meritValue) throws IllegalArgumentException;

    void addFlaw(Character character, String flawName, int flawValue) throws IllegalArgumentException;

    void increaseMegaAttribute(Character character, String megaAttributeName, String enhancementName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException;

    void addEnhancement(Character character, String megaAttributeName, String enhancementName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException;

    void increasePower(Character character, String powerName, int level, int quantumMinimum, String attributeName,
                       Boolean hasExtra, String extraName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException;

    void increaseQuantumPool(Character character) throws IllegalArgumentException;

    Character saveCharacter(Character character);

}
*/