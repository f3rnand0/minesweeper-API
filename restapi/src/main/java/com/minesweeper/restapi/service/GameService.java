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

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
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

    /**
     * Add a game in the database based on the game parameters indicated
     * @param gameDto Dto with game parameters
     * @return Dto with game parameters, game id and list of cells
     */
    public GameDto addGame(GameDto gameDto) {
        int rows = gameDto.getRows();
        int columns = gameDto.getColumns();
        Optional<User> optionalUser = userRepository.findByName(gameDto.getUser().getName());
        // Retrieve the user if it's in the database, otherwise saved it
        User user = optionalUser.orElseGet(() -> addUser(gameDto.getUser().getName()));

        Game game = new Game()
                .setRows(rows)
                .setColumns(columns)
                .setMines(gameDto.getMines())
                .setGameTurn(GameTurn.ZERO);
        Game gameSaved = gameRepository.save(game);

        // Generate a list cells with empty state
        List<Cell> cellList = generateBoard(rows, columns, gameSaved);

        // Set user associated to game
        gameSaved.setUser(user);
        gameRepository.save(gameSaved);

        // Store game and corresponding cells
        GameDto respGameDto = modelMapper.map(gameSaved, GameDto.class);
        respGameDto.setUser(modelMapper.map(user, UserDto.class));
        List<CellDto> cellDtoList =
                cellList.stream().map(cell -> modelMapper.map(cell, CellDto.class))
                        .collect(Collectors.toList());
        respGameDto.setCells(cellDtoList);
        return respGameDto;
    }

    private User addUser(String name) {
        User user = new User(name);
        return userRepository.save(user);
    }

    /**
     * Starts a game based on the first selected cell
     * @param gameDto Dto with game parameters
     * @return Dto with game parameters, game id and list of cells (including states)
     */
    public GameDto startGame(GameDto gameDto) {
        Optional<Game> optionalGame = gameRepository.findById(gameDto.getId());
        Game game = optionalGame.orElseThrow(() -> new NotFoundException("Game not found"));
        game.setGameTurn(gameDto.getGameTurn());
        game.setDateStarted(new Timestamp(Instant.now().toEpochMilli()));

        BoardDto boardDto;
        List<Cell> cellList;
        int rows = game.getRows();
        int columns = game.getColumns();
        int mines = game.getMines();

        // Obtain a dto containing state of every cell, flagged, and visible cells
        boardDto = DataStructureTransformer
                .transformFromListDtoIntoArrays(game.getCells(), rows, columns);
        String[][] cells = boardDto.getCells();
        Integer selectedX = gameDto.getSelectedCell().getRow();
        Integer selectedY = gameDto.getSelectedCell().getColumn();
        // Generate mines on random places
        cells = MineSweeperAlgorithm.mineGenerator(cells,
                                                   rows,
                                                   columns, mines,
                                                   selectedX,
                                                   selectedY);
        // Generate numbers surrounding mines
        cells = MineSweeperAlgorithm.numberGenerator(cells,
                                                     rows,
                                                     columns);

        boardDto.setCells(cells);

        // Make visible the first selected cell and count it
        Boolean[][] visibleCells = boardDto.getVisibleCells();
        visibleCells[selectedX][selectedY] = Boolean.TRUE;
        boardDto.setVisibleCount(1);
        boardDto.setEndMessage("");
        /*boardDto =
                MineSweeperAlgorithm.checkSelectedCell(boardDto, selectedX,
                                                       selectedY,
                                                       mines);*/
        cellList = DataStructureTransformer.transformFromArraysIntoListDto(boardDto, rows, columns);
        game.setCells(cellList);
        game.setVisibleCount(boardDto.getVisibleCount());
        // Set the end message in case the game finished
        game.setEndMessage(boardDto.getEndMessage());

        // Store cells with modifications
        Game gameSaved = gameRepository.save(game);
        //Boolean gameFinished = !"".equals(gameSaved.getEndMessage());
        //cellList = modifyBoard(boardDto, rows, columns, gameSaved, gameFinished);
        GameDto mappedGameDto = modelMapper.map(gameSaved, GameDto.class);
        mappedGameDto.setUser(modelMapper.map(gameSaved.getUser(), UserDto.class));
        List<CellDto> cellDtoList =
                cellList.stream().map(cell -> modelMapper.map(cell, CellDto.class))
                        .collect(Collectors.toList());
        mappedGameDto.setCells(cellDtoList);
        return mappedGameDto;

    }

    /**
     * Continues the game based on the selected cell
     * @param gameDto Dto with game parameters
     * @return Dto with game parameters, game id and list of cells (including states)
     */
    public GameDto continueGame(GameDto gameDto) {
        Optional<Game> optionalGame = gameRepository.findById(gameDto.getId());
        Game game = optionalGame.orElseThrow(() -> new NotFoundException("Game not found"));
        game.setGameTurn(gameDto.getGameTurn());

        BoardDto boardDto;
        List<Cell> cellList;
        int rows = game.getRows();
        int columns = game.getColumns();
        int mines = game.getMines();

        // Obtain state of the board at the moment
        cellList = cellRepository.findCellsByGameId(game.getId());

        // Obtain a dto containing state of every cell, flagged, and visible cells
        boardDto = DataStructureTransformer
                .transformFromListDtoIntoArrays(cellList, rows, columns);
        boardDto.setVisibleCount(game.getVisibleCount());
        boardDto.setEndMessage("");

        // Check surrounding cells of selected cell and do the corresponding action
        Integer selectedX = gameDto.getSelectedCell().getRow();
        Integer selectedY = gameDto.getSelectedCell().getColumn();
        boardDto =
                MineSweeperAlgorithm.checkSelectedCell(boardDto, selectedX,
                                                       selectedY,
                                                       mines);

        cellList = DataStructureTransformer.transformFromArraysIntoListDto(boardDto, rows, columns);
        game.setCells(cellList);
        // Set number of visible cells
        game.setVisibleCount(boardDto.getVisibleCount());
        // Set end message in case the game finished
        game.setEndMessage(boardDto.getEndMessage());

        // If game the ended
        if (!"".equals(game.getEndMessage())) {
            // Set finished date of the game and elapsed time
            game.setDateFinished(new Timestamp(Instant.now().toEpochMilli()));
            Long elapsedTime = Duration.between(game.getDateStarted().toInstant(),
                                                game.getDateFinished().toInstant()).toMillis();
            String duration = String.format("%d min, %d sec",
                                            TimeUnit.MILLISECONDS.toMinutes(elapsedTime),
                                            TimeUnit.MILLISECONDS.toSeconds(elapsedTime) -
                                            TimeUnit.MINUTES.toSeconds(
                                                    TimeUnit.MILLISECONDS.toMinutes(elapsedTime)));
            game.setElapsedTime(duration);
        }
        // Store cells with modifications
        Game gameSaved = gameRepository.save(game);
        boolean gameFinished = !"".equals(gameSaved.getEndMessage());
        cellList = modifyBoard(boardDto, rows, columns, gameSaved, gameFinished);
        GameDto mappedGameDto = modelMapper.map(gameSaved, GameDto.class);
        mappedGameDto.setUser(modelMapper.map(gameSaved.getUser(), UserDto.class));
        List<CellDto> cellDtoList =
                cellList.stream().map(cell -> modelMapper.map(cell, CellDto.class))
                        .collect(Collectors.toList());
        mappedGameDto.setCells(cellDtoList);
        return mappedGameDto;
    }

    /**
     * Flags a selected cell of the game
     * @param gameDto Dto with game parameters
     * @return Dto with game parameters, game id and list of cells (including states)
     */
    public GameDto flagCellGame(GameDto gameDto) {
        Optional<Game> optionalGame = gameRepository.findById(gameDto.getId());
        Game game = optionalGame.orElseThrow(() -> new NotFoundException("Game not found"));

        BoardDto boardDto = null;
        List<Cell> cellList;
        int rows = game.getRows();
        int columns = game.getColumns();

        // Obtain state of the board at the moment
        cellList = cellRepository.findCellsByGameId(game.getId());

        // Obtain a dto containing state of every cell, flagged, and visible cells
        boardDto = DataStructureTransformer
                .transformFromListDtoIntoArrays(cellList, rows, columns);
        Integer selectedX = gameDto.getFlaggedCell().getRow();
        Integer selectedY = gameDto.getFlaggedCell().getColumn();
        Boolean[][] visibleCells = boardDto.getVisibleCells();
        Boolean[][] flaggedCells = boardDto.getFlaggedCells();

        // Flag selected cell
        boardDto.setFlaggedCells(MineSweeperAlgorithm
                                         .flagSelectedCell(visibleCells[selectedX][selectedY], flaggedCells,
                                                           selectedX, selectedY));

        // Store cells with modifications
        Game gameSaved = gameRepository.save(game);
        boolean gameFinished = !"".equals(gameSaved.getEndMessage());
        cellList = modifyBoard(boardDto, rows, columns, gameSaved, gameFinished);
        GameDto mappedGameDto = modelMapper.map(gameSaved, GameDto.class);
        mappedGameDto.setUser(modelMapper.map(gameSaved.getUser(), UserDto.class));
        List<CellDto> cellDtoList =
                cellList.stream().map(cell -> modelMapper.map(cell, CellDto.class))
                        .collect(Collectors.toList());
        mappedGameDto.setCells(cellDtoList);
        return mappedGameDto;
    }


    /**
     * Generates a list of cells on the first turn of the game
     * @param rows      Number of rows
     * @param columns   Number of columns
     * @param gameSaved Entity with the game id
     * @return List of cells saved in the database
     */
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

    /**
     * Modifies the list of cells in the database
     * @param boardDto  Dto containing arrays of visible, flagged and cell states
     * @param rows      Number of rows
     * @param columns   Number of columns
     * @param gameSaved Entity with the game id
     * @return List of cells saved in the database
     */
    private List<Cell> modifyBoard(BoardDto boardDto, int rows, int columns, Game gameSaved,
                                   boolean gameFinished) {
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
                        .setFlagged(flaggedCells[i][j])
                        .setState(cells[i][j]);
                // If game not finished show only corresponding cells
                if (!gameFinished)
                    cell.setVisible(visibleCells[i][j]);
                    // If game finished show all cells
                else
                    cell.setVisible(Boolean.TRUE);
                Cell cellSaved = cellRepository.save(cell);
                cellList.add(cellSaved);
                count++;
            }
        }
        return cellList;
    }
}
