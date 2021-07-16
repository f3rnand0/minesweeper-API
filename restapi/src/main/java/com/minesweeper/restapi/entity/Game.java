package com.minesweeper.restapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Game {
    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "game")
    private Set<User> users;

    @Column(name = "rows_number")
    private Integer rowsNumber;

    @Column(name = "cols_number")
    private Integer colsNumber;

    @Column(name = "mines_number")
    private String minesNumber;

    @Column(name = "date_started")
    private Timestamp dateStarted;

    @Column(name = "date_finished")
    private Timestamp dateFinished;

    @Column(name = "elapsed_time")
    private Time elapsedTime;
}
