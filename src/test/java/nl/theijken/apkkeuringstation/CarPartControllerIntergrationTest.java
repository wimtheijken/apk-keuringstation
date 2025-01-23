package nl.theijken.apkkeuringstation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CarPartControllerIntergrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreateCorrectAction() throws Exception {

        String requestJson = """
                {
                    "name" : "Autoband",
                    "price" : 100.00
                }
                """;

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/carparts")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseJson);
        String createdId = jsonNode.get("id").asText();

        // check location field in response header (using Hamcrest regex matcher)
        assertThat(result.getResponse().getHeader("Location"), matchesPattern("^.*/carparts/" + createdId));
    }

    @Test
    void shouldGetCorrectAction() throws Exception {

        String requestJson = """
                {
                    "name" : "Autoband",
                    "price" : 100.00
                }
                """;

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/carparts")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseJson);
        String createdId = jsonNode.get("id").asText();

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/carparts/" + createdId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Autoband")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", is(100.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(Integer.parseInt(createdId))));
    }
}