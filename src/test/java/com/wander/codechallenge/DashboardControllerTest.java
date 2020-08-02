package com.wander.codechallenge;

import com.wander.codechallenge.controllers.DashboardController;
import com.wander.codechallenge.models.StateWiseStats;
import com.wander.codechallenge.repositories.RoleRepository;
import com.wander.codechallenge.repositories.UserRepository;
import com.wander.codechallenge.services.Covid19DataService;
import com.wander.codechallenge.services.CustomUserDetailsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DashboardController.class)
@WithMockUser
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Covid19DataService covid19DataService;

    @MockBean
    CustomUserDetailsService userDetailsService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    BCryptPasswordEncoder passwordEncoder;

    StateWiseStats mockStats = new StateWiseStats("998877", "Maharashtra",
            120000, 400000, 250000, 55000, 1500000);

    @Test
    public void whenAccessExternalURI_thenReturn403Forbidden() throws Exception {
        Mockito.when(covid19DataService.getAllStats().get(0)).thenReturn(mockStats);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/dashboard").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), HttpStatus.FORBIDDEN.value());
    }
}
