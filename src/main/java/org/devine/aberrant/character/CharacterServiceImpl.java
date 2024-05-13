package org.devine.aberrant.character;

import jakarta.persistence.EntityNotFoundException;
import org.devine.aberrant.ability.Ability;
import org.devine.aberrant.ability.Specialty;
import org.devine.aberrant.attribute.Attribute;
import org.devine.aberrant.attribute.AttributeSet;
import org.devine.aberrant.attribute.Quality;
import org.devine.aberrant.background.Background;
import org.devine.aberrant.megaAttribute.Enhancement;
import org.devine.aberrant.megaAttribute.MegaAttribute;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

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
    public Character findById(Long characterId) {
        // Retrieve the character from the database by ID
        Optional<Character> characterOptional = characterRepository.findById(characterId);

        // If the character is found, return it; otherwise, throw an exception
        return characterOptional.orElseThrow(() -> new EntityNotFoundException("Character not found with id: " + characterId));
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

                case "willpower":
                    // Calculate the number of dots to increase for Willpower
                    int willpowerDotsToIncrease = pointsSpent / 2; // 2 bonus points for one dot increase

                    // Increase the Willpower value by the calculated dots
                    int newWillpowerValue = character.getWillpower() + willpowerDotsToIncrease;
                    character.setWillpower(newWillpowerValue);

                    // Update the remaining bonus points
                    int remainingWillpowerPoints = pointsSpent % 2; // Remaining points after spending on dots
                    character.setBonusPoints(character.getBonusPoints() - pointsSpent + remainingWillpowerPoints);

                    break;

                case "quantum":
                    // Calculate the number of dots to increase for Quantum
                    int quantumDotsToIncrease = pointsSpent / 7; // 7 bonus points for one dot increase

                    // Increase the Quantum value by the calculated dots
                    int newQuantumValue = character.getQuantum() + quantumDotsToIncrease;
                    character.setQuantum(newQuantumValue);

                    // Update the remaining bonus points
                    int remainingQuantumPoints = pointsSpent % 7; // Remaining points after spending on dots
                    character.setBonusPoints(character.getBonusPoints() - pointsSpent + remainingQuantumPoints);

                    break;

                case "initiative":
                    // Calculate the number of dots to increase for Initiative
                    int initiativeDotsToIncrease = pointsSpent; // 1 bonus point for one dot increase

                    // Increase the Initiative value by the calculated dots
                    int newInitiativeValue = character.getInitiative() + initiativeDotsToIncrease;
                    character.setInitiative(newInitiativeValue);

                    // Update the remaining bonus points
                    character.setBonusPoints(character.getBonusPoints() - pointsSpent);

                    break;

                case "merit":
                    // Check if the merit name and value are provided in the spending map
                    String meritName = entry.getKey();
                    Integer meritValue = entry.getValue();
                    if (meritName == null || meritName.isEmpty() || meritValue == null || meritValue < 1 || meritValue > 7) {
                        throw new IllegalArgumentException("Invalid merit name or value");
                    }

                    // Create and add the merit
                    Merit merit = new Merit();
                    merit.setName(meritName);
                    merit.setValue(meritValue);
                    merit.setCharacter(character);
                    character.getMerits().add(merit);

                    // Deduct bonus points for adding a merit
                    character.setBonusPoints(character.getBonusPoints() - meritValue);

                    break;

                case "flaw":
                    // Check if the flaw name and value are provided in the spending map
                    String flawName = entry.getKey();
                    Integer flawValue = entry.getValue();
                    if (flawName == null || flawName.isEmpty() || flawValue == null || flawValue < 1 || flawValue > 7) {
                        throw new IllegalArgumentException("Invalid flaw name or value");
                    }

                    // Check if the total flaw points exceed 10
                    int totalFlawPoints = character.getFlaws().stream().mapToInt(Flaw::getValue).sum();
                    if (totalFlawPoints + flawValue > 10) {
                        throw new IllegalArgumentException("Total flaw points exceed limit");
                    }

                    // Create and add the flaw
                    Flaw flaw = new Flaw();
                    flaw.setName(flawName);
                    flaw.setValue(flawValue);
                    flaw.setCharacter(character);
                    character.getFlaws().add(flaw);

                    // Award bonus points for adding a flaw
                    character.setBonusPoints(character.getBonusPoints() + flawValue);

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

    @Override
    public void increaseAttribute(Character character, String attributeName, Boolean isNewChar, Boolean isNova) {
        // Find the attribute by name
        Attribute attribute = character.getAttributes().stream()
                .filter(attr -> attr.getName().equalsIgnoreCase(attributeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + attributeName));
        // Get current attribute value
        int currentAttributeValue = character.getAttributeValue(attributeName);

        // Is this character creation or increase through exp
        int pointsToSpend;
        if (isNewChar) {
            if (isNova) {
                pointsToSpend = character.getNovaPoints();
            }   else {
                pointsToSpend = character.getBonusPoints();
            }
        }   else {
            pointsToSpend = character.getExperiencePoints();
        }

        int cost;
        if (isNewChar) {
            if (isNova) {
                cost = 1000; // TODO need a way to get three attribute increases for a cost of 1
            }   else {
                cost = 5;
            }
        }   else {
            cost = currentAttributeValue * 4;
        }

    }

    @Override
    public void spendNovaPoints(Character character, Map<String, Integer> novaSpendingMap) {
        // Iterate through the nova spending map
        for (Map.Entry<String, Integer> entry : novaSpendingMap.entrySet()) {
            String novaItemType = entry.getKey();
            int novaPointsSpent = entry.getValue();

            switch (novaItemType) {
//                case "mega_attribute":
//                    spendNovaPointOnMegaAttribute(character, novaPointsSpent);
//                    break;
//                case "enhancement":
//                    spendNovaPointsOnEnhancement(character, novaPointsSpent);
//                    break;
                case "normal_attribute":
                    spendNovaPointsOnNormalAttributes(character, novaPointsSpent);
                    break;
                case "ability":
                    spendNovaPointsOnAbilities(character, novaPointsSpent);
                    break;
                // Handle other nova spending types if needed
                default:
                    throw new IllegalArgumentException("Invalid nova spending type: " + novaItemType);
            }
        }
    }

    public void increaseMegaAttribute(Character character, String megaAttributeName, String enhancementName, Boolean isNewChar) {
        // Find the ability by name or initialize new mega-attribute
        MegaAttribute megaAttribute = character.getMegaAttributes().stream()
                .filter(attr -> attr.getName().equalsIgnoreCase(megaAttributeName))
                .findFirst().orElseGet(() -> {
                    MegaAttribute newMegaAttribute = new MegaAttribute();
                    newMegaAttribute.setCharacter(character);
                    newMegaAttribute.setName(megaAttributeName);
                    newMegaAttribute.setValue(0);
                    return newMegaAttribute;
                });
        // Get current mega-attribute value
        int currentMegaAttributeValue = character.getMegaAttributeValue(megaAttributeName);
        // Get baseline attribute corresponding to mega-attribute
        String attributeName = megaAttributeName.substring(4);

        // Is this character creation or increase through exp
        int pointsToSpend = isNewChar ? character.getNovaPoints() : character.getExperiencePoints();
        int cost = isNewChar ? 3 : (currentMegaAttributeValue == 0 ? 6 : (currentMegaAttributeValue * 5));

        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }
        // Character cannot have a higher Mega-attribute value than they have a baseline Attribute value
        if (character.getMegaAttributeValue(megaAttributeName) >= character.getAttributeValue(attributeName)) {
            throw new IllegalArgumentException("Mega attribute value cannot exceed attribute value");
        }
        // Character cannot have a higher Mega-attribute value than they have a Quantum value minus one
        if (character.getMegaAttributeValue(megaAttributeName) >= (character.getQuantum() - 1)) {
            throw new IllegalArgumentException("Mega attribute value must be less than Quantum value");
        }
        // Gain a new Enhancement if this is the first dot in a Mega-attribute
        if (character.getMegaAttributeValue(megaAttributeName) == 0) {
            Enhancement enhancement = new Enhancement();
            enhancement.setName(enhancementName);
            enhancement.setMegaAttribute(megaAttribute);
            enhancement.setCharacter(character);
        }
        // Increase mega-attribute value
        megaAttribute.setValue(currentMegaAttributeValue + 1);
        // Deduct points spent
        if (isNewChar) {
            character.setNovaPoints(character.getNovaPoints() - cost);
        } else {
            character.setExperiencePoints(character.getExperiencePoints() - cost);
        }
    }
