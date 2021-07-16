package com.minesweeper.restapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "board")
    private Set<Game> games;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private Integer row;

    private Integer column;

    private CellState state;
}
