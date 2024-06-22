package org.devine.aberrant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int value;
    private int novaPurchased;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private GameCharacter gameCharacter;

    @OneToMany(mappedBy = "ability", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Specialty> specialties;

}
