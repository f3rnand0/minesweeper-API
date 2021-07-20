package com.minesweeper.restapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@ToString(exclude = "game")
@NoArgsConstructor
@Accessors(chain = true)
public class Cell {
    @Id
    @Column(name = "cell_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private Integer row;

    private Integer column;

    private Boolean visible;

    private String state;

    public Cell(Long id) {
        this.id = id;
    }

    public Cell(Integer row, Integer column, Boolean visible, String state) {
        this.row = row;
        this.column = column;
        this.visible = visible;
        this.state = state;
    }
}
