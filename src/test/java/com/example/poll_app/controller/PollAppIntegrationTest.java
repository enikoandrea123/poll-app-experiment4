package com.example.poll_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PollAppIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void pollAppIntegrationTests() throws Exception {
        ///Create a new user
        String user1Json = """
                {
                  "username": "user1",
                  "email": "user1@email.com"
                }
                """;
        MvcResult user1Result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user1Json))
                .andExpect(status().isCreated())
                .andReturn();
        Map user1Map = objectMapper.readValue(user1Result.getResponse().getContentAsString(), Map.class);
        Long user1Id = ((Number) user1Map.get("id")).longValue();

        ///###List all users (-> shows the newly created user)
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        ///###Create another user
        String user2Json = """
                {
                "username": "user2",
                "email": "user2@email.com"
                }
                """;
        MvcResult user2Result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user2Json))
                .andExpect(status().isCreated())
                .andReturn();
        Map user2Map = objectMapper.readValue(user2Result.getResponse().getContentAsString(), Map.class);
        Long user2Id = Long.valueOf(user2Map.get("id").toString());

        ///###List all users again (-> shows two users)
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));


        ///###User 1 creates a new poll
        String pollJson = String.format("""
                {
                  "question": "What's your favorite animal?",
                  "publishedAt": "2025-08-30T10:00:00Z",
                  "validUntil": "2026-01-01T00:00:00Z",
                  "creatorId": %d,
                  "isPublic": true,
                  "allowSingleVotePerUser": true,
                  "options": [
                    { "caption": "dog", "presentationOrder": 1 },
                    { "caption": "cat", "presentationOrder": 2 }
                  ]
                }
                """, user1Id);
        MvcResult pollResult = mockMvc.perform(post("/polls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pollJson))
                .andExpect(status().isCreated())
                .andReturn();
        Map pollMap = objectMapper.readValue(pollResult.getResponse().getContentAsString(), Map.class);
        Long pollId = Long.valueOf(pollMap.get("id").toString());
        List<Map<String, Object>> options = (List<Map<String, Object>>) pollMap.get("options");
        Long option1Id = ((Number) options.get(0).get("id")).longValue();
        Long option2Id = ((Number) options.get(1).get("id")).longValue();

        ///###List polls (-> shows the new poll)
        mockMvc.perform(get("/polls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        ///###User 2 votes on the poll
        String voteJson = String.format("""
        {
          "voterId": %d,
          "optionId": %d
        }
        """, user2Id, option1Id);
        MvcResult voteResult = mockMvc.perform(post("/polls/" + pollId + "/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteJson))
                .andExpect(status().isCreated())
                .andReturn();
        Map vote = objectMapper.readValue(voteResult.getResponse().getContentAsString(), Map.class);
        Long voteId = ((Number) vote.get("id")).longValue();

        ///###User 2 changes his vote
        String updateVoteJson = String.format("""
        {
          "voterId": %d,
          "optionId": %d
        }
        """, user2Id, option2Id);
        mockMvc.perform(put("/polls/" + pollId + "/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateVoteJson))
                .andExpect(status().isOk());

        ///###List votes (-> shows the most recent vote for User 2)
        mockMvc.perform(get("/votes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        ///###Delete the one poll
        mockMvc.perform(delete("/polls/" + pollId))
                .andExpect(status().isNoContent());
    }
    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        ///Create a new user
        String userJson = """
                {
                  "username": "user1",
                  "email": "user1@email.com"
                }
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.email").value("user1@email.com"));
    }

    @Test
    void listAllUsers_shouldReturnEmptyListInitially() throws Exception {
        ///###List all users (-> shows the newly created user)
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void listVotesEmpty() throws Exception {
        ///###List votes (-> empty)
        mockMvc.perform(get("/votes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

}