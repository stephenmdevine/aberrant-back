package org.devine.aberrant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int value;
    private int novaPurchased;

    @ManyToOne
    @JoinColumn(name = "attribute_set_id")
    private AttributeSet attributeSet;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_character_id")
    private GameCharacter gameCharacter;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Quality> qualities;
    @OneToMany(mappedBy = "attribute")
    private List<Power> powers;

}
