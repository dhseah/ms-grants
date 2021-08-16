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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.msgrants.constant.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        repository.save(richHousehold());
        repository.save(nuclearHouseholdWithBaby());
        repository.save(householdWithStudent());
        repository.save(householdWithElderly());
    }

    @Test
    public void householdShouldBeReturnedAfterAdd() throws Exception {
        String householdJson = objectMapper.writeValueAsString(newHousehold());

        MvcResult mvcResult = this.mockMvc.perform(post("/household")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(householdJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        assertThat(mvcResult).isNotNull();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Household actualResponse = objectMapper.readValue(responseBody, new TypeReference<>() {
        });
        assertThat(actualResponse.getHousingType()).isEqualTo("Condominium");
        assertThat(actualResponse.getHouseholdMembers().size()).isEqualTo(0);
    }

    @Test
    public void householdMembersShouldBeUpdated() throws Exception {
        Household household = repository.save(newHousehold());
        String memberJson = objectMapper.writeValueAsString(newHouseholdMember());

        MvcResult mvcResult = this.mockMvc.perform(put("/household/" + household.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(memberJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult).isNotNull();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Household actualResponse = objectMapper.readValue(responseBody, new TypeReference<>() {
        });
        assertThat(actualResponse.getHousingType()).isEqualTo("Condominium");
        assertThat(actualResponse.getHouseholdMembers().get(0)).isEqualTo(newHouseholdMember());
    }

    @Test
    public void everyHouseholdsShouldBeReturned() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/households")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult).isNotNull();

        String responseBody = mvcResult.getResponse().getContentAsString();
        List<Household> actualResponse = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        List<Household> expectedResponse = households(richHousehold(), nuclearHouseholdWithBaby(), householdWithStudent(), householdWithElderly());

        assertThat(actualResponse).usingElementComparatorIgnoringFields("id").isEqualTo(expectedResponse);
    }

    @Test
    public void middleClassHouseholdsShouldBeReturned() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/households")
                        .param("income", "150000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult).isNotNull();

        String responseBody = mvcResult.getResponse().getContentAsString();
        List<Household> actualResponse = objectMapper.readValue(responseBody, new TypeReference<>() {});

        List<Household> expectedResponse = households(householdWithStudent(), householdWithElderly());

        assertThat(actualResponse).usingElementComparatorIgnoringFields("id").isEqualTo(expectedResponse);
    }

    @Test
    public void studentEncouragementHouseholdsShouldBeReturned() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/households")
                        .param("income", "150000")
                        .param("student", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult).isNotNull();

        String responseBody = mvcResult.getResponse().getContentAsString();
        List<Household> actualResponse = objectMapper.readValue(responseBody, new TypeReference<>() {});
        List<Household> expectedResponse = objectMapper.readValue(new File("src/test/resources/studentEncouragementHouseholds.json"), new TypeReference<>() {});
        assertThat(actualResponse).usingElementComparatorIgnoringFields("id").isEqualTo(expectedResponse);
    }

    @Test
    public void householdsWithElderlyShouldBeReturned() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/households")
                        .param("elderly", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult).isNotNull();

        String responseBody = mvcResult.getResponse().getContentAsString();
        List<Household> actualResponse = objectMapper.readValue(responseBody, new TypeReference<>() {});
        List<Household> expectedResponse = objectMapper.readValue(new File("src/test/resources/elderlyHouseholds.json"), new TypeReference<>() {});
        assertThat(actualResponse).usingElementComparatorIgnoringFields("id").isEqualTo(expectedResponse);
    }

    @Test
    public void nuclearHouseholdsWithBabyShouldBeReturned() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/households")
                        .param("nuclear", "true")
                        .param("baby", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(mvcResult).isNotNull();

        String responseBody = mvcResult.getResponse().getContentAsString();
        List<Household> actualResponse = objectMapper.readValue(responseBody, new TypeReference<>() {});
        List<Household> expectedResponse = objectMapper.readValue(new File("src/test/resources/nuclearHouseholds.json"), new TypeReference<>() {});
        assertThat(actualResponse).usingElementComparatorIgnoringFields("id").isEqualTo(expectedResponse);
    }


    private List<Household> households(Household... households) {
        return new ArrayList<>(Arrays.asList(households));
    }
}
