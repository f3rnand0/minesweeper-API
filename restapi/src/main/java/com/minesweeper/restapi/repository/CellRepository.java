package com.minesweeper.restapi.repository;

import com.minesweeper.restapi.entity.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CellRepository extends JpaRepository<Cell, Long> {

    @Query(value = "SELECT c FROM Cell c WHERE c.game.id = ?1 ORDER by c.id")
    List<Cell> findCellsByGameId(Long Id);
}
