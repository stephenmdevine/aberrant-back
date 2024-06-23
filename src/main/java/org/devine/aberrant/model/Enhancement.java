package org.devine.aberrant.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Enhancement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "mega_attribute_id")
    private MegaAttribute megaAttribute;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_character_id")
    private GameCharacter gameCharacter;

}
