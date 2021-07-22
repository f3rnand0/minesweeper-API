package com.minesweeper.restapi.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@ToString(exclude = {"cells", "selectedCell"})
@Accessors(chain = true)
public class Game {
    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "game")
    private List<Cell> cells;

    private Integer rows;

    private Integer columns;

    private Integer mines;

    private GameTurn gameTurn;

    @Column(name = "date_started")
    private Timestamp dateStarted;

    @Column(name = "date_finished")
    private Timestamp dateFinished;

    @Column(name = "elapsed_time")
    private Time elapsedTime;

    private String endMessage;

    private Integer visibleCount;
}
