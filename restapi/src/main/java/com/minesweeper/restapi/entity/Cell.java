package com.minesweeper.restapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Cell {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private Integer row;

    private Integer column;

    private String state;

    public Cell(Integer row, Integer column, String state) {
        this.row = row;
        this.column = column;
        this.state = state;
    }
}
