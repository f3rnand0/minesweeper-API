package com.minesweeper.restapi.repository;

import com.minesweeper.restapi.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}
