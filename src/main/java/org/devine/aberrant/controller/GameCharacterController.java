package org.devine.aberrant.controller;

import org.devine.aberrant.model.GameCharacter;
import org.devine.aberrant.repository.GameCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameCharacterController {

    @Autowired
    private GameCharacterRepository gameCharacterRepository;

    @PostMapping("/")
    GameCharacter newCharacter(@RequestBody GameCharacter gameCharacter) {
        return gameCharacterRepository.save(gameCharacter);
    }

}
