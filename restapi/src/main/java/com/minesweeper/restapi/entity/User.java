package com.minesweeper.restapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString(exclude = "games")
@NoArgsConstructor
@Accessors(chain = true)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Game> games;

    private String name;

    public User(String name) {
        this.name = name;
    }
}
