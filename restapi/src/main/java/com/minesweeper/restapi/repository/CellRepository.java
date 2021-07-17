package com.minesweeper.restapi.repository;

import com.minesweeper.restapi.entity.Cell;
import com.minesweeper.restapi.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CellRepository extends JpaRepository<Cell, Long> {

}
