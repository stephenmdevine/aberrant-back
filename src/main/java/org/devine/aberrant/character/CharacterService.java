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

//    Character spendBonusPoints(Character character, Map<String, Integer> bonusPointSpending) throws IllegalArgumentException;

    void increaseAttribute(Character character, String attributeName, Boolean isNewChar, Boolean isNova);

    void increaseAbility(Character character, String abilityName, Boolean isNewChar, Boolean isNova);

    void increaseBackground(Character character, String backgroundName, Boolean isNewChar, Boolean isNova);

//    void spendNovaPoints(Character character, Map<String, Integer> novaPointSpending) throws IllegalArgumentException;

    void increaseMegaAttribute(Character character, String megaAttributeName, String enhancementName, Boolean isNewChar);

}
