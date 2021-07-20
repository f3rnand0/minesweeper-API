package com.minesweeper.restapi.service;

import com.minesweeper.restapi.dto.CellDto;
import com.minesweeper.restapi.dto.GameDto;
import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.*;
import com.minesweeper.restapi.exception.GameNotFoundException;
import com.minesweeper.restapi.exception.UserNotFoundException;
import com.minesweeper.restapi.repository.CellRepository;
import com.minesweeper.restapi.repository.GameRepository;
import com.minesweeper.restapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CellRepository cellRepository;

    @Autowired
    private ModelMapper modelMapper;

    public GameDto addGame(GameDto gameDto) {
        int rows = gameDto.getRows();
        int columns = gameDto.getColumns();
        Optional<User> user = userRepository.findByName(gameDto.getUser().getName());

        // Store a game only if a user associated exist
        if (user.isPresent()) {
            Game game = new Game()
                    .setRows(rows)
                    .setColumns(columns)
                    .setMines(gameDto.getMines())
                    .setGameTurn(GameTurn.ZERO);
            Game gameSaved = gameRepository.save(game);
            List<Cell> cellList = generateBoard(rows, columns, gameSaved);

            // Store game into user table
            gameSaved.setUser(user.get());
            gameRepository.save(gameSaved);

            // Store game and corresponding cells
            GameDto respGameDto = modelMapper.map(gameSaved, GameDto.class);
            respGameDto.setUser(modelMapper.map(user.get(), UserDto.class));
            List<CellDto> cellDtoList =
                    cellList.stream().map(cell -> modelMapper.map(cell, CellDto.class))
                            .collect(Collectors.toList());
            respGameDto.setCells(cellDtoList);
            return respGameDto;
        }
        throw new UserNotFoundException("User not found");
    }

    public GameDto modifyGame(GameDto gameDto) {
        Optional<Game> game = gameRepository.findById(gameDto.getId());
        if (game.isPresent()) {
            List<Cell> cellList = new ArrayList<>();
            Game reqGame = game.get();
            // Generate mines, numbers and visible cells on first turn
            if (GameTurn.FIRST.equals(gameDto.getGameTurn())) {
                reqGame.setDateStarted(new Timestamp(Instant.now().toEpochMilli()));
                int rows = reqGame.getRows();
                int columns = reqGame.getColumns();
                Character[][] cells = transformFromDtosIntoPrimitives(reqGame.getCells(), rows, columns);
                MineSweeperAlgorithm.mineGenerator(cells,
                                                           rows,
                                                           columns, reqGame.getMines());
                MineSweeperAlgorithm.numberGenerator(cells,
                                                           rows,
                                                           columns);
                cellList = transformFromPrimitivesIntoDtos(cells, rows, columns);
                reqGame.setCells(cellList);
            }

            // Store cells with modifications
            Game gameSaved = gameRepository.save(reqGame);
            GameDto respGameDto = modelMapper.map(gameSaved, GameDto.class);
            User userGame = gameRepository.findUserByGameId(gameSaved.getId());
            respGameDto.setUser(modelMapper.map(userGame, UserDto.class));
            List<CellDto> cellDtoList =
                    cellList.stream().map(cell -> modelMapper.map(cell, CellDto.class))
                            .collect(Collectors.toList());
            respGameDto.setCells(cellDtoList);
            return respGameDto;
        }
        throw new GameNotFoundException("Game not found");
    }

    private List<Cell> generateBoard(int rows, int columns, Game gameSaved) {
        List<Cell> cellList = new ArrayList<>(rows * columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = new Cell()
                        .setGame(gameSaved)
                        .setRow(i)
                        .setColumn(j)
                        .setState(String.valueOf(CellState.HIDDEN));
                Cell cellSaved = cellRepository.save(cell);
                cellList.add(cellSaved);
            }
        }
        return cellList;
    }

    /*private List<Cell> updateBoard(int rows, int columns, List<Cell> cellList, Game gameSaved) {
        List<Cell> cellList = new ArrayList<>(rows * columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = new Cell()
                        .setGame(gameSaved)
                        .setRow(i)
                        .setColumn(j)
                        .setState(String.valueOf(CellState.HIDDEN));
                Cell cellSaved = cellRepository.save(cell);
                cellList.add(cellSaved);
            }
        }
        return cellList;
    }*/

    private List<Cell> transformFromPrimitivesIntoDtos(Character[][] cells, int rows, int columns) {
        List<Cell> cellList = new ArrayList<>(rows * columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cellList.add(new Cell(i, j, cells[i][j].toString()));
            }
        }
        return cellList;
    }

    private Character[][] transformFromDtosIntoPrimitives(List<Cell> cellList, int rows, int columns) {
        Character[][] cells = new Character[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = cellList.get(i + j).getState().charAt(0);
            }
        }
        return cells;
    }
}
