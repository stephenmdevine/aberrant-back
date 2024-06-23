package org.devine.aberrant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Data
public class Power {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int value;
    private int level;
    private int quantumMinimum;
    private Boolean hasExtra;
    private String extraName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_character_id")
    private GameCharacter gameCharacter;
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

}
