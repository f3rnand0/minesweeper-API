package com.minesweeper.restapi;

import com.minesweeper.restapi.dto.UserDto;
import com.minesweeper.restapi.repository.UserRepository;
import com.minesweeper.restapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

//@RunWith(SpringRunner.class)
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
        List<UserDto> userList = (List<UserDto>) userService.getUserList();
        assertEquals("User list size different", 3, userList.size());
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
