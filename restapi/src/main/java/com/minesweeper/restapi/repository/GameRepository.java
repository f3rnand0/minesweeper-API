package com.minesweeper.restapi.repository;

import com.minesweeper.restapi.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Long> {

    @Query(value = "SELECT g FROM Game g JOIN FETCH g.cells c WHERE g.id = ?1")
    Optional<Game> findByIdIncludingCells(Long aLong);
}
