package org.devine.aberrant.controller;

import org.devine.aberrant.model.GameCharacter;
import org.devine.aberrant.repository.GameCharacterRepository;
import org.devine.aberrant.service.GameCharacterCreationRequest;
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

    @PostMapping("/new")
    GameCharacter newCharacter(@RequestBody GameCharacterCreationRequest gameCharacter) {
        return gameCharacterService.createCharacter(gameCharacter);
    }

    @GetMapping
    List<GameCharacter> getAllCharacters() {
        return gameCharacterService.findAll();
    }

}
