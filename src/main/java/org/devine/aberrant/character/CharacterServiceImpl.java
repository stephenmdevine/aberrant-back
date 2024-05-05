package org.devine.aberrant.character;

import org.devine.aberrant.ability.Ability;
import org.devine.aberrant.ability.Specialty;
import org.devine.aberrant.attribute.Attribute;
import org.devine.aberrant.attribute.AttributeSet;
import org.devine.aberrant.attribute.Quality;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public Character createCharacter(String name,
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
                                     int experiencePoints) {
        Character character = new Character();
        character.setName(name);
        character.setPlayer(player);
        character.setNovaName(novaName);
        character.setConcept(concept);
        character.setNature(nature);
        character.setAllegiance(allegiance);
        character.setDescription(description);
        character.setAttributePoints(attributePoints);
        character.setAbilityPoints(abilityPoints);
        character.setBackgroundPoints(backgroundPoints);
        character.setBonusPoints(bonusPoints);
        character.setNovaPoints(novaPoints);
        character.setExperiencePoints(experiencePoints);
        return characterRepository.save(character);
    }

    @Override
    public Character allocateAttributePoints(Character character, AttributeSet attributeSet, Map<String, Integer> attributeValues, Map<String, String> qualityDetails) throws IllegalArgumentException {
        // Validate if the character has enough attribute points
        int totalPointsAllocated = attributeValues.values().stream().mapToInt(Integer::intValue).sum();
        if (totalPointsAllocated > character.getAttributePoints()) {
            throw new IllegalArgumentException("Insufficient attribute points");
        }

        // Iterate over attribute values and update the corresponding attributes in the attribute set
        for (Map.Entry<String, Integer> entry : attributeValues.entrySet()) {
            String attributeName = entry.getKey();
            int attributeValue = entry.getValue();

            // Find the attribute with the given name in the attribute set
            Attribute attribute = attributeSet.getAttributes().stream()
                    .filter(attr -> attr.getName().equalsIgnoreCase(attributeName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + attributeName));

            // Update the attribute value
            attribute.setValue(attributeValue);

            // Validate attribute value range (1 to 5)
            if (attributeValue < 1 || attributeValue > 5) {
                throw new IllegalArgumentException("Invalid attribute value: " + attributeValue);
            }

            // Check if the attribute value is 4 or higher
            if (attributeValue >= 4) {
                // Create a new Quality with user-defined name and description
                Quality quality = new Quality();
                quality.setName(qualityDetails.getOrDefault(attributeName + "_name", "High " + attributeName));
                quality.setDescription(qualityDetails.getOrDefault(attributeName + "_description", "A quality associated with a high " + attributeName + " attribute value."));
                quality.setAttribute(attribute);

                attribute.getQualities().add(quality);
                /* React code for Quality
                    <input type="text" name="Strength_name" placeholder="Name for Strength Quality">
                    <textarea name="Strength_description" placeholder="Description for Strength Quality"></textarea>
                */
            }
        }

        // Update the character's attribute points
        int remainingPoints = character.getAttributePoints() - totalPointsAllocated;
        character.setAttributePoints(remainingPoints);

        // Save the changes to the character and return
        return characterRepository.save(character);
    }

    @Override
    public Character allocateAbilityPoints(Character character, Map<String, Integer> abilityValues) throws IllegalArgumentException {
        // Validate total points allocated
        int totalPointsAllocated = abilityValues.values().stream().mapToInt(Integer::intValue).sum();
        if (totalPointsAllocated > character.getAbilityPoints()) {
            throw new IllegalArgumentException("Insufficient ability points");
        }

        // Iterate over the provided ability values
        for (Map.Entry<String, Integer> entry : abilityValues.entrySet()) {
            String abilityName = entry.getKey();
            int abilityValue = entry.getValue();

            // Find the ability by name
            Ability ability = character.getAbilities().stream()
                    .filter(a -> a.getName().equalsIgnoreCase(abilityName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Ability not found: " + abilityName));

            // Validate ability value range (0 to 5)
            if (abilityValue < 0 || abilityValue > 5) {
                throw new IllegalArgumentException("Invalid ability value: " + abilityValue);
            }

            // Update the ability value
            ability.setValue(abilityValue);
        }

        // Update the character's remaining ability points
        int remainingPoints = character.getAbilityPoints() - totalPointsAllocated;
        character.setAbilityPoints(remainingPoints);

        // Save the changes to the character and return
        return characterRepository.save(character);
    }

    @Override
    public Character addSpecialtyToAbility(Character character, String abilityName, String specialtyName) {
        // Find the ability by name
        Ability ability = character.getAbilities().stream()
                .filter(a -> a.getName().equalsIgnoreCase(abilityName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ability not found: " + abilityName));

        // Create and add the specialty
        Specialty specialty = new Specialty();
        specialty.setName(specialtyName);
        specialty.setAbility(ability);
        ability.getSpecialties().add(specialty);

        // Save the changes to the character and return
        return characterRepository.save(character);
    }


}
