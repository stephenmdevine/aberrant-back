package org.devine.aberrant.service;

import jakarta.persistence.EntityNotFoundException;
import org.devine.aberrant.model.*;
import org.devine.aberrant.repository.GameCharacterRepository;
import org.devine.aberrant.request.AllocateAbilityPointsRequest;
import org.devine.aberrant.request.AllocateAttributePointsRequest;
import org.devine.aberrant.request.AllocateBackgroundPointsRequest;
import org.devine.aberrant.request.GameCharacterCreationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GameCharacterServiceImpl implements GameCharacterService {

    private GameCharacterRepository gameCharacterRepository;

    public GameCharacterServiceImpl(GameCharacterRepository gameCharacterRepository) {
        this.gameCharacterRepository = gameCharacterRepository;
    }

    @Override
    public GameCharacter createCharacter(GameCharacterCreationRequest request) {
//      Create a new character entity using the data from the request
        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setName(request.getName());
        gameCharacter.setPlayer(request.getPlayer());
        gameCharacter.setNovaName(request.getNovaName());
        gameCharacter.setConcept(request.getConcept());
        gameCharacter.setNature(request.getNature());
        gameCharacter.setAllegiance(request.getAllegiance());
        gameCharacter.setDescription(request.getDescription());
        gameCharacter.setAttributePoints(request.getAttributePoints());
        gameCharacter.setAbilityPoints(request.getAbilityPoints());
        gameCharacter.setBackgroundPoints(request.getBackgroundPoints());
        gameCharacter.setBonusPoints(request.getBonusPoints());
        gameCharacter.setNovaPoints(request.getNovaPoints());
        gameCharacter.setExperiencePoints(request.getExperiencePoints());

//      Save the character entity in the database
        return gameCharacterRepository.save(gameCharacter);
    }

    @Override
    public List<GameCharacter> findAll() {
        return gameCharacterRepository.findAll();
    }

    @Override
    public GameCharacter findById(Long gameCharacterId) {
        // Retrieve the character from the database by ID
        Optional<GameCharacter> gameCharacterOptional = gameCharacterRepository.findById(gameCharacterId);

        // If the character is found, return it; otherwise, throw an exception
        return gameCharacterOptional.orElseThrow(() -> new EntityNotFoundException("Character not found with id: " + gameCharacterId));
    }

    @Override
    public GameCharacter saveCharacter(GameCharacter gameCharacter) {
        return gameCharacterRepository.save(gameCharacter);
    }

    @Override
    public GameCharacter allocateAttributePoints(GameCharacter gameCharacter, AllocateAttributePointsRequest request) throws IllegalArgumentException {
        // Validate if the character has enough attribute points
        int totalPointsAllocated = request.getAttributeValues().values().stream().mapToInt(Integer::intValue).sum();
        if (totalPointsAllocated > gameCharacter.getAttributePoints()) {
            throw new IllegalArgumentException("Insufficient attribute points");
        }

        // Iterate over attribute values and update the corresponding attributes in the attribute set
        for (Map.Entry<String, Integer> entry : request.getAttributeValues().entrySet()) {
            String attributeName = entry.getKey();
            int attributeValue = entry.getValue();

            // Find the attribute with the given name in the attribute set
            Attribute attribute = gameCharacter.getAttributes().stream()
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
        int remainingPoints = gameCharacter.getAttributePoints() - totalPointsAllocated;
        gameCharacter.setAttributePoints(remainingPoints);

        // Save the changes to the character and return
        return gameCharacterRepository.save(gameCharacter);
    }

    @Override
    public GameCharacter allocateAbilityPoints(GameCharacter gameCharacter, AllocateAbilityPointsRequest request) throws IllegalArgumentException {
        // Validate total points allocated
        int totalPointsAllocated = request.getAbilityValues().values().stream().mapToInt(Integer::intValue).sum();
        if (totalPointsAllocated > gameCharacter.getAbilityPoints()) {
            throw new IllegalArgumentException("Insufficient ability points");
        }

        // Iterate over the provided ability values
        for (Map.Entry<String, Integer> entry : request.getAbilityValues().entrySet()) {
            String abilityName = entry.getKey();
            int abilityValue = entry.getValue();

            // Find the ability by name
            Ability ability = gameCharacter.getAbilities().stream()
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
        int remainingPoints = gameCharacter.getAbilityPoints() - totalPointsAllocated;
        gameCharacter.setAbilityPoints(remainingPoints);

        // Save the changes to the character and return
        return gameCharacterRepository.save(gameCharacter);
    }

    @Override
    public GameCharacter allocateBackgroundPoints(GameCharacter gameCharacter, AllocateBackgroundPointsRequest request) throws IllegalArgumentException {
        // Validate total points allocated
        int totalPointsAllocated = request.getBackgroundValues().values().stream().mapToInt(Integer::intValue).sum();
        if (totalPointsAllocated > gameCharacter.getBackgroundPoints()) {
            throw new IllegalArgumentException("Insufficient ability points");
        }

        // Iterate over the provided background values
        for (Map.Entry<String, Integer> entry : request.getBackgroundValues().entrySet()) {
            String backgroundName = entry.getKey();
            int backgroundValue = entry.getValue();

            // Find the background by name
            Background background = gameCharacter.getBackgrounds().stream()
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
        int remainingPoints = gameCharacter.getBackgroundPoints() - totalPointsAllocated;
        gameCharacter.setBackgroundPoints(remainingPoints);

        // Save the changes to the character and return
        return gameCharacterRepository.save(gameCharacter);
    }

    @Override
    public void increaseAttribute(GameCharacter gameCharacter, String attributeName, String qualityName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException {
        // Find the attribute by name
        Attribute attribute = gameCharacter.getAttributes().stream()
                .filter(attr -> attr.getName().equalsIgnoreCase(attributeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + attributeName));
        // Get current attribute value
        int currentAttributeValue = gameCharacter.getAttributeValue(attributeName);

        // Is this character creation or increase through exp
        int pointsToSpend;
        if (isNewChar) {
            if (isNova) {
                pointsToSpend = gameCharacter.getNovaPoints();
            }   else {
                pointsToSpend = gameCharacter.getBonusPoints();
            }
        }   else {
            pointsToSpend = gameCharacter.getExperiencePoints();
        }
        int cost;
        if (isNewChar) {
            if (isNova) { // One Nova point grants 3 dots in attributes
                if (gameCharacter.getNoOfAttrsBoughtWithNovaPts() % 3 == 0) {cost = 1;}
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
        if (gameCharacter.getAttributeValue(attributeName) >= 5) {
            throw new IllegalArgumentException("Attribute value must be less than or equal to 5");
        }
        // Increase attribute value
        attribute.setValue(currentAttributeValue + 1);
        // Check if the attribute value is 4 or higher
        if (gameCharacter.getAttributeValue(attributeName) == 4) {
            Quality quality = new Quality();
            quality.setName(qualityName);
            quality.setAttribute(attribute);
            attribute.getQualities().add(quality);
        }
        // Deduct points spent
        if (isNewChar) {
            if (isNova) {
                attribute.setNovaPurchased(attribute.getNovaPurchased() + 1);
                gameCharacter.setNovaPoints(gameCharacter.getNovaPoints() - cost);
            }   else {
                gameCharacter.setBonusPoints(gameCharacter.getBonusPoints() - cost);
            }
        }   else {
            gameCharacter.setExperiencePoints(gameCharacter.getExperiencePoints() - cost);
        }
    }

    @Override
    public void increaseAbility(GameCharacter gameCharacter, String abilityName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException {
        // Find the ability by name
        Ability ability = gameCharacter.getAbilities().stream()
                .filter(abil -> abil.getName().equalsIgnoreCase(abilityName))
                .findFirst().orElseGet(() -> {
                    Ability newAbility = new Ability();
                    newAbility.setGameCharacter(gameCharacter);
                    newAbility.setName(abilityName);
                    newAbility.setValue(0);
                    return newAbility;
                });
        // Get current ability value
        int currentAbilityValue = gameCharacter.getAbilityValue(abilityName);

        // Is this character creation or increase through exp
        int pointsToSpend;
        if (isNewChar) {
            if (isNova) {
                pointsToSpend = gameCharacter.getNovaPoints();
            }   else {
                pointsToSpend = gameCharacter.getBonusPoints();
            }
        }   else {
            pointsToSpend = gameCharacter.getExperiencePoints();
        }
        int cost;
        if (isNewChar) {
            if (isNova) { // One Nova point grants 6 dots in abilities
                if (gameCharacter.getNoOfAbilsBoughtWithNovaPts() % 6 == 0) {cost = 1;}
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
        if (gameCharacter.getAbilityValue(abilityName) >= 5) {
            throw new IllegalArgumentException("Ability value must be less than or equal to 5");
        }
        // Increase ability value
        ability.setValue(currentAbilityValue + 1);
        // Deduct points spent
        if (isNewChar) {
            if (isNova) {
                ability.setNovaPurchased(ability.getNovaPurchased() + 1);
                gameCharacter.setNovaPoints(gameCharacter.getNovaPoints() - cost);
            }   else {
                gameCharacter.setBonusPoints(gameCharacter.getBonusPoints() - cost);
            }
        }   else {
            gameCharacter.setExperiencePoints(gameCharacter.getExperiencePoints() - cost);
        }
    }

    @Override
    public void addAbilitySpecialty(GameCharacter gameCharacter, String abilityName, String specialtyName, Boolean isNewChar) throws IllegalArgumentException {
        // Find the ability by name
        Ability ability = gameCharacter.getAbilities().stream()
                .filter(abil -> abil.getName().equalsIgnoreCase(abilityName))
                .findFirst().orElseGet(() -> {
                    Ability newAbility = new Ability();
                    newAbility.setGameCharacter(gameCharacter);
                    newAbility.setName(abilityName);
                    newAbility.setValue(0);
                    return newAbility;
                });
        // Check if the ability already has maximum specialties
        if (ability.getSpecialties().size() >= 3) {
            throw new IllegalArgumentException("Maximum specialties reached for ability: " + abilityName);
        }
        // Is this character creation or increase through exp
        int pointsToSpend = isNewChar ? gameCharacter.getBonusPoints() : gameCharacter.getExperiencePoints();

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
            gameCharacter.setNovaPoints(gameCharacter.getNovaPoints() - 1);
        } else {
            gameCharacter.setExperiencePoints(gameCharacter.getExperiencePoints() - 1);
        }
    }

    @Override
    public void increaseBackground(GameCharacter gameCharacter, String backgroundName, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException {
        // Find the background by name
        Background background = gameCharacter.getBackgrounds().stream()
                .filter(bkgr -> bkgr.getName().equalsIgnoreCase(backgroundName))
                .findFirst().orElseGet(() -> {
                    Background newBackground = new Background();
                    newBackground.setGameCharacter(gameCharacter);
                    newBackground.setName(backgroundName);
                    newBackground.setValue(0);
                    return newBackground;
                });
        // Get current background value
        int currentBackgroundValue = gameCharacter.getBackgroundValue(backgroundName);

        // Is this character creation or increase through exp
        int pointsToSpend;
        if (isNewChar) {
            if (isNova) {
                pointsToSpend = gameCharacter.getNovaPoints();
            }   else {
                pointsToSpend = gameCharacter.getBonusPoints();
            }
        }   else {
            pointsToSpend = gameCharacter.getExperiencePoints();
        }
        int cost;
        if (isNewChar) {
            if (isNova) { // One Nova point grants 5 dots in backgrounds
                if (gameCharacter.getNoOfBkgrsBoughtWithNovaPts() % 5 == 0) {cost = 1;}
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
        if (gameCharacter.getBackgroundValue(backgroundName) >= 5) {
            throw new IllegalArgumentException("Background value must be less than or equal to 5");
        }
        // Increase background value and add taint, if necessary
        background.setValue(currentBackgroundValue + 1);
        if (background.getName().equalsIgnoreCase("node") && background.getValue() >= 3) {
            gameCharacter.setTaint(gameCharacter.getTaint() + 1);
        }

        // Deduct points spent
        if (isNewChar) {
            if (isNova) {
                background.setNovaPurchased(background.getNovaPurchased() + 1);
                gameCharacter.setNovaPoints(gameCharacter.getNovaPoints() - cost);
            }   else {
                gameCharacter.setBonusPoints(gameCharacter.getBonusPoints() - cost);
            }
        }   else {
            gameCharacter.setExperiencePoints(gameCharacter.getExperiencePoints() - cost);
        }
    }

    @Override
    public void increaseWillpower(GameCharacter gameCharacter, Boolean isNewChar, Boolean isNova) throws IllegalArgumentException {
        // Is this character creation or increase through exp
        int pointsToSpend;
        if (isNewChar) {
            if (isNova) {
                pointsToSpend = gameCharacter.getNovaPoints();
            }   else {
                pointsToSpend = gameCharacter.getBonusPoints();
            }
        }   else {
            pointsToSpend = gameCharacter.getExperiencePoints();
        }
        int cost;
        if (isNewChar) {
            if (isNova) {cost = 1;}
            else {cost = 2;}
        }   else {cost = gameCharacter.getWillpower();}
        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }

        // Increase willpower value
        gameCharacter.setWillpower(gameCharacter.getWillpower() + 1);

        // Deduct points spent
        if (isNewChar) {
            if (isNova) {
                gameCharacter.setNovaPoints(gameCharacter.getNovaPoints() - cost);
            }   else {
                gameCharacter.setBonusPoints(gameCharacter.getBonusPoints() - cost);
            }
        }   else {
            gameCharacter.setExperiencePoints(gameCharacter.getExperiencePoints() - cost);
        }
    }

    @Override
    public void increaseQuantum(GameCharacter gameCharacter, Boolean isNewChar, Boolean isNova, Boolean isTainted) throws IllegalArgumentException {
        // Is this character creation or increase through exp
        int pointsToSpend;
        if (isNewChar) {
            if (isNova) {
                pointsToSpend = gameCharacter.getNovaPoints();
            }   else {
                pointsToSpend = gameCharacter.getBonusPoints();
            }
        }   else {
            pointsToSpend = gameCharacter.getExperiencePoints();
        }
        int cost;
        if (isNewChar) {
            if (isNova) cost = !isTainted ? 5 : 3;
            else cost = 7;
        }   else cost = gameCharacter.getQuantum() * (!isTainted ? 8 : 4);
        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }

        // Increase Quantum value and Quantum pool, if necessary
        gameCharacter.setQuantum(gameCharacter.getQuantum() + 1);
        if (isNewChar) gameCharacter.setQuantumPool(gameCharacter.getQuantumPool() + 2);
        if (isTainted) gameCharacter.setTaint(gameCharacter.getTaint() + 1);
        if (gameCharacter.getQuantum() >= 5) gameCharacter.setTaint(gameCharacter.getTaint() + 1);

        // Deduct points spent
        if (isNewChar) {
            if (isNova) {
                gameCharacter.setNovaPoints(gameCharacter.getNovaPoints() - cost);
            }   else {
                gameCharacter.setBonusPoints(gameCharacter.getBonusPoints() - cost);
            }
        }   else {
            gameCharacter.setExperiencePoints(gameCharacter.getExperiencePoints() - cost);
        }
    }

    @Override
    public void increaseInitiative(GameCharacter gameCharacter, Boolean isNewChar) throws IllegalArgumentException {
        // Is this character creation or increase through exp
        int pointsToSpend;
        if (isNewChar) {
            pointsToSpend = gameCharacter.getBonusPoints();
        }   else {
            pointsToSpend = gameCharacter.getExperiencePoints();
        }
        int cost;
        if (isNewChar) {cost = 2;}
        else {cost = gameCharacter.getInitiative();}
        // Check to see if character has sufficient funds
        if (pointsToSpend < cost) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }

        // Increase initiative value
        gameCharacter.setInitiative(gameCharacter.getInitiative() + 1);

        // Deduct points spent
        if (isNewChar) {
            gameCharacter.setBonusPoints(gameCharacter.getBonusPoints() - cost);
        }   else {
            gameCharacter.setExperiencePoints(gameCharacter.getExperiencePoints() - cost);
        }
    }

    @Override
    public void addMerit(GameCharacter gameCharacter, String meritName, int meritValue) throws IllegalArgumentException {
        // Check to see if character has sufficient funds
        if (meritValue < gameCharacter.getBonusPoints()) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }
        // Create and add the merit
        Merit merit = new Merit();
        merit.setName(meritName);
        merit.setValue(meritValue);
        merit.setGameCharacter(gameCharacter);
        gameCharacter.getMerits().add(merit);

        // Deduct bonus points for adding a merit
        gameCharacter.setBonusPoints(gameCharacter.getBonusPoints() - meritValue);
    }

    @Override
    public void addFlaw(GameCharacter gameCharacter, String flawName, int flawValue) throws IllegalArgumentException {
        // Check if the total flaw points exceed 10
        int totalFlawPoints = gameCharacter.getFlaws().stream().mapToInt(Flaw::getValue).sum();
        if (totalFlawPoints + flawValue > 10) {
            throw new IllegalArgumentException("Total flaw points exceed limit");
        }
        // Create and add the flaw
        Flaw flaw = new Flaw();
        flaw.setName(flawName);
        flaw.setValue(flawValue);
        flaw.setGameCharacter(gameCharacter);
        gameCharacter.getFlaws().add(flaw);

        // Add bonus points for taking a flaw
        gameCharacter.setBonusPoints(gameCharacter.getBonusPoints() + flawValue);
    }

    @Override
    public void increaseMegaAttribute(GameCharacter gameCharacter, String megaAttributeName, String enhancementName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException {
        // Find the attribute by name or initialize new mega-attribute
        MegaAttribute megaAttribute = gameCharacter.getMegaAttributes().stream()
                .filter(attr -> attr.getName().equalsIgnoreCase(megaAttributeName))
                .findFirst().orElseGet(() -> {
                    MegaAttribute newMegaAttribute = new MegaAttribute();
                    newMegaAttribute.setGameCharacter(gameCharacter);
                    newMegaAttribute.setName(megaAttributeName);
                    newMegaAttribute.setValue(0);
                    return newMegaAttribute;
                });
        // Get current mega-attribute value
        int currentMegaAttributeValue = gameCharacter.getMegaAttributeValue(megaAttributeName);
        // Get baseline attribute corresponding to mega-attribute
        String attributeName = megaAttributeName.substring(4);

        // Is this character creation or increase through exp
        int pointsToSpend = isNewChar ? gameCharacter.getNovaPoints() : gameCharacter.getExperiencePoints();
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
        if (gameCharacter.getMegaAttributeValue(megaAttributeName) >= gameCharacter.getAttributeValue(attributeName)) {
            throw new IllegalArgumentException("Mega attribute value cannot exceed attribute value");
        }
        // Character cannot have a higher Mega-attribute value than they have a Quantum value minus one
        if (gameCharacter.getMegaAttributeValue(megaAttributeName) >= (gameCharacter.getQuantum() - 1)) {
            throw new IllegalArgumentException("Mega attribute value must be less than Quantum value");
        }
        // Gain a new Enhancement if this is the first dot in a Mega-attribute
        if (gameCharacter.getMegaAttributeValue(megaAttributeName) == 0) {
            Enhancement enhancement = new Enhancement();
            enhancement.setName(enhancementName);
            enhancement.setMegaAttribute(megaAttribute);
            enhancement.setGameCharacter(gameCharacter);
        }
        // Increase mega-attribute value
        megaAttribute.setValue(currentMegaAttributeValue + 1);
        if (isTainted) gameCharacter.setTaint(gameCharacter.getTaint() + 1);
        // Deduct points spent
        if (isNewChar) {
            gameCharacter.setNovaPoints(gameCharacter.getNovaPoints() - cost);
        } else {
            gameCharacter.setExperiencePoints(gameCharacter.getExperiencePoints() - cost);
        }
    }

    @Override
    public void addEnhancement(GameCharacter gameCharacter, String megaAttributeName, String enhancementName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException {
        // Check if the character has at least one dot in the associated mega-attribute
        MegaAttribute megaAttribute = gameCharacter.getMegaAttributes().stream()
                .filter(attr -> attr.getName().equalsIgnoreCase(megaAttributeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Mega-Attribute not found: " + megaAttributeName));

        // Is this character creation or increase through exp
        int pointsToSpend = isNewChar ? gameCharacter.getNovaPoints() : gameCharacter.getExperiencePoints();
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
        newEnhancement.setGameCharacter(gameCharacter);
        newEnhancement.setMegaAttribute(megaAttribute);
        newEnhancement.setName(enhancementName);
        gameCharacter.getEnhancements().add(newEnhancement);
        if (isTainted) gameCharacter.setTaint(gameCharacter.getTaint() + 1);

        // Deduct points spent
        if (isNewChar) {
            gameCharacter.setNovaPoints(gameCharacter.getNovaPoints() - cost);
        } else {
            gameCharacter.setExperiencePoints(gameCharacter.getExperiencePoints() - cost);
        }
    }

    @Override
    public void increasePower(GameCharacter gameCharacter, String powerName, int level, int quantumMinimum, String attributeName,
                              Boolean hasExtra, String extraName, Boolean isNewChar, Boolean isTainted) throws IllegalArgumentException {
        // Find the attribute by name
        Attribute attribute = gameCharacter.getAttributes().stream()
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
        Power power = gameCharacter.getPowers().stream()
                .filter(pwr -> pwr.getName().equalsIgnoreCase(powerName))
                .findFirst().orElseGet(() -> {
                    Power newPower = new Power();
                    newPower.setName(powerName);
                    newPower.setValue(0);
                    newPower.setLevel(powerLevel);
                    newPower.setQuantumMinimum(quantumMinimum);
                    newPower.setHasExtra(hasExtra);
                    newPower.setExtraName(extraName);
                    newPower.setGameCharacter(gameCharacter);
                    newPower.setAttribute(attribute);
                    return newPower;
                });

        // Get the current power's value
        int currentPowerValue = gameCharacter.getPowerValue(powerName);

        // Is this character creation or increase through exp
        int pointsToSpend = isNewChar ? gameCharacter.getNovaPoints() : gameCharacter.getExperiencePoints();
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
        if (quantumMinimum > (gameCharacter.getQuantum())) {
            throw new IllegalArgumentException("Power's Quantum minimum exceeds character's current Quantum");
        }
        // Increase power value
        power.setValue(currentPowerValue + 1);
        if (isTainted && powerLevel == 1) {
            power.setValue(gameCharacter.getPowerValue(powerName) + 1);
        }
        if (isTainted) gameCharacter.setTaint(gameCharacter.getTaint() + 1);
        // Deduct points spent
        if (isNewChar) {
            gameCharacter.setNovaPoints(gameCharacter.getNovaPoints() - cost);
        } else {
            gameCharacter.setExperiencePoints(gameCharacter.getExperiencePoints() - cost);
        }
    }

    @Override
    public void increaseQuantumPool(GameCharacter gameCharacter) throws IllegalArgumentException {
        // Check to see if character has sufficient funds
        if (gameCharacter.getNovaPoints() < 1) {
            throw new IllegalArgumentException("Insufficient points to spend");
        }

        // Increase quantum pool value
        gameCharacter.setQuantumPool(gameCharacter.getQuantumPool() + 2);

        // Deduct points spent
        gameCharacter.setNovaPoints(gameCharacter.getNovaPoints() - 1);
    }

}
