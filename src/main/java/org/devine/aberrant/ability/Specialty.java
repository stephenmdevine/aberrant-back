package org.devine.aberrant.ability;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "ability_id")
    private Ability ability;

}
