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
@Table(name = "user")
public class User {

    public User(String name) {
        this.name = name;
    }
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Game> games;

    private String name;
}
