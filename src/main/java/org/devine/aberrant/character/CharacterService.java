package org.devine.aberrant.character;

import org.devine.aberrant.attribute.AttributeSet;

import java.util.Map;

public interface CharacterService {

    Character createCharacter(String name,
                              String player,
                              String novaName,
                              String concept,
                              String nature,
                              String allegiance,
                              String description,
                              int attributePoints,
                              int abilityPoints,
                              int backgroundPoints,
                              int bonusPoints,
                              int novaPoints,
                              int experiencePoints);

    Character findById(Long characterId);

    Character allocateAttributePoints(Character character, AttributeSet attributeSet, Map<String, Integer> attributeValues, Map<String, String> qualityDetails) throws IllegalArgumentException;

    Character allocateAbilityPoints(Character character, Map<String, Integer> abilityValues) throws IllegalArgumentException;

    Character addSpecialtyToAbility(Character character, String abilityName, String specialtyName);

    Character allocateBackgroundPoints(Character character, Map<String, Integer> backgroundValues) throws IllegalArgumentException;

    void increaseAttribute(Character character, String attributeName, Boolean isNewChar, Boolean isNova);

    void increaseAbility(Character character, String abilityName, Boolean isNewChar, Boolean isNova);

    void addAbilitySpecialty(Character character, String abilityName, String specialtyName, Boolean isNewChar);

    void increaseBackground(Character character, String backgroundName, Boolean isNewChar, Boolean isNova);

    void increaseWillpower(Character character, Boolean isNewChar, Boolean isNova);

    void increaseQuantum(Character character, Boolean isNewChar, Boolean isNova, Boolean isTainted);

    void increaseInitiative(Character character, Boolean isNewChar);

    void addMerit(Character character, String meritName, int meritValue);

    void addFlaw(Character character, String flawName, int flawValue);

    void increaseMegaAttribute(Character character, String megaAttributeName, String enhancementName, Boolean isNewChar, Boolean isTainted);

    void addEnhancement(Character character, String megaAttributeName, String enhancementName, Boolean isNewChar, Boolean isTainted);

    void increasePower(Character character, String powerName, int level, int quantumMinimum, String attributeName,
                       Boolean hasExtra, String extraName, Boolean isNewChar, Boolean isTainted);

}
