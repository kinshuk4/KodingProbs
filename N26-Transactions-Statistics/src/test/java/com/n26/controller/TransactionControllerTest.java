package com.n26.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.n26.constant.Constants.ENDPOINT_TRANSACTIONS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void when_empty_request_body_get_unprocessable_entity() throws JsonProcessingException, Exception {
        ResultActions resultActions = performRequest("", "");
        resultActions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void when_invalid_json_get_unprocessable_entity() throws JsonProcessingException, Exception {
        ResultActions resultActions = performRequest("12.3343", "2018-08-12");
        resultActions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void when_invalid_json_get_bad_request() throws JsonProcessingException, Exception {
        ResultActions resultActions = performRequest("Invalid Json");
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void when_transaction_in_future_get_unprocessable_entity() throws JsonProcessingException, Exception {
        ResultActions resultActions = performRequest("12.3343", Instant.now().plusSeconds(180));
        resultActions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void when_everything_is_right_get_created() throws JsonProcessingException, Exception {
        ResultActions resultActions = performRequest("12.3343", Instant.now().minusSeconds(30));
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void when_transaction_older_then_one_minute_get_no_content() throws JsonProcessingException, Exception {
        ResultActions resultActions = performRequest("12.3343", Instant.now().minusSeconds(61));
        resultActions.andExpect(status().isNoContent());
    }

    private ResultActions performRequest(String amount, Instant timestamp) throws JsonProcessingException, Exception {
        return performRequest(amount, DateTimeFormatter.ISO_INSTANT.format(timestamp));
    }

    private ResultActions performRequest(String amount, String timestamp)throws JsonProcessingException, Exception {
        Map<String, String> request = new HashMap<>();
        request.put("amount", amount);
        request.put("timestamp", timestamp);
        return performRequest(MAPPER.writeValueAsString(request));
    }

    private ResultActions performRequest(String data)throws JsonProcessingException, Exception {
        ResultActions resultActions= mockMvc.perform(post(ENDPOINT_TRANSACTIONS).contentType(MediaType.APPLICATION_JSON)
                .content(data));
        return resultActions;
    }

}