package com.example.msgrants;

import com.example.msgrants.model.Household;
import com.example.msgrants.repository.HouseholdRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.util.List;

import static com.example.msgrants.constant.TestConstants.householdWithStudent;
import static com.example.msgrants.constant.TestConstants.richHousehold;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class MsGrantsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HouseholdRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();

        repository.save(householdWithStudent());
        repository.save(richHousehold());
    }

    @Test
    public void everyHouseholdsShouldReturnFromService() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/household")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        assertThat(mvcResult).isNotNull();

        List<Household> expectedResponse = objectMapper.readValue(new File("src/test/resources/allHouseholds.json"), new TypeReference<>(){});
        String responseBody = mvcResult.getResponse().getContentAsString();
        List<Household> actualResponse = objectMapper.readValue(responseBody, new TypeReference<>() {});
        assertThat(actualResponse).usingElementComparatorIgnoringFields("id").isEqualTo(expectedResponse);
    }

    @Test
    public void middleClassHouseholdsShouldReturnFromService() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/household")
                        .param("income", "150000")
                        .param("student", "false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        assertThat(mvcResult).isNotNull();

        List<Household> expectedResponse = objectMapper.readValue(new File("src/test/resources/middleClassHouseholds.json"), new TypeReference<>(){});
        String responseBody = mvcResult.getResponse().getContentAsString();
        List<Household> actualResponse = objectMapper.readValue(responseBody, new TypeReference<>() {});
        assertThat(actualResponse).usingElementComparatorIgnoringFields("id").isEqualTo(expectedResponse);
    }

    @Test
    public void studentEncouragementHouseholdsShouldReturnFromService() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/household")
                        .param("income", "150000")
                        .param("student", "true")
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
