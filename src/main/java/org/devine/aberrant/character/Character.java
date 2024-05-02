package org.devine.aberrant.character;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Character {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    @Size(max = 64)
    @Column(name = "player")
    private String player;

    @NotBlank
    @Size(max = 64)
    @Column(name = "character_name")
    private String characterName;

    @NotBlank
    @Size(max = 64)
    @Column(name = "nova_name")
    private String novaName;

    @Column(name = "creation_pts")
    private int creationPts;

    @Column(name = "experience")
    private int experience;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "character", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CharacterAdditionalInfo info;

}
