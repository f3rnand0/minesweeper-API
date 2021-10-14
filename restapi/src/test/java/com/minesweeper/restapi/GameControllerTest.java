package com.minesweeper.restapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minesweeper.restapi.dto.GameDto;
import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.entity.GameTurn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

    private GameDto gameDto;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        gameDto = new GameDto()
                .setRows(4)
                .setColumns(4)
                .setMines(2)
                .setGameTurn(GameTurn.ZERO)
                .setUser(new UserDto("anonymous"));
    }

    @Test
    public void givenAddGameRequest_WhenProperGameDtoRequest_thenGameDtoIsReturned() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mockMvc.perform(post("/minesweeper-api/game/add")
                                .content(mapper.writeValueAsBytes(gameDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                                   .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user.name", is("anonymous")));
    }
}
