package ca.cal.mockmvctest.task;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TaskService taskService;

  @Test
  public void shouldRejectCreatingReviewsWhenUserIsAnonymous() throws Exception {
    this.mockMvc
      .perform(
        post("/api/tasks")
          .contentType(MediaType.APPLICATION_JSON)
          .content("{\"taskTitle\": \"Learn MockMvc\"}")
          //.with(csrf())
      )
      .andExpect(status().isCreated());
  }

  @Test
  public void shouldReturnLocationOfReviewWhenUserIsAuthenticatedAndCreatesReview() throws Exception {
    when(taskService.createTask(anyString())).thenReturn(42L);

    mockMvc
      .perform(
        post("/api/tasks")
          .contentType(MediaType.APPLICATION_JSON)
          .content("{\"taskTitle\": \"Learn MockMvc\"}")
          //.with(csrf())
          //.with(user("duke"))
      )
      .andExpect(status().isCreated())
      .andExpect(header().exists("Location"))
      .andExpect(header().string("Location", Matchers.containsString("42")));

    verify(taskService, times(1)).createTask(anyString());
  }

  @Test
  public void shouldRejectDeletingReviewsWhenUserLacksAdminRole() throws Exception {
    this.mockMvc
      .perform(delete("/api/tasks/42"))
      .andExpect(status().isOk());
  }

  @Test
  public void shouldAllowDeletingReviewsWhenUserIsAdmin() throws Exception {
    this.mockMvc
      .perform(
        delete("/api/tasks/42")

      )
      .andExpect(status().isOk());

    verify(taskService).deleteTask(42L);
  }
}
