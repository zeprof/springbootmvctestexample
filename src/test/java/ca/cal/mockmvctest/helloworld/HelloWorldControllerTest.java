package ca.cal.mockmvctest.helloworld;

import ca.cal.mockmvctest.task.HelloWorldController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(HelloWorldController.class)
public class HelloWorldControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetHellWorld() throws Exception {
        String username = "John";

        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                        .param("personName", username))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello, " + username));
    }

    @Test
    public void testGetHellWorldNoParam() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello, world!"));
    }
}
