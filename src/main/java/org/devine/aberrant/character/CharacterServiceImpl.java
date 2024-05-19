package org.devine.aberrant.character;

import jakarta.persistence.EntityNotFoundException;
import org.devine.aberrant.ability.Ability;
import org.devine.aberrant.ability.AllocateAbilityPointsRequest;
import org.devine.aberrant.ability.Specialty;
import org.devine.aberrant.attribute.AllocateAttributePointsRequest;
import org.devine.aberrant.attribute.Attribute;
import org.devine.aberrant.attribute.AttributeSet;
import org.devine.aberrant.attribute.Quality;
import org.devine.aberrant.background.Background;
import org.devine.aberrant.megaAttribute.Enhancement;
import org.devine.aberrant.megaAttribute.MegaAttribute;
import org.devine.aberrant.power.Power;
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
    public Character createCharacter(CharacterCreationRequest request) {
        // Create a new character entity using the data from the request
        Character character = new Character();
        character.setName(request.getName());
        character.setPlayer(request.getPlayer());
        character.setNovaName(request.getNovaName());
        character.setConcept(request.getConcept());
        character.setNature(request.getNature());
        character.setAllegiance(request.getAllegiance());
        character.setDescription(request.getDescription());
        character.setAttributePoints(request.getAttributePoints());
        character.setAbilityPoints(request.getAbilityPoints());
        character.setBackgroundPoints(request.getBackgroundPoints());
        character.setBonusPoints(request.getBonusPoints());
        character.setNovaPoints(request.getNovaPoints());
        character.setExperiencePoints(request.getExperiencePoints());

        // Save the character entity in the database
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
    public Character allocateAttributePoints(Character character, AllocateAttributePointsRequest request) throws IllegalArgumentException {
        // Validate if the character has enough attribute points
        int totalPointsAllocated = request.getAttributeValues().values().stream().mapToInt(Integer::intValue).sum();
        if (totalPointsAllocated > character.getAttributePoints()) {
            throw new IllegalArgumentException("Insufficient attribute points");
        }

        // Iterate over attribute values and update the corresponding attributes in the attribute set
        for (Map.Entry<String, Integer> entry : request.getAttributeValues().entrySet()) {
            String attributeName = entry.getKey();
            int attributeValue = entry.getValue();

            // Find the attribute with the given name in the attribute set
            Attribute attribute = character.getAttributes().stream()
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
                String qualityName = request.getQualityDetails().getOrDefault(attributeName + "_name", "High " + attributeName);
                String qualityDescription = request.getQualityDetails().getOrDefault(attributeName + "_description", "A quality associated with a high " + attributeName + " attribute value.");
                Quality quality = new Quality();
                quality.setName(qualityName);
                quality.setDescription(qualityDescription);
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
    public Character allocateAbilityPoints(Character character, AllocateAbilityPointsRequest request) throws IllegalArgumentException {
        // Validate total points allocated
        int totalPointsAllocated = request.getAbilityValues().values().stream().mapToInt(Integer::intValue).sum();
        if (totalPointsAllocated > character.getAbilityPoints()) {
            throw new IllegalArgumentException("Insufficient ability points");
        }

        // Iterate over the provided ability values
        for (Map.Entry<String, Integer> entry : request.getAbilityValues().entrySet()) {
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
    public Character allocateBackgroundPoints(Character character, AllocateBackgroundPointsRequest request) throws IllegalArgumentException {
        // Validate total points allocated
        int totalPointsAllocated = request.getBackgroundValues().values().stream().mapToInt(Integer::intValue).sum();
        if (totalPointsAllocated > character.getBackgroundPoints()) {
            throw new IllegalArgumentException("Insufficient ability points");
        }

        // Iterate over the provided background values
        for (Map.Entry<String, Integer> entry : request.getBackgroundValues().entrySet()) {
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
    public void increaseAttribute(Character character, String attributeName, String qualityName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException {
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
            if (isNova) { // One Nova point grants 3 dots in attributes
                if (character.getNoOfAttrsBoughtWithNovaPts() % 3 == 0) {cost = 1;}
                    else {cost = 0;}
            }   else {cost = 5;}
        }   else {
            cost = currentAttributeValue * 4;
        }
        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }
        // Character cannot have an attribute value greater than 5
        if (character.getAttributeValue(attributeName) >= 5) {
            throw new IllegalArgumentException("Attribute value must be less than or equal to 5");
        }
        // Increase attribute value
        attribute.setValue(currentAttributeValue + 1);
        // Check if the attribute value is 4 or higher
        if (character.getAttributeValue(attributeName) == 4) {
            Quality quality = new Quality();
            quality.setName(qualityName);
            quality.setAttribute(attribute);
            attribute.getQualities().add(quality);
        }
        // Deduct points spent
        if (isNewChar) {
            if (isNova) {
                attribute.setNovaPurchased(attribute.getNovaPurchased() + 1);
                character.setNovaPoints(character.getNovaPoints() - cost);
            }   else {
                character.setBonusPoints(character.getBonusPoints() - cost);
            }
        }   else {
            character.setExperiencePoints(character.getExperiencePoints() - cost);
        }
    }

    @Override
    public void increaseAbility(Character character, String abilityName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException {
        // Find the ability by name
        Ability ability = character.getAbilities().stream()
                .filter(abil -> abil.getName().equalsIgnoreCase(abilityName))
                .findFirst().orElseGet(() -> {
                    Ability newAbility = new Ability();
                    newAbility.setCharacter(character);
                    newAbility.setName(abilityName);
                    newAbility.setValue(0);
                    return newAbility;
                });
        // Get current ability value
        int currentAbilityValue = character.getAbilityValue(abilityName);

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
            if (isNova) { // One Nova point grants 6 dots in abilities
                if (character.getNoOfAbilsBoughtWithNovaPts() % 6 == 0) {cost = 1;}
                    else {cost = 0;}
            }   else {cost = 2;}
        }   else {
            cost = (currentAbilityValue == 0) ? 3 : (currentAbilityValue * 2);
        }
        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }
        // Character cannot have an ability value greater than 5
        if (character.getAbilityValue(abilityName) >= 5) {
            throw new IllegalArgumentException("Ability value must be less than or equal to 5");
        }
        // Increase ability value
        ability.setValue(currentAbilityValue + 1);
        // Deduct points spent
        if (isNewChar) {
            if (isNova) {
                ability.setNovaPurchased(ability.getNovaPurchased() + 1);
                character.setNovaPoints(character.getNovaPoints() - cost);
            }   else {
                character.setBonusPoints(character.getBonusPoints() - cost);
            }
        }   else {
            character.setExperiencePoints(character.getExperiencePoints() - cost);
        }
    }

    @Override
    public void addAbilitySpecialty(Character character, String abilityName, String specialtyName, Boolean isNewChar) throws IllegalArgumentException {
        // Find the ability by name
        Ability ability = character.getAbilities().stream()
                .filter(abil -> abil.getName().equalsIgnoreCase(abilityName))
                .findFirst().orElseGet(() -> {
                    Ability newAbility = new Ability();
                    newAbility.setCharacter(character);
                    newAbility.setName(abilityName);
                    newAbility.setValue(0);
                    return newAbility;
                });
        // Check if the ability already has maximum specialties
        if (ability.getSpecialties().size() >= 3) {
            throw new IllegalArgumentException("Maximum specialties reached for ability: " + abilityName);
        }
        // Is this character creation or increase through exp
        int pointsToSpend = isNewChar ? character.getBonusPoints() : character.getExperiencePoints();

        // Check to see if character has sufficient funds
        if (pointsToSpend < 1) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }

        // Create and add the specialty
        Specialty specialty = new Specialty();
        specialty.setName(specialtyName);
        specialty.setAbility(ability);
        ability.getSpecialties().add(specialty);

        // Deduct points spent
        if (isNewChar) {
            character.setNovaPoints(character.getNovaPoints() - 1);
        } else {
            character.setExperiencePoints(character.getExperiencePoints() - 1);
        }
    }

    @Override
    public void increaseBackground(Character character, String backgroundName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException {
        // Find the background by name
        Background background = character.getBackgrounds().stream()
                .filter(bkgr -> bkgr.getName().equalsIgnoreCase(backgroundName))
                .findFirst().orElseGet(() -> {
                    Background newBackground = new Background();
                    newBackground.setCharacter(character);
                    newBackground.setName(backgroundName);
                    newBackground.setValue(0);
                    return newBackground;
                });
        // Get current background value
        int currentBackgroundValue = character.getBackgroundValue(backgroundName);

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
            if (isNova) { // One Nova point grants 5 dots in backgrounds
                if (character.getNoOfBkgrsBoughtWithNovaPts() % 5 == 0) {cost = 1;}
                    else {cost = 0;}
            }   else {cost = 1;}
        }   else {
            cost = (currentBackgroundValue == 0) ? 2 : (currentBackgroundValue * 2);
        }
        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }
        // Character cannot have a background value greater than 5
        if (character.getBackgroundValue(backgroundName) >= 5) {
            throw new IllegalArgumentException("Background value must be less than or equal to 5");
        }
        // Increase background value and add taint, if necessary
        background.setValue(currentBackgroundValue + 1);
        if (background.getName().equalsIgnoreCase("node") && background.getValue() >= 3) {
            character.setTaint(character.getTaint() + 1);
        }

        // Deduct points spent
        if (isNewChar) {
            if (isNova) {
                background.setNovaPurchased(background.getNovaPurchased() + 1);
                character.setNovaPoints(character.getNovaPoints() - cost);
            }   else {
                character.setBonusPoints(character.getBonusPoints() - cost);
            }
        }   else {
            character.setExperiencePoints(character.getExperiencePoints() - cost);
        }
    }

    @Override
    public void increaseWillpower(Character character, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException {
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
            if (isNova) {cost = 1;}
                else {cost = 2;}
        }   else {cost = character.getWillpower();}
        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }

        // Increase willpower value
        character.setWillpower(character.getWillpower() + 1);

        // Deduct points spent
        if (isNewChar) {
            if (isNova) {
                character.setNovaPoints(character.getNovaPoints() - cost);
            }   else {
                character.setBonusPoints(character.getBonusPoints() - cost);
            }
        }   else {
            character.setExperiencePoints(character.getExperiencePoints() - cost);
        }
    }

    @Override
    public void increaseQuantum(Character character, Boolean isNewChar, Boolean isNova, Boolean isTainted) throws IllegalArgumentException {
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
            if (isNova) cost = !isTainted ? 5 : 3;
                else cost = 7;
        }   else cost = character.getQuantum() * (!isTainted ? 8 : 4);
        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }

        // Increase Quantum value and Quantum pool, if necessary
        character.setQuantum(character.getQuantum() + 1);
        if (isNewChar) character.setQuantumPool(character.getQuantumPool() + 2);
        if (isTainted) character.setTaint(character.getTaint() + 1);
        if (character.getQuantum() >= 5) character.setTaint(character.getTaint() + 1);

        // Deduct points spent
        if (isNewChar) {
            if (isNova) {
                character.setNovaPoints(character.getNovaPoints() - cost);
            }   else {
                character.setBonusPoints(character.getBonusPoints() - cost);
            }
        }   else {
            character.setExperiencePoints(character.getExperiencePoints() - cost);
        }

    }

    @Override
    public void increaseInitiative(Character character, Boolean isNewChar) throws IllegalArgumentException {
        // Is this character creation or increase through exp
        int pointsToSpend;
        if (isNewChar) {
            pointsToSpend = character.getBonusPoints();
        }   else {
            pointsToSpend = character.getExperiencePoints();
        }
        int cost;
        if (isNewChar) {cost = 2;}
            else {cost = character.getInitiative();}
        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }

        // Increase initiative value
        character.setInitiative(character.getInitiative() + 1);

        // Deduct points spent
        if (isNewChar) {
            character.setBonusPoints(character.getBonusPoints() - cost);
        }   else {
            character.setExperiencePoints(character.getExperiencePoints() - cost);
        }

    }

    @Override
    public void addMerit(Character character, String meritName, int meritValue) throws IllegalArgumentException {
        // Check to see if character has sufficient funds
        if (meritValue < character.getBonusPoints()) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }
        // Create and add the merit
        Merit merit = new Merit();
        merit.setName(meritName);
        merit.setValue(meritValue);
        merit.setCharacter(character);
        character.getMerits().add(merit);

        // Deduct bonus points for adding a merit
        character.setBonusPoints(character.getBonusPoints() - meritValue);
    }

    @Override
    public void addFlaw(Character character, String flawName, int flawValue) throws IllegalArgumentException {
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

        // Add bonus points for taking a flaw
        character.setBonusPoints(character.getBonusPoints() + flawValue);
    }

    @Override
    public void increaseMegaAttribute(Character character, String megaAttributeName, String enhancementName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException {
        // Find the attribute by name or initialize new mega-attribute
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
        int cost;
        double costDouble;
        if (isNewChar) {
            if (isTainted) cost = 2;
            else cost = 3;
        } else {
            if (currentMegaAttributeValue == 0) {
                if (isTainted) cost = 3;
                else cost = 6;
            } else {
                costDouble = (double) (currentMegaAttributeValue * 5) / (isTainted ? 2 : 1);
                cost = (int) Math.round(costDouble);
            }
        }

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
        if (isTainted) character.setTaint(character.getTaint() + 1);
        // Deduct points spent
        if (isNewChar) {
            character.setNovaPoints(character.getNovaPoints() - cost);
        } else {
            character.setExperiencePoints(character.getExperiencePoints() - cost);
        }
    }

    @Override
    public void addEnhancement(Character character, String megaAttributeName, String enhancementName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException {
        // Check if the character has at least one dot in the associated mega-attribute
        MegaAttribute megaAttribute = character.getMegaAttributes().stream()
                .filter(attr -> attr.getName().equalsIgnoreCase(megaAttributeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Mega-Attribute not found: " + megaAttributeName));

        // Is this character creation or increase through exp
        int pointsToSpend = isNewChar ? character.getNovaPoints() : character.getExperiencePoints();
        int cost;
        if (isNewChar) {
            if (isTainted) cost = 2;
            else cost = 3;
        } else {
            if (isTainted) cost = 3;
            else cost = 5;
        }

        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }

        // Create enhancement and add it to character
        Enhancement newEnhancement = new Enhancement();
        newEnhancement.setCharacter(character);
        newEnhancement.setMegaAttribute(megaAttribute);
        newEnhancement.setName(enhancementName);
        character.getEnhancements().add(newEnhancement);
        if (isTainted) character.setTaint(character.getTaint() + 1);

        // Deduct points spent
        if (isNewChar) {
            character.setNovaPoints(character.getNovaPoints() - cost);
        } else {
            character.setExperiencePoints(character.getExperiencePoints() - cost);
        }
    }

    @Override
    public void increasePower(Character character, String powerName, int level, int quantumMinimum, String attributeName,
                              Boolean hasExtra, String extraName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException {
        // Find the attribute by name
        Attribute attribute = character.getAttributes().stream()
                .filter(attr -> attr.getName().equalsIgnoreCase(attributeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + attributeName)); // TODO should return N/A for no attribute

        int powerLevel;
        if (hasExtra) {
            powerLevel = level + 1;
        }   else {
            powerLevel = level;
        }
        // Find the power by name or initialize new power
        Power power = character.getPowers().stream()
                .filter(pwr -> pwr.getName().equalsIgnoreCase(powerName))
                .findFirst().orElseGet(() -> {
                    Power newPower = new Power();
                    newPower.setName(powerName);
                    newPower.setValue(0);
                    newPower.setLevel(powerLevel);
                    newPower.setQuantumMinimum(quantumMinimum);
                    newPower.setHasExtra(hasExtra);
                    newPower.setExtraName(extraName);
                    newPower.setCharacter(character);
                    newPower.setAttribute(attribute);
                    return newPower;
                });

        // Get the current power's value
        int currentPowerValue = character.getPowerValue(powerName);

        // Is this character creation or increase through exp
        int pointsToSpend = isNewChar ? character.getNovaPoints() : character.getExperiencePoints();
        int cost;
        int novaPtCost = (int) (Math.pow(2, (powerLevel - 1)) + 1);
        int expMultiplier = (powerLevel * 2) + 1;
        double costDouble;
        if (isNewChar) {
            if (isTainted) cost = powerLevel;
            else cost = novaPtCost;
        } else {
            if (currentPowerValue == 0) {
                if (isTainted) {
                    cost = (int) Math.round((float) (powerLevel * 3) / 2);
                } else cost = powerLevel * 3;
            } else {
                costDouble = (double) (currentPowerValue * expMultiplier) / (isTainted ? 2 : 1);
                cost = (int) Math.round(costDouble);
            }
        }

        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }
        // Checks that a character has the minimum Quantum for the power
        if (quantumMinimum > (character.getQuantum())) {
            throw new IllegalArgumentException("Power's Quantum minimum exceeds character's current Quantum");
        }
        // Increase power value
        power.setValue(currentPowerValue + 1);
        if (isTainted && powerLevel == 1) {
            power.setValue(character.getPowerValue(powerName) + 1);
        }
        if (isTainted) character.setTaint(character.getTaint() + 1);
        // Deduct points spent
        if (isNewChar) {
            character.setNovaPoints(character.getNovaPoints() - cost);
        } else {
            character.setExperiencePoints(character.getExperiencePoints() - cost);
        }
    }

    @Override
    public void increaseQuantumPool(Character character) throws IllegalArgumentException {
        // Check to see if character has sufficient funds
        if (character.getNovaPoints() < 1) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }

        // Increase quantum pool value
        character.setQuantumPool(character.getQuantumPool() + 2);

        // Deduct points spent
        character.setNovaPoints(character.getNovaPoints() - 1);
    }

    @Override
    public Character saveCharacter(Character character) {
        return characterRepository.save(character);
    }

}
