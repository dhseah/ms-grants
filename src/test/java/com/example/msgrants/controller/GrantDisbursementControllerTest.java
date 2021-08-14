package com.example.msgrants.controller;

import com.example.msgrants.service.GrantDisbursementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.msgrants.constant.TestConstants.studentEncouragementHouseholds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
public class GrantDisbursementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GrantDisbursementService service;

    @Test
    public void studentEncouragementHouseholdsShouldReturnFromService() throws Exception {
        when(service.searchHouseholds()).thenReturn(studentEncouragementHouseholds());

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
