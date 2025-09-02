package com.example.poll_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class PollAppIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fullScenario_shouldWork() throws Exception {
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
        Long user1Id = Long.valueOf(user1Map.get("id").toString());

        ///###List all users (-> shows the newly created user)
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        ///###Create another user

        ///###List all users again (-> shows two users)

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
        Long option1Id = Long.valueOf(pollMap.get("option1").toString());
        Long option2Id = Long.valueOf(pollMap.get("option2").toString());

        ///###List polls (-> shows the new poll)
        mockMvc.perform(get("/polls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        ///###User 2 votes on the poll
        String voteJson = """
                {
                  "voterId": "{{user2Id}}",
                  "optionId": "{{option1Id}}"
                }
                """;
        MvcResult voteResult = mockMvc.perform(post("/polls" + pollId + "/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteJson))
                .andExpect(status().isCreated())
                .andReturn();
        Map vote = objectMapper.readValue(voteResult.getResponse().getContentAsString(), Map.class);
        Long voteId = Long.valueOf(vote.get("id").toString());

        ///###User 2 changes his vote
        String updateVoteJson = """
                {
                  "voterId": "{{user2Id}}",
                  "optionId": "{{option2Id}}"
                }
                """;
        mockMvc.perform(put("/polls" + pollId + "/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateVoteJson))
                .andExpect(status().isOk());

        ///###List votes (-> shows the most recent vote for User 2)
        mockMvc.perform(get("/votes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        ///###Delete the one poll
        mockMvc.perform(get("/polls" + pollId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        ///###List votes (-> empty)
        mockMvc.perform(get("/votes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

}