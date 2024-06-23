package org.devine.aberrant.controller;

import org.devine.aberrant.model.GameCharacter;
import org.devine.aberrant.request.*;
import org.devine.aberrant.service.GameCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class GameCharacterController {

    @Autowired
    private GameCharacterService gameCharacterService;

    public GameCharacterController(GameCharacterService gameCharacterService) {
        this.gameCharacterService = gameCharacterService;
    }

//  Endpoint to save new character data
    @PostMapping("/new")
    GameCharacter newCharacter(@RequestBody GameCharacterCreationRequest gameCharacter) {
        return gameCharacterService.createCharacter(gameCharacter);
    }

//  Endpoint to retrieve all characters
    @GetMapping
    List<GameCharacter> getAllCharacters() {
        return gameCharacterService.findAll();
    }

//  Endpoint to retrieve a character by ID
    @GetMapping("/{gameCharacterId}")
    GameCharacter getCharacterById(@PathVariable Long gameCharacterId) {
        return gameCharacterService.findById(gameCharacterId);
    }

//  Endpoint to allocate attribute points
    @PostMapping("/{characterId}/allocateAttributePoints")
    GameCharacter allocateAttributePoints(@PathVariable Long characterId, @RequestBody AllocateAttributePointsRequest request) {
        return gameCharacterService.allocateAttributePoints(gameCharacterService.findById(characterId), request);
    }

//  Endpoint to allocate ability points
    @PostMapping("/{characterId}/allocateAbilityPoints")
    GameCharacter allocateAbilityPoints(@PathVariable Long characterId, @RequestBody AllocateAbilityPointsRequest request) {
        return gameCharacterService.allocateAbilityPoints(gameCharacterService.findById(characterId), request);
    }

//  Endpoint to allocate background points
    @PostMapping("/{characterId}/allocateBackgroundPoints")
    GameCharacter allocateBackgroundPoints(@PathVariable Long characterId, @RequestBody AllocateBackgroundPointsRequest request) {
        return gameCharacterService.allocateBackgroundPoints(gameCharacterService.findById(characterId), request);
    }

//  Endpoint to allocate bonus points during character creation
    @PostMapping("/{characterId}/spendBonusPoints")
    GameCharacter spendBonusPoints(@PathVariable Long characterId, @RequestBody SpendPointsRequest request) {
        GameCharacter gameCharacter = gameCharacterService.findById(characterId);
        gameCharacter = handlePointSpending(gameCharacter, request, true, false);
        return gameCharacterService.saveCharacter(gameCharacter);
    }

//  Endpoint to allocate nova points during character creation
    @PostMapping("/{characterId}/spendNovaPoints")
    GameCharacter spendNovaPoints(@PathVariable Long characterId, @RequestBody SpendPointsRequest request) {
        GameCharacter gameCharacter = gameCharacterService.findById(characterId);
        gameCharacter = handlePointSpending(gameCharacter, request, false, true);
        return gameCharacterService.saveCharacter(gameCharacter);
    }

//  Endpoint to allocate experience points earned during gameplay
    @PostMapping("/{characterId}/spendExperiencePoints")
    GameCharacter spendExperiencePoints(@PathVariable Long characterId, @RequestBody SpendPointsRequest request) {
        GameCharacter gameCharacter = gameCharacterService.findById(characterId);
        gameCharacter = handlePointSpending(gameCharacter, request, false, false);
        return gameCharacterService.saveCharacter(gameCharacter);
    }

    private GameCharacter handlePointSpending(GameCharacter gameCharacter, SpendPointsRequest request, boolean isBonus, boolean isNova) {
        switch (request.getPointType().toLowerCase()) {
            case "attribute":
                gameCharacterService.increaseAttribute(gameCharacter, request.getName(), request.getQualityName(), isBonus, isNova);
                break;
            case "ability":
                gameCharacterService.increaseAbility(gameCharacter, request.getName(), isBonus, isNova);
                break;
            case "background":
                gameCharacterService.increaseBackground(gameCharacter, request.getName(), isBonus, isNova);
                break;
            case "willpower":
                gameCharacterService.increaseWillpower(gameCharacter, isBonus, isNova);
                break;
            case "quantum":
                gameCharacterService.increaseQuantum(gameCharacter, isBonus, isNova, request.isTainted());
                break;
            case "initiative":
                gameCharacterService.increaseInitiative(gameCharacter, isBonus);
                break;
            case "merit":
                gameCharacterService.addMerit(gameCharacter, request.getName(), request.getValue());
                break;
            case "flaw":
                gameCharacterService.addFlaw(gameCharacter, request.getName(), request.getValue());
                break;
            case "megaattribute":
                gameCharacterService.increaseMegaAttribute(gameCharacter, request.getName(), request.getEnhancementName(), isBonus, request.isTainted());
                break;
            case "enhancement":
                gameCharacterService.addEnhancement(gameCharacter, request.getName(), request.getExtraName(), isBonus, request.isTainted());
                break;
            case "power":
                gameCharacterService.increasePower(gameCharacter, request.getName(), request.getValue(), request.getQuantumMinimum(), request.getAttributeName(), request.isHasExtra(), request.getExtraName(), isBonus, request.isTainted());
                break;
            case "abilityspecialty":
                gameCharacterService.addAbilitySpecialty(gameCharacter, request.getName(), request.getSpecialtyName(), isBonus);
                break;
            case "quantumpool":
                gameCharacterService.increaseQuantumPool(gameCharacter);
                break;
            default:
                throw new IllegalArgumentException("Invalid point type");
        }
        return gameCharacterService.saveCharacter(gameCharacter); // Assuming you have a saveCharacter method to persist changes
    }

}
