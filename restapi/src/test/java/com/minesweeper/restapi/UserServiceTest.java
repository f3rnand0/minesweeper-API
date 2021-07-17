package com.minesweeper.restapi;

import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.repository.UserRepository;
import com.minesweeper.restapi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Test
	public void shouldReturnUser() {
		String expectedName = "Sample 1";
		userService.addUser(new UserDto(expectedName));
		UserDto queriedUser = userService.getUser(expectedName);
		assertNotNull("User shouldn't be null", queriedUser);
		assertEquals("User name shouldn't be null", "Sample 1", queriedUser.getName());
	}

	@Test
	public void shouldReturnUserList() {
		userRepository.deleteAll();
		userService.addUser(new UserDto("Sample 1"));
		userService.addUser(new UserDto("Sample 2"));
		userService.addUser(new UserDto("Sample 3"));
		List<UserDto> userList = (List<UserDto>)userService.getUserList();
		assertEquals("User list", userList.size() ,3);
	}

	@Test
	public void shouldReturnUserAdded() {
		UserDto userDto = new UserDto();
		userDto.setName("User sample 1");
		UserDto userDtoAdded = userService.addUser(userDto);
		assertNotNull("User shouldn't be null", userDtoAdded);
		assertNotNull("User name shouldn't be null", userDtoAdded.getName());
	}
}
