package org.devine.aberrant.character;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "character")
@EqualsAndHashCode(exclude = "character")
public class CharacterAdditionalInfo {

    @Id
    @Column(name = "character")
    private int id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "character", referencedColumnName = "id")
    private Character character;

    @Size(max = 32)
    @Column(name = "concept")
    private String concept;

    @Size(max = 32)
    @Column(name = "nature")
    private String nature;

    @Size(max = 64)
    @Column(name = "allegiance")
    private String allegiance;

}
