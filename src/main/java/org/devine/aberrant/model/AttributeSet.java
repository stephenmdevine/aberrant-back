package org.devine.aberrant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class AttributeSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "attributeSet")
    private List<Attribute> attributes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_character_id")
    private GameCharacter gameCharacter;

}
