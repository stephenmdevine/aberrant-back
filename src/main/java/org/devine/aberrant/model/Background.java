package org.devine.aberrant.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Background {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int value;
    private int novaPurchased;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_character_id")
    private GameCharacter gameCharacter;

}
