package com.wander.codechallenge.controllers;

import com.wander.codechallenge.models.StateWiseStats;
import com.wander.codechallenge.models.User;
import com.wander.codechallenge.services.Covid19DataService;
import com.wander.codechallenge.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class DashboardController {

    Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private Covid19DataService covid19DataService;

    /**
     * COVID-19 India dashboard. This is a secured page, will be shown only after successful login.
     * @return
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());

        List<StateWiseStats> allStats = covid19DataService.getAllStats();
        int totalActive = allStats.stream().mapToInt(ta -> ta.getActive()).sum();
        int totalConfirmed = allStats.stream().mapToInt(tc -> tc.getConfirmed()).sum();
        int totalRecovered = allStats.stream().mapToInt(tr -> tr.getRecovered()).sum();
        int totalDeceased = allStats.stream().mapToInt(td -> td.getDeaths()).sum();

        // overall India Totals
        modelAndView.addObject("stateWiseStats", allStats);
        modelAndView.addObject("totalActive", totalActive);
        modelAndView.addObject("totalConfirmed", totalConfirmed);
        modelAndView.addObject("totalRecovered", totalRecovered);
        modelAndView.addObject("totalDeceased", totalDeceased);

        modelAndView.setViewName("dashboard");
        return modelAndView;
    }
}
