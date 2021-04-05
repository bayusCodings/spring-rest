package com.app.manager.controller;

import com.app.manager.model.User;
import com.app.manager.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @MockBean
    protected UserService userService;

    private User testUser;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        testUser = new User();
        testUser.setFirstName("Abayomi");
        testUser.setLastName("Ogunbayo");
        testUser.setEmail("ogunbayo.abayo@gmail.com");
        testUser.setDateOfBirth("01/01/1776");
        testUser.setGender("Male");
        testUser.setPhone("08064621983");
        testUser.setNationality("Nigerian");
        testUser.setBlocked(false);
    }

    @Test
    public void createUserTest() throws Exception {
        // Mocking service
        when(userService.save(any(User.class))).thenReturn(testUser);

        mockMvc.perform(
            post("/api/user/new")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(testUser))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstName", is("Abayomi")))
            .andExpect(jsonPath("$.lastName", is("Ogunbayo")))
            .andExpect(jsonPath("$.email", is("ogunbayo.abayo@gmail.com")))
            .andExpect(jsonPath("$.gender", is("Male")));
    }

    @Test
    public void updateUserTest() throws Exception {
        // Mocking service
        when(userService.getUserById(any(Long.class))).thenReturn(Optional.of(testUser));
        when(userService.save(any(User.class))).thenReturn(testUser);

        mockMvc.perform(
                put("/api/user/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testUser))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Abayomi")))
                .andExpect(jsonPath("$.lastName", is("Ogunbayo")))
                .andExpect(jsonPath("$.email", is("ogunbayo.abayo@gmail.com")))
                .andExpect(jsonPath("$.gender", is("Male")));
    }

    @Test
    public void blockUserTest() throws Exception {
        // Mocking service
        when(userService.blockUser(any(User.class))).thenReturn(testUser);
        when(userService.getUserById(any(Long.class))).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/user/block/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void unblockUserTest() throws Exception {
        // Mocking service
        when(userService.blockUser(any(User.class))).thenReturn(testUser);
        when(userService.getUserById(any(Long.class))).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/user/unblock/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
