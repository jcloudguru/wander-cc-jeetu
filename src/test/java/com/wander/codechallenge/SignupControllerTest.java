package com.wander.codechallenge;

import com.wander.codechallenge.controllers.SignupController;
import com.wander.codechallenge.models.Role;
import com.wander.codechallenge.models.User;
import com.wander.codechallenge.repositories.RoleRepository;
import com.wander.codechallenge.repositories.UserRepository;
import com.wander.codechallenge.services.CustomUserDetailsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@WebMvcTest(SignupController.class)
@WithMockUser
public class SignupControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomUserDetailsService userDetailsService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    BCryptPasswordEncoder passwordEncoder;

    Role role = new Role("123456", "ADMIN");

    User mockUser = new User("000999", "testuser1@test.com", "password1", "TestUserFullName", true,
            new HashSet<>(Arrays.asList(role)));

    String testUser = "{\"098765\":\"Spring@test.com\",\"password3\":\"MockUser3\",\"true\":[\"ADMIN\"]}";

    @Test
    public void shouldLoadSignUpPageSuccessfully() throws Exception {

        Mockito.when(
                userDetailsService.findUserByEmail(Mockito.anyString())).thenReturn(mockUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/signup").accept(
                MediaType.ALL);

        // The result.getResponse() will return the Sign up page content. So check for status 200 and close.
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        // Assert - Status, ViewName and Response NotNull
        Assert.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        Assert.assertEquals(result.getModelAndView().getViewName(), "signup");
        Assert.assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void shouldCreateNewUserSuccessfully() throws Exception {

        User user = new User("00099900", "testuser2@test.com", "password2", "TestUserFullName2", true,
                new HashSet<>(Arrays.asList(role)));

        Mockito.when(userDetailsService.saveUser(Mockito.any(User.class))).thenReturn(mockUser);

        // Send course as body to /students/Student1/courses
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .accept(MediaType.APPLICATION_JSON).content(testUser)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(response.getStatus(), 200);

    }
}
