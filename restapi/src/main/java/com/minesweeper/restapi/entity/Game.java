package com.minesweeper.restapi.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "game")
public class Game {
    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "game")
    private Set<Cell> cells;

    private Integer rows;

    private Integer columns;

    private String mines;

    @Column(name = "date_started")
    private Timestamp dateStarted;

    @Column(name = "date_finished")
    private Timestamp dateFinished;

    @Column(name = "elapsed_time")
    private Time elapsedTime;
}
