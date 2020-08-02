package com.wander.codechallenge.controllers;

import com.wander.codechallenge.models.StateWiseStats;
import com.wander.codechallenge.models.User;
import com.wander.codechallenge.services.Covid19DataService;
import com.wander.codechallenge.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SignupController {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private Covid19DataService covid19DataService;

    /**
     * Model and View for the main login page.0
     * @return
     */
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     * main default home page.
     * @return
     */
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");

        // Top 5 states
        List<StateWiseStats> allStats = covid19DataService.getAllStats();
        modelAndView.addObject("MS", allStats.get(0).getConfirmed());
        modelAndView.addObject("TN", allStats.get(1).getConfirmed());
        modelAndView.addObject("DL", allStats.get(2).getConfirmed());
        modelAndView.addObject("AP", allStats.get(3).getConfirmed());
        modelAndView.addObject("KA", allStats.get(4).getConfirmed());

        return modelAndView;
    }

    /**
     * This is for Signup or Register.
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    /**
     * If this is a NEW user, then create that.
     * @param user
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with this Email Id. Please provide another.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            User newUser = userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }
}
