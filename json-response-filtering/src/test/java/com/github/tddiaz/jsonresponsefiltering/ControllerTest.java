package com.github.tddiaz.jsonresponsefiltering;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Application.Controller.class)
public class ControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCallingFilterEndpoint_thenShouldReturnResponseWithoutField3() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/filter"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Map<String, String> resultAsMap = objectMapper.readValue(content, Map.class);

        assertThat(resultAsMap.get("field1"), notNullValue());
        assertThat(resultAsMap.get("field2"), notNullValue());
        assertThat(resultAsMap.get("field3"), nullValue());
    }
}
