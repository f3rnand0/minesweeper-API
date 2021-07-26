package com.minesweeper.restapi;

import com.minesweeper.restapi.dto.CellDto;
import com.minesweeper.restapi.dto.GameDto;
import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.CellState;
import com.minesweeper.restapi.entity.GameTurn;
import com.minesweeper.restapi.service.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameService1Test {

    private static final String DEFAULT_USER = "anonymous";
    private final int NUMBER_OF_ROWS = 4;
    private final int NUMBER_OF_COLUMNS = 4;
    private final int NUMBER_OF_MINES = 2;

    @Autowired
    GameService gameService;

    @Test
    public void shouldReturnGame() {
        GameDto gameDto = new GameDto();
        gameDto.setRows(NUMBER_OF_ROWS);
        gameDto.setColumns(NUMBER_OF_COLUMNS);
        gameDto.setMines(NUMBER_OF_MINES);
        gameDto.setGameTurn(GameTurn.ZERO);
        gameDto.setUser(new UserDto(DEFAULT_USER));
        GameDto gameDtoSaved = gameService.addGame(gameDto);
        assertNotNull("Game shouldn't be null", gameDtoSaved);
        assertEquals("Game cells size different", NUMBER_OF_ROWS * NUMBER_OF_COLUMNS,
                     gameDtoSaved.getCells().size());
    }

    @Test
    public void shouldReturnGameWithMinesNumbers1() {
        GameDto gameDto = new GameDto();
        gameDto.setRows(NUMBER_OF_ROWS);
        gameDto.setColumns(NUMBER_OF_COLUMNS);
        gameDto.setMines(NUMBER_OF_MINES);
        gameDto.setGameTurn(GameTurn.ZERO);
        gameDto.setUser(new UserDto(DEFAULT_USER));
        GameDto gameDtoSaved = gameService.addGame(gameDto);
        gameDtoSaved.setGameTurn(GameTurn.FIRST);
        gameDtoSaved.setSelectedCell(new CellDto(2, 2));
        gameDtoSaved = gameService.modifyGame(gameDtoSaved);
        List<CellDto> cellDtoList = gameDtoSaved.getCells();
        long minesCount = cellDtoList.stream().filter(c -> c.getState().equals(CellState.MINE.label)).count();
        assertEquals("Game mines should be equal", (int) minesCount, (int) gameDtoSaved.getMines());
        Predicate<CellDto> filterCellNumbers =
                c -> c.getState().equals("1") || c.getState().equals("2") || c.getState().equals("3") ||
                     c.getState().equals("4") || c.getState().equals("5");
        long numbersCount = cellDtoList.stream().filter(filterCellNumbers).count();
        assertTrue("Game with cell numbered should be greater than zero", (int) numbersCount > 0);
        long hiddenCount =
                cellDtoList.stream().filter(c -> c.getVisible().equals(Boolean.FALSE)).count();
        assertTrue("Game with hidden cells should be greater than zero", (int) hiddenCount > 0);
    }

    @Test
    public void shouldReturnGameWithFlaggedCell() {
        GameDto gameDto = new GameDto();
        gameDto.setRows(NUMBER_OF_ROWS);
        gameDto.setColumns(NUMBER_OF_COLUMNS);
        gameDto.setMines(NUMBER_OF_MINES);
        gameDto.setGameTurn(GameTurn.ZERO);
        gameDto.setUser(new UserDto(DEFAULT_USER));
        GameDto gameDtoSaved = gameService.addGame(gameDto);
        gameDtoSaved.setGameTurn(GameTurn.FIRST);
        gameDtoSaved.setSelectedCell(new CellDto(2, 2));
        gameDtoSaved = gameService.modifyGame(gameDtoSaved);
        gameDtoSaved.setGameTurn(GameTurn.LATER);
        gameDtoSaved.setFlaggedCell(new CellDto(2, 1));
        gameDtoSaved = gameService.modifyGame(gameDtoSaved);
        List<CellDto> cellDtoList = gameDtoSaved.getCells();
        long flaggedCount = cellDtoList.stream().filter(c -> c.getFlagged().equals(Boolean.TRUE)).count();
        assertEquals("Game with flagged cells should be one", 1, flaggedCount);
    }

    @Test
    public void shouldReturnGameWithUnflaggedCell() {
        GameDto gameDto = new GameDto();
        gameDto.setRows(NUMBER_OF_ROWS);
        gameDto.setColumns(NUMBER_OF_COLUMNS);
        gameDto.setMines(NUMBER_OF_MINES);
        gameDto.setGameTurn(GameTurn.ZERO);
        gameDto.setUser(new UserDto(DEFAULT_USER));
        GameDto gameDtoSaved = gameService.addGame(gameDto);
        gameDtoSaved.setGameTurn(GameTurn.FIRST);
        gameDtoSaved.setSelectedCell(new CellDto(2, 2));
        gameDtoSaved = gameService.modifyGame(gameDtoSaved);
        gameDtoSaved.setGameTurn(GameTurn.LATER);
        gameDtoSaved.setFlaggedCell(new CellDto(2, 1));
        gameDtoSaved = gameService.modifyGame(gameDtoSaved);
        gameDtoSaved.setGameTurn(GameTurn.LATER);
        gameDtoSaved.setFlaggedCell(new CellDto(2, 1));
        gameDtoSaved = gameService.modifyGame(gameDtoSaved);
        List<CellDto> cellDtoList = gameDtoSaved.getCells();
        long flaggedCount = cellDtoList.stream().filter(c -> c.getFlagged().equals(Boolean.TRUE)).count();
        assertEquals("Game with unflagged cells should be zero", 0, flaggedCount);
    }

    @Test
    public void shouldNotWinOrLoseGameOnFirstTurn() {
        GameDto gameDto = new GameDto();
        gameDto.setRows(NUMBER_OF_ROWS);
        gameDto.setColumns(NUMBER_OF_COLUMNS);
        gameDto.setMines(NUMBER_OF_MINES);
        gameDto.setGameTurn(GameTurn.ZERO);
        gameDto.setUser(new UserDto(DEFAULT_USER));
        GameDto gameDtoSaved = gameService.addGame(gameDto);
        gameDtoSaved.setGameTurn(GameTurn.FIRST);
        gameDtoSaved.setSelectedCell(new CellDto(2, 2));
        gameDtoSaved = gameService.modifyGame(gameDtoSaved);
        if ("".equals(gameDtoSaved.getEndMessage())) {
            gameDtoSaved.setGameTurn(GameTurn.LATER);
            gameDtoSaved.setSelectedCell(new CellDto(2, 1));
            gameDtoSaved = gameService.modifyGame(gameDtoSaved);
        } else
            assertTrue("Game ended", !"".equals(gameDtoSaved.getEndMessage()));
    }
}