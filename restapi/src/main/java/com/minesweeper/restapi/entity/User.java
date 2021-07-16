package com.minesweeper.restapi.entity;

import lombok.Data;

import javax.persistence.*;

@Data
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private String name;
}
