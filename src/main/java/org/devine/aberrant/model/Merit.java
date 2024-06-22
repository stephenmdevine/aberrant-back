package org.devine.aberrant.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Merit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int value;

    @ManyToOne
    @JoinColumn(name = "game_character_id")
    private GameCharacter gameCharacter;

}
