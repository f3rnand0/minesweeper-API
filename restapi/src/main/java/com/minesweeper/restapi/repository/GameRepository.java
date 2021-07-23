package com.minesweeper.restapi.repository;

import com.minesweeper.restapi.entity.Game;
import com.minesweeper.restapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "SELECT g.user FROM Game g WHERE g.id = ?1")
    User findUserByGameId(Long id);

}
