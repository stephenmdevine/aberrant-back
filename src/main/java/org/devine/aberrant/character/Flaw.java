/*package org.devine.aberrant.character;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Flaw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int value;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

}
*/