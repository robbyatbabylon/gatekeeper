package com.babylonhealth.bamstemplate.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.babylonhealth.bamstemplate.controller.ExampleApiController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(
    controllers = {ExampleApiController.class},
    includeFilters = @Filter(classes = EnableWebSecurity.class))
class ExampleApiControllerTest {

  @Autowired private MockMvc mockMvc; // NOPMD

  @Test
  void controllerWorks() throws Exception {
    this.mockMvc
        .perform(get("/v1/exampleApi"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Hello world!")));
  }
}
