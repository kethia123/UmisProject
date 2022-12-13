package com.company.umis.controller;

import com.company.umis.model.User;
import com.company.umis.service.UserService;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void ReturnSavedUser_Success() throws Exception{

        User user = User.builder()
                .name("ketty")
                .username("whiteKet")
                .email("ketty@gmail.com")
                .build();
        given(userService.saveUser(any(User.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(user.getName())))
                .andExpect(jsonPath("$.username",
                        is(user.getUsername())))
                .andExpect(jsonPath("$.email",
                        is(user.getEmail())));

    }

    // Test for Get All users REST API
    @Test
    public void ReturnListOfUsers_Success() throws Exception{

        List<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(User.builder().name("ketty").username("whiteKet").email("ketty@gmail.com").build());
        listOfUsers.add(User.builder().name("Tony").username("Stark").email("tony@gmail.com").build());
        given(userService.getAllUsers()).willReturn(listOfUsers);

        ResultActions response = mockMvc.perform(get("/users"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfUsers.size())));

    }

    // Test for GET user by id REST API (valid user id)
    @Test
    public void ReturnUserObject_Success() throws Exception{

        long userId = 1L;
        User user = User.builder()
                .name("ketty")
                .username("whiteKet")
                .email("ketty@gmail.com")
                .build();
        given(userService.getUserById(userId)).willReturn(Optional.of(user));

        ResultActions response = mockMvc.perform(get("/user/{id}", userId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));

    }

    // Test for GET user by id REST API (invalid id)
    @Test
    public void ReturnEmpty() throws Exception{
        // given - precondition or setup
        long userId = 1L;
        User user = User.builder()
                .name("ketty")
                .username("whiteKet")
                .email("ketty@gmail.com")
                .build();
        given(userService.getUserById(userId)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/user/{id}", userId));

        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    // Test for update user REST API - positive scenario
    @Test
    public void ReturnUpdateUserObject_Success() throws Exception{
        // given - precondition or setup
        long userId = 1L;
        User savedUser = User.builder()
                .name("ketty")
                .username("whiteKet")
                .email("ketty@gmail.com")
                .build();

        User updatedUser = User.builder()
                .name("Remy")
                .username("Doverem")
                .email("remdo@gmail.com")
                .build();
        given(userService.getUserById(userId)).willReturn(Optional.of(savedUser));
        given(userService.updateUser(any(User.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedUser.getName())))
                .andExpect(jsonPath("$.username", is(updatedUser.getUsername())))
                .andExpect(jsonPath("$.email", is(updatedUser.getEmail())));
    }

    // Test for update user REST API - negative scenario
    @Test
    public void Return404_FailingTest() throws Exception{

        long userId = 1L;
        User savedUser = User.builder()
                .name("ketty")
                .username("whiteKet")
                .email("ketty@gmail.com")
                .build();

        User updatedUser = User.builder()
                .name("Remy")
                .username("Doverem")
                .email("remdo@gmail.com")
                .build();
        given(userService.getUserById(userId)).willReturn(Optional.empty());
        given(userService.updateUser(any(User.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // Test for delete user REST API
    @Test
    public void Return200_Success() throws Exception{

        long userId = 1L;
        willDoNothing().given(userService).deleteUser(userId);

        ResultActions response = mockMvc.perform(delete("/user/{id}", userId));

        response.andExpect(status().isOk())
                .andDo(print());
    }
}