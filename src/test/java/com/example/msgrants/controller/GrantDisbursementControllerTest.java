package com.example.msgrants.controller;

import com.example.msgrants.model.Household;
import com.example.msgrants.service.GrantDisbursementService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.util.List;

import static com.example.msgrants.constant.TestConstants.studentEncouragementHouseholds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
public class GrantDisbursementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GrantDisbursementService service;

    @Test
    public void studentEncouragementHouseholdsShouldReturnFromService() throws Exception {
        when(service.searchHouseholds(anyInt())).thenReturn(studentEncouragementHouseholds());

        MvcResult mvcResult = this.mockMvc.perform(get("/household")
                        .param("income", "150000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        assertThat(mvcResult).isNotNull();

        List<Household> expectedResponse = objectMapper.readValue(new File("src/test/resources/studentEncouragementHouseholds.json"), new TypeReference<>(){});
        String responseBody = mvcResult.getResponse().getContentAsString();
        List<Household> actualResponse = objectMapper.readValue(responseBody, new TypeReference<>() {});
        assertThat(actualResponse).usingElementComparatorIgnoringFields("id").isEqualTo(expectedResponse);
    }
}
