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

    Character allocateAttributePoints(Character character, AttributeSet attributeSet, Map<String, Integer> attributeValues, Map<String, String> qualityDetails) throws IllegalArgumentException;

}
