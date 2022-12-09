package com.company.umis.service;

import com.company.umis.model.User;
import com.company.umis.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserServiceTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

//    User user=new User(2L,"Kethia","ketia","ketia@gmail.com");


    @Test
        public void getAll_Success() throws Exception{

        User user = User.builder()
                .name("kethia")
                .username("Kent")
                .email("kent@gmail.com")
                .build();

            List<User> asList = List.of(user);
            when(userService.getAllUsers()).thenReturn(asList);

            MockHttpServletRequestBuilder requestBuilder= MockMvcRequestBuilders
                    .get("")
                    .accept(MediaType.APPLICATION_JSON);

            MvcResult result = mockMvc
                    .perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{\"id\":2,\"name\":\"Kethia\",\"username\":\"ketia\",\"email\":\"ketia@gmail.com\"}]"))
                    .andReturn();

        }
        
        @Test
        public void getOne_404() throws Exception{

            User user = User.builder()
                    .name("kethia")
                    .username("Kent")
                    .email("kent@gmail.com")
                    .build();

            when(userService.getUserById(user.getId())).thenReturn(Optional.ofNullable(user));
            MockHttpServletRequestBuilder requestBuilder=MockMvcRequestBuilders
                    .get("/user/{id}")
                    .accept(MediaType.APPLICATION_JSON);
            MvcResult result = mockMvc
                    .perform(requestBuilder)
                    .andExpect(status().isNotFound())
                    .andExpect(content().json("{\"status\":false,\"message\":\"Item not found\"}"))
                    .andReturn();
        }
    }

