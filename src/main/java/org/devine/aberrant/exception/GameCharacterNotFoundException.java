package org.devine.aberrant.exception;

public class GameCharacterNotFoundException extends RuntimeException {

    public GameCharacterNotFoundException(Long id) {
        super("Could not find the character with id " + id);
    }

}
