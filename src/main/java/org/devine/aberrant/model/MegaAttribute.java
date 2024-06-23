package org.devine.aberrant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class MegaAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//  name should take the form of "MegaAttribute" with no spaces
    private String name;
    private int value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_character_id")
    private GameCharacter gameCharacter;

    @OneToMany(mappedBy = "megaAttribute", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Enhancement> enhancements;

}
