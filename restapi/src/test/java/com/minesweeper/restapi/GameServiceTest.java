package com.minesweeper.restapi;

import com.minesweeper.restapi.dto.CellDto;
import com.minesweeper.restapi.dto.GameDto;
import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.*;
import com.minesweeper.restapi.repository.GameRepository;
import com.minesweeper.restapi.service.GameService;
import org.junit.Before;
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
public class GameServiceTest {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameService gameService;

    private static final String DEFAULT_USER = "anonymous";

    private GameDto gameDtoSaved;

    @Before
    public void setup() {
        GameDto gameDto = new GameDto();
        gameDto.setRows(4);
        gameDto.setColumns(4);
        gameDto.setMines(2);
        gameDto.setGameTurn(GameTurn.ZERO);
        gameDto.setUser(new UserDto(DEFAULT_USER));
        gameDtoSaved = gameService.addGame(gameDto);
    }

    @Test
    public void shouldReturnGame() {
        GameDto gameDto = new GameDto();
        gameDto.setRows(4);
        gameDto.setColumns(4);
        gameDto.setUser(new UserDto(DEFAULT_USER));
        GameDto gameSaved = gameService.addGame(gameDto);
        assertNotNull("Game shouldn't be null", gameSaved);
        assertEquals("Game cells size different", 3 * 3, gameSaved.getCells().size());
    }

    @Test
    public void shouldReturnGameWithMinesNumbers() {
        GameDto gameDto = new GameDto();
        gameDto.setRows(4);
        gameDto.setColumns(4);
        gameDto.setMines(2);
        gameDto.setGameTurn(GameTurn.ZERO);
        gameDto.setUser(new UserDto(DEFAULT_USER));
        GameDto gameDtoSaved = gameService.addGame(gameDto);
        gameDtoSaved.setGameTurn(GameTurn.FIRST);
        gameDtoSaved.setSelectedCell(new CellDto(2,2));
        gameDtoSaved = gameService.modifyGame(gameDtoSaved);
        List<CellDto> cellDtoList = gameDtoSaved.getCells();
        long minesCount = cellDtoList.stream().filter(c -> c.getState().equals(CellState.MINE.label)).count();
        assertEquals("Game mines should be equal", (int) gameDto.getMines(), (int) minesCount);
        Predicate<CellDto> filterCellNumbers =
                c -> c.getState().equals("1") || c.getState().equals("2") || c.getState().equals("3") ||
                     c.getState().equals("4") || c.getState().equals("5");
        long numbersCount = cellDtoList.stream().filter(filterCellNumbers).count();
        assertTrue("Game with cell numbered should be greater than zero", (int) numbersCount > 0);
        long hiddenCount =
                cellDtoList.stream().filter(c -> c.getState().equals(CellState.EMPTY.label)).count();
        assertTrue("Game with hidden cells should be greater than zero", (int) hiddenCount > 0);
    }

    @Test
    public void shouldWinorLoseGame() {
        gameDtoSaved.setGameTurn(GameTurn.FIRST);
        gameDtoSaved.setSelectedCell(new CellDto(2,2));
        gameDtoSaved = gameService.modifyGame(gameDtoSaved);
        if ("".equals(gameDtoSaved.getEndMessage())) {
            gameDtoSaved.setGameTurn(GameTurn.LATER);
            gameDtoSaved.setSelectedCell(new CellDto(2,1));
            gameDtoSaved = gameService.modifyGame(gameDtoSaved);
        }
        else
            assertTrue("Game ended", !"".equals(gameDtoSaved.getEndMessage()));
    }
}
