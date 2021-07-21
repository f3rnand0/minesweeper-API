package com.minesweeper.restapi.service;

import com.minesweeper.restapi.dto.BoardDto;
import com.minesweeper.restapi.dto.CellDto;
import com.minesweeper.restapi.dto.GameDto;
import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.*;
import com.minesweeper.restapi.exception.NotFoundException;
import com.minesweeper.restapi.repository.CellRepository;
import com.minesweeper.restapi.repository.GameRepository;
import com.minesweeper.restapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;
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
                    .setGameTurn(GameTurn.ZERO)
                    .setSelectedCell(new Cell(1L));
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
        throw new NotFoundException("User not found");
    }

    public GameDto modifyGame(GameDto gameDto) {
        Optional<Game> game = gameRepository.findById(gameDto.getId());
        if (game.isPresent()) {
            BoardDto boardDto = null;
            List<Cell> cellList;
            Game queriedGame = game.get();
            int rows = queriedGame.getRows();
            int columns = queriedGame.getColumns();
            int mines = queriedGame.getMines();
            // Generate mines, numbers on first turn
            if (GameTurn.FIRST.equals(gameDto.getGameTurn())) {
                queriedGame.setGameTurn(gameDto.getGameTurn());
                queriedGame.setDateStarted(new Timestamp(Instant.now().toEpochMilli()));

                boardDto = DataStructureTransformer
                        .transformFromListDtoIntoArrays(queriedGame.getCells(), rows, columns);
                String[][] cells = boardDto.getCells();
                cells = MineSweeperAlgorithm.mineGenerator(cells,
                                                           rows,
                                                           columns, queriedGame.getMines(), gameDto.getSelectedCell().getRow(),
                                                           gameDto.getSelectedCell().getColumn());
                cells = MineSweeperAlgorithm.numberGenerator(cells,
                                                             rows,
                                                             columns);
                boardDto.setCells(cells);
                boardDto.setVisibleCount(0);
                boardDto.setEndMessage("");
            } else if (GameTurn.LATER.equals(gameDto.getGameTurn())) {
                cellList = cellRepository.findCellsByGameId(queriedGame.getId());
                queriedGame.setGameTurn(gameDto.getGameTurn());
                boardDto = DataStructureTransformer
                        .transformFromListDtoIntoArrays(cellList, rows, columns);
                boardDto.setVisibleCount(queriedGame.getVisibleCount());
                boardDto.setEndMessage("");
            }

            // Only do actions related to a selected cell when a user dont flag cell
            if (gameDto.getFlaggedCell() == null) {
                // Check surrounding cells of selected cell and do the corresponding action
                boardDto = MineSweeperAlgorithm.checkSelectedCell(boardDto, gameDto.getSelectedCell().getRow(),
                                                                  gameDto.getSelectedCell().getColumn(),
                                                                  mines);
                cellList = DataStructureTransformer.transformFromArraysIntoListDto(boardDto, rows, columns);
                queriedGame.setCells(cellList);
                queriedGame.setVisibleCount(boardDto.getVisibleCount());
                queriedGame.setEndMessage(boardDto.getEndMessage());
                // Check if game ended
                if (!"".equals(queriedGame.getEndMessage())) {
                    queriedGame.setDateFinished(new Timestamp(Instant.now().toEpochMilli()));
                    long elapsedTime = Duration.between(queriedGame.getDateStarted().toInstant(),
                                                        queriedGame.getDateFinished().toInstant()).toMillis();
                    queriedGame.setElapsedTime(new Time(elapsedTime));
                }
            }
            // Only change the corresponding cell to flagged state and nothing else
            else {
                int x = gameDto.getFlaggedCell().getRow();
                int y = gameDto.getFlaggedCell().getColumn();
                Boolean[][] visibleCells = boardDto.getVisibleCells();
                Boolean[][] flaggedCells = boardDto.getFlaggedCells();
                // Only flag a hidden cell
                if (!visibleCells[x][y]) {
                    if (flaggedCells[x][y])
                        flaggedCells[x][y] = Boolean.FALSE;
                    else
                        flaggedCells[x][y] = Boolean.TRUE;
                    boardDto.setFlaggedCells(flaggedCells);
                }
            }
            //queriedGame.setCells(cellList);

            // Store cells with modifications
            Game gameSaved = gameRepository.save(queriedGame);
            cellList = modifyBoard(boardDto, rows, columns, gameSaved);
            GameDto mappedGameDto = modelMapper.map(gameSaved, GameDto.class);
            // TODO En el gameSaved ya esta el usuario asociado
            User userGame = gameRepository.findUserByGameId(gameSaved.getId());
            mappedGameDto.setUser(modelMapper.map(userGame, UserDto.class));
            List<CellDto> cellDtoList =
                    cellList.stream().map(cell -> modelMapper.map(cell, CellDto.class))
                            .collect(Collectors.toList());
            mappedGameDto.setCells(cellDtoList);
            return mappedGameDto;
        }
        throw new NotFoundException("Game not found");
    }

    private List<Cell> generateBoard(int rows, int columns, Game gameSaved) {
        List<Cell> cellList = new ArrayList<>(rows * columns);
        long count = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = new Cell()
                        .setId(count)
                        .setGame(gameSaved)
                        .setRow(i)
                        .setColumn(j)
                        .setVisible(Boolean.FALSE)
                        .setFlagged(Boolean.FALSE)
                        .setState(CellState.EMPTY.label);
                Cell cellSaved = cellRepository.save(cell);
                cellList.add(cellSaved);
                count++;
            }
        }
        return cellList;
    }

    private List<Cell> modifyBoard(BoardDto boardDto, int rows, int columns, Game gameSaved) {
        Boolean[][] visibleCells = boardDto.getVisibleCells();
        Boolean[][] flaggedCells = boardDto.getFlaggedCells();
        String[][] cells = boardDto.getCells();
        List<Cell> cellList = new ArrayList<>(rows * columns);
        long count = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = new Cell()
                        .setId(count)
                        .setGame(gameSaved)
                        .setRow(i)
                        .setColumn(j)
                        .setVisible(visibleCells[i][j])
                        .setFlagged(flaggedCells[i][j])
                        .setState(cells[i][j]);
                Cell cellSaved = cellRepository.save(cell);
                cellList.add(cellSaved);
                count++;
            }
        }
        return cellList;
    }
}
