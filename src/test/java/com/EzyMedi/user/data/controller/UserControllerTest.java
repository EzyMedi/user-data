package com.EzyMedi.user.data.controller;

import com.EzyMedi.user.data.constants.Role;
import com.EzyMedi.user.data.model.User;
import com.EzyMedi.user.data.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.junit.jupiter.api.extension.ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private User mockDoctor;

    @BeforeEach
    void setUp() {
        mockDoctor = new User();
        mockDoctor.setUserId(UUID.randomUUID());
        mockDoctor.setFullName("Test Doctor");
        mockDoctor.setGender("Female");
        mockDoctor.setEmail("test@example.com");
        mockDoctor.setPhone("1234567890");
        mockDoctor.setRole(Role.DOCTOR);
        mockDoctor.setFollowing(new ArrayList<>());
        mockDoctor.setFollowers(new ArrayList<>());

        // Initialize MockMvc manually
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(mockDoctor);

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockDoctor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Test Doctor"))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.role").value("DOCTOR"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(mockDoctor));

        mockMvc.perform(get("/user/get"))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Test Doctor"));
    }

    @Test
    void testGetAllDoctors() throws Exception {
        when(userService.getAllDoctors()).thenReturn(List.of(mockDoctor));

        mockMvc.perform(get("/user/doctors/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Test Doctor"));
    }

    @Test
    void testGetAllPatients() throws Exception {
        User patient = new User();
        patient.setUserId(UUID.randomUUID());
        patient.setFullName("Test Patient");
        patient.setGender("Female");
        patient.setEmail("patient@example.com");
        patient.setPhone("020200202");
        patient.setRole(Role.PATIENT);
        patient.setFollowing(new ArrayList<>());
        patient.setFollowers(new ArrayList<>());

        when(userService.getAllPatients()).thenReturn(List.of(patient));

        mockMvc.perform(get("/user/patients/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Test Patient"));
    }

    @Test
    void testUpdateUser() throws Exception {
        UUID userId = mockDoctor.getUserId();

        User updatedInfo = new User();
        updatedInfo.setFullName("Updated Name");

        User updatedUser = new User();
        updatedUser.setUserId(userId);
        updatedUser.setFullName("Updated Name");
        updatedUser.setGender(mockDoctor.getGender());
        updatedUser.setEmail(mockDoctor.getEmail());
        updatedUser.setPhone(mockDoctor.getPhone());
        updatedUser.setRole(mockDoctor.getRole());
        updatedUser.setFollowing(new ArrayList<>());
        updatedUser.setFollowers(new ArrayList<>());

        when(userService.updateUser(eq(userId), any(User.class)))
                .thenReturn(ResponseEntity.ok(updatedUser));

        mockMvc.perform(put("/user/" + userId + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testUpdateUser_NotFound() throws Exception {
        UUID userId = UUID.randomUUID();

        User updatedInfo = new User();
        updatedInfo.setFullName("Updated Name");

        when(userService.updateUser(eq(userId), any(User.class)))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(put("/user/" + userId + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedInfo)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(mockDoctor.getUserId())).thenReturn(mockDoctor);

        mockMvc.perform(get("/user/" + mockDoctor.getUserId() + "/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/" + mockDoctor.getUserId() + "/delete"))
                .andExpect(status().isOk());
    }

    @Test
    void testFollowUser() throws Exception {
        UUID toFollowId = UUID.randomUUID();

        when(userService.follow(mockDoctor.getUserId(), toFollowId))
                .thenReturn(ResponseEntity.ok("Followed successfully"));

        mockMvc.perform(post("/user/" + mockDoctor.getUserId() + "/follow/" + toFollowId))
                .andExpect(status().isOk())
                .andExpect(content().string("Followed successfully"));
    }

    @Test
    void testUnfollowUser() throws Exception {
        UUID toUnfollowId = UUID.randomUUID();

        when(userService.unfollow(mockDoctor.getUserId(), toUnfollowId))
                .thenReturn(ResponseEntity.ok("Unfollowed successfully"));

        mockMvc.perform(post("/user/" + mockDoctor.getUserId() + "/unfollow/" + toUnfollowId))
                .andExpect(status().isOk())
                .andExpect(content().string("Unfollowed successfully"));
    }

    @Test
    void testGetFollowers() throws Exception {
        User follower = new User();
        follower.setUserId(UUID.randomUUID());
        follower.setFullName("Follower One");
        follower.setGender("Female");
        follower.setEmail("follower@example.com");
        follower.setPhone("09099090");
        follower.setRole(Role.PATIENT);

        when(userService.getFollowers(mockDoctor.getUserId())).thenReturn(List.of(follower));

        mockMvc.perform(get("/user/" + "getFollowers/" + mockDoctor.getUserId() ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Follower One"));
    }
}