/*
    private void spendNovaPointsOnEnhancement(Character character, String megaAttributeName, String enhancementName) {

        // Check if the character has at least one dot in the associated mega-attribute
        // (Assuming enhancements are associated with mega-attributes)
        // Example: if (character.getMegaAttributeDexterity() >= 1) {

        // Purchase enhancements
        for (int i = 0; i < enhancementsToPurchase; i++) {
            // Create and add the enhancement to the character
            Enhancement enhancement = new Enhancement();
            // Set enhancement properties as needed
            // Example: enhancement.setMegaAttribute(character.getMegaAttributeDexterity());
            // Add enhancement to character's list of enhancements
            character.getEnhancements().add(enhancement);
        }
        // Deduct nova points spent
        character.setNovaPoints(character.getNovaPoints() - 3);
    }
*/

    private void spendNovaPointsOnNormalAttributes(Character character, int novaPointsSpent) {
        // Calculate the number of dots to increase for normal attributes
        int dotsToIncrease = novaPointsSpent * 3; // 1 nova point for 3 dots increase

        // Distribute dots across attributes (Example: increase three attributes by one dot each)
        // Adjust this logic based on your game's requirements
        // Example: character.setStrength(character.getStrength() + 1);
        //          character.setDexterity(character.getDexterity() + 1);
        //          character.setIntelligence(character.getIntelligence() + 1);

        // Deduct nova points spent
        character.setNovaPoints(character.getNovaPoints() - novaPointsSpent);
    }

    private void spendNovaPointsOnAbilities(Character character, int novaPointsSpent) {
        // Calculate the number of dots to increase for abilities
        int dotsToIncrease = novaPointsSpent * 6; // 1 nova point for 6 dots increase

        // Distribute dots across abilities (Example: increase all abilities by six dots each)
        // Adjust this logic based on your game's requirements
        // Example: character.getAbilities().forEach(ability -> ability.setValue(ability.getValue() + 6));

        // Deduct nova points spent
        character.setNovaPoints(character.getNovaPoints() - novaPointsSpent);
    }
}
