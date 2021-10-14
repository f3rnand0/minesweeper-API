package com.minesweeper.restapi;

import com.minesweeper.restapi.dto.CellDto;
import com.minesweeper.restapi.dto.GameDto;
import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.CellState;
import com.minesweeper.restapi.entity.GameTurn;
import com.minesweeper.restapi.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.function.Predicate;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;


//@RunWith(SpringRunner.class)
@SpringBootTest
public class GameService2Test {

    private static final String DEFAULT_USER = "anonymous";
    private final int NUMBER_OF_ROWS = 4;
    private final int NUMBER_OF_COLUMNS = 4;
    @Autowired
    GameService gameService;
    private GameDto gameDtoSaved;

    @Test
    public void shouldReturnGameWithMinesNumbers1() {
        GameDto gameDto = new GameDto();
        gameDto.setRows(NUMBER_OF_ROWS);
        gameDto.setColumns(NUMBER_OF_COLUMNS);
        gameDto.setMines(5);
        gameDto.setGameTurn(GameTurn.ZERO);
        gameDto.setUser(new UserDto(DEFAULT_USER));
        gameDtoSaved = gameService.addGame(gameDto);
        gameDtoSaved.setGameTurn(GameTurn.FIRST);
        gameDtoSaved.setSelectedCell(new CellDto(2, 2));
        gameDtoSaved = gameService.startGame(gameDtoSaved);
        List<CellDto> cellDtoList = gameDtoSaved.getCells();
        long minesCount = cellDtoList.stream().filter(c -> c.getState().equals(CellState.MINE.label)).count();
        assertEquals("Game mines should be equal", (int) gameDtoSaved.getMines(), (int) minesCount);
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
    public void shouldReturnGameWithMinesNumbers2() {
        GameDto gameDto = new GameDto();
        gameDto.setRows(NUMBER_OF_ROWS);
        gameDto.setColumns(NUMBER_OF_COLUMNS);
        gameDto.setMines((NUMBER_OF_ROWS * NUMBER_OF_COLUMNS) - 2);
        gameDto.setGameTurn(GameTurn.ZERO);
        gameDto.setUser(new UserDto(DEFAULT_USER));
        gameDtoSaved = gameService.addGame(gameDto);
        gameDtoSaved.setGameTurn(GameTurn.FIRST);
        gameDtoSaved.setSelectedCell(new CellDto(2, 2));
        gameDtoSaved = gameService.startGame(gameDtoSaved);
        List<CellDto> cellDtoList = gameDtoSaved.getCells();
        long minesCount = cellDtoList.stream().filter(c -> c.getState().equals(CellState.MINE.label)).count();
        assertEquals("Game mines should be equal", (int) gameDtoSaved.getMines(), (int) minesCount);
        Predicate<CellDto> filterCellNumbers =
                c -> c.getState().equals("1") || c.getState().equals("2") || c.getState().equals("3") ||
                     c.getState().equals("4") || c.getState().equals("5") || c.getState().equals("6") ||
                     c.getState().equals("7") || c.getState().equals("8");
        long numbersCount = cellDtoList.stream().filter(filterCellNumbers).count();
        assertTrue("Game with cell numbered should be greater than zero", (int) numbersCount > 0);
        long hiddenCount =
                cellDtoList.stream().filter(c -> c.getVisible().equals(Boolean.FALSE)).count();
        assertTrue("Game with hidden cells should be greater than zero", (int) hiddenCount > 0);
    }
}
