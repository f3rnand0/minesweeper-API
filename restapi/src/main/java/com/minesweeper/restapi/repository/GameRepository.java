package com.minesweeper.restapi.repository;

import com.minesweeper.restapi.entity.Game;
import com.minesweeper.restapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

}
