/*package org.devine.aberrant.megaAttribute;

import jakarta.persistence.*;
import lombok.Data;
import org.devine.aberrant.character.Character;

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

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

}
*/