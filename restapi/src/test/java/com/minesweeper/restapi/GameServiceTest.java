package com.minesweeper.restapi;

import com.minesweeper.restapi.dto.GameDto;
import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.Game;
import com.minesweeper.restapi.entity.User;
import com.minesweeper.restapi.repository.GameRepository;
import com.minesweeper.restapi.repository.UserRepository;
import com.minesweeper.restapi.service.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {

	@Autowired
	GameRepository gameRepository;

	@Autowired
	GameService gameService;

	@Test
	public void shouldReturnGame() {
		GameDto gameDto = new GameDto();
		gameDto.setRows(3);
		gameDto.setColumns(3);
		gameDto.setUser(new UserDto("anonymous"));
		GameDto gameSaved = gameService.addGame(gameDto);
		assertNotNull("Game shouldn't be null", gameSaved);
		assertEquals("Game cells size different", 3*3, gameSaved.getCells().size());
	}

}
