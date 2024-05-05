package org.devine.aberrant.character;

import org.devine.aberrant.ability.Ability;
import org.devine.aberrant.ability.Specialty;
import org.devine.aberrant.attribute.Attribute;
import org.devine.aberrant.attribute.AttributeSet;
import org.devine.aberrant.attribute.Quality;
import org.devine.aberrant.background.Background;
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

    @Override
    public Character allocateBackgroundPoints(Character character, Map<String, Integer> backgroundValues) throws IllegalArgumentException {
        // Validate total points allocated
        int totalPointsAllocated = backgroundValues.values().stream().mapToInt(Integer::intValue).sum();
        if (totalPointsAllocated > character.getBackgroundPoints()) {
            throw new IllegalArgumentException("Insufficient ability points");
        }

        // Iterate over the provided background values
        for (Map.Entry<String, Integer> entry : backgroundValues.entrySet()) {
            String backgroundName = entry.getKey();
            int backgroundValue = entry.getValue();

            // Find the background by name
            Background background = character.getBackgrounds().stream()
                    .filter(a -> a.getName().equalsIgnoreCase(backgroundName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Background not found: " + backgroundName));

            // Validate background value range (0 to 5)
            if (backgroundValue < 0 || backgroundValue > 5) {
                throw new IllegalArgumentException("Invalid background value: " + backgroundValue);
            }

            // Update the ability value
            background.setValue(backgroundValue);
        }

        // Update the character's remaining background points
        int remainingPoints = character.getBackgroundPoints() - totalPointsAllocated;
        character.setBackgroundPoints(remainingPoints);

        // Save the changes to the character and return
        return characterRepository.save(character);
    }

    @Override
    public Character spendBonusPoints(Character character, Map<String, Integer> bonusPointSpending) throws IllegalArgumentException {
        // Validate total bonus points spent
        int totalPointsSpent = bonusPointSpending.values().stream().mapToInt(Integer::intValue).sum();
        if (totalPointsSpent > character.getBonusPoints()) {
            throw new IllegalArgumentException("Insufficient bonus points");
        }

        // Iterate over the bonus point spending map
        for (Map.Entry<String, Integer> entry : bonusPointSpending.entrySet()) {
            String aspect = entry.getKey();
            int pointsSpent = entry.getValue();

            switch (aspect) {
                case "attribute":
                    // Check if the attribute name is provided in the spending map
                    String attributeName = entry.getKey();
                    if (attributeName == null || attributeName.isEmpty()) {
                        throw new IllegalArgumentException("Attribute name is required");
                    }

                    // Find the attribute by name
                    Attribute attribute = character.getAttributes().stream()
                            .filter(attr -> attr.getName().equalsIgnoreCase(attributeName))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + attributeName));

                    // Calculate the number of dots to increase
                    int attributeDotsToIncrease = pointsSpent / 5; // 5 bonus points for one dot increase

                    // Increase the attribute value by the calculated dots
                    int newAttributeValue = attribute.getValue() + attributeDotsToIncrease;
                    attribute.setValue(newAttributeValue);

                    // Update the remaining bonus points
                    int remainingAttributePoints = pointsSpent % 5; // Remaining points after spending on dots
                    character.setBonusPoints(character.getBonusPoints() - pointsSpent + remainingAttributePoints);

                    break;
                case "ability":
                    // Check if the ability name is provided in the spending map
                    String abilityName = entry.getKey();
                    if (abilityName == null || abilityName.isEmpty()) {
                        throw new IllegalArgumentException("Ability name is required");
                    }

                    // Find the ability by name
                    Ability ability = character.getAbilities().stream()
                            .filter(abil -> abil.getName().equalsIgnoreCase(abilityName))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Ability not found: " + abilityName));

                    // Calculate the number of dots to increase
                    int abilityDotsToIncrease = pointsSpent / 2; // 2 bonus points for one dot increase

                    // Increase the ability value by the calculated dots
                    int newAbilityValue = ability.getValue() + abilityDotsToIncrease;
                    ability.setValue(newAbilityValue);

                    // Update the remaining bonus points
                    int remainingAbilityPoints = pointsSpent % 2; // Remaining points after spending on dots
                    character.setBonusPoints(character.getBonusPoints() - pointsSpent + remainingAbilityPoints);

                    break;
                case "background":
                    // Check if the background name is provided in the spending map
                    String backgroundName = entry.getKey();
                    if (backgroundName == null || backgroundName.isEmpty()) {
                        throw new IllegalArgumentException("Background name is required");
                    }

                    // Find the background by name
                    Background background = character.getBackgrounds().stream()
                            .filter(bg -> bg.getName().equalsIgnoreCase(backgroundName))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Background not found: " + backgroundName));

                    // Calculate the number of dots to increase
                    int backgroundDotsToIncrease = pointsSpent; // 1 bonus point for one dot increase

                    // Increase the background value by the calculated dots
                    int newBackgroundValue = background.getValue() + backgroundDotsToIncrease;
                    background.setValue(newBackgroundValue);

                    // Update the remaining bonus points
                    character.setBonusPoints(character.getBonusPoints() - pointsSpent);

                    break;
                case "ability_specialty":
                    // Check if both ability name and specialty name are provided in the spending map
                    String anotherAbilityName = entry.getKey();
                    String specialtyName = String.valueOf(entry.getValue()); // Assuming the value contains the specialty name
                    if (anotherAbilityName == null || anotherAbilityName.isEmpty() || specialtyName == null || specialtyName.isEmpty()) {
                        throw new IllegalArgumentException("Ability name and specialty name are required");
                    }

                    // Find the ability by name
                    Ability anotherAbility = character.getAbilities().stream()
                            .filter(abil -> abil.getName().equalsIgnoreCase(anotherAbilityName))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Ability not found: " + anotherAbilityName));

                    // Check if the ability already has maximum specialties
                    if (anotherAbility.getSpecialties().size() >= 3) {
                        throw new IllegalArgumentException("Maximum specialties reached for ability: " + anotherAbilityName);
                    }

                    // Create and add the specialty
                    Specialty specialty = new Specialty();
                    specialty.setName(specialtyName);
                    specialty.setAbility(anotherAbility);
                    anotherAbility.getSpecialties().add(specialty);

                    // Update the remaining bonus points
                    character.setBonusPoints(character.getBonusPoints() - 1);

                    break;

                // Add more cases for other aspects if needed
                default:
                    throw new IllegalArgumentException("Invalid aspect: " + aspect);
            }
        }

        // Update the character's remaining bonus points
        int remainingPoints = character.getBonusPoints() - totalPointsSpent;
        character.setBonusPoints(remainingPoints);

        // Save the changes to the character and return
        return characterRepository.save(character);
    }


}
