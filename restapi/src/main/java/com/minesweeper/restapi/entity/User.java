package com.minesweeper.restapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString(exclude = "games")
@NoArgsConstructor
@Accessors(chain = true)
public class User {

    public User(String name) {
        this.name = name;
    }
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Game> games;

    private String name;
}
