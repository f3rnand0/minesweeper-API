package com.minesweeper.restapi.service;

import com.minesweeper.restapi.dto.CellDto;
import com.minesweeper.restapi.dto.GameDto;
import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.Cell;
import com.minesweeper.restapi.entity.CellState;
import com.minesweeper.restapi.entity.Game;
import com.minesweeper.restapi.entity.User;
import com.minesweeper.restapi.exception.NotFoundException;
import com.minesweeper.restapi.repository.CellRepository;
import com.minesweeper.restapi.repository.GameRepository;
import com.minesweeper.restapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
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
        int cols = gameDto.getColumns();
        Optional<User> user = userRepository.findByName(gameDto.getUser().getName());

        if (user.isPresent()) {

            Game game = new Game()
                    .setRows(rows)
                    .setColumns(cols);
            Game gameSaved = gameRepository.save(game);

            // Store game into user table
            User reqUser = user.get();
            Set<Game> gameSet = reqUser.getGames();
            if (gameSet.isEmpty())
                gameSet = new HashSet<>();
            gameSet.add(gameSaved);
            reqUser.setGames(gameSet);
            userRepository.save(user.get());

            // Generate board
            Set<Cell> cellSet = new HashSet<>(rows * cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Cell cell = new Cell()
                            .setGame(gameSaved)
                            .setRow(i)
                            .setColumn(j)
                            .setState(CellState.HIDDEN);
                    Cell cellSaved = cellRepository.save(cell);
                    cellSet.add(cellSaved);
                }
            }
            GameDto respGameDto = modelMapper.map(gameSaved, GameDto.class);
            respGameDto.setUser(modelMapper.map(reqUser, UserDto.class));
            Set<CellDto> cellDtoSet =
                    cellSet.stream().map(cell -> modelMapper.map(cell, CellDto.class))
                            .collect(Collectors.toCollection(
                                    TreeSet::new));
            respGameDto.setCells(cellDtoSet);
            return respGameDto;
        }
        throw new NotFoundException("User not found");
    }

    public GameDto updateGame(GameDto gameDto) {
        Optional<Game> game = gameRepository.findById(gameDto.getId());
        if (game.isPresent()) {
            // Generate mines, numbers/visible cells
        }
        throw new NotFoundException("Game not found");
    }
}
