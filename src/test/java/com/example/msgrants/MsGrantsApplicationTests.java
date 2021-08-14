package com.example.msgrants;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class MsGrantsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void studentEncouragementHouseholdsShouldReturnFromService() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/household")
                        .param("student", "true")
                        .param("income", "150000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult).isNotNull();

        String expectedResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/studentEncouragementHouseholds.json")));
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
    }
}
