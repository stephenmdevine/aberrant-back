package org.devine.aberrant.character;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
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



}
