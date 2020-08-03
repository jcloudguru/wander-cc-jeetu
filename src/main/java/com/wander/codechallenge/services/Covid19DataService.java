package com.wander.codechallenge.services;

import com.google.gson.Gson;
import com.wander.codechallenge.models.StateWiseStats;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class Covid19DataService {

    private static final Logger logger = LoggerFactory.getLogger(Covid19DataService.class);
    private static String COVID19_DATA_URL = "https://api.covid19india.org/data.json";

    private List<StateWiseStats> allStats = new ArrayList<>();

    public List<StateWiseStats> getAllStats() {
        return allStats;
    }

    /**
     * Schedule to run this post construct every 1st hour of the day.
     *
     * @return List of Location Statistics Info
     * @throws IOException
     * @throws InterruptedException
     */
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void getCovidData() throws IOException, InterruptedException {

        StringBuilder httpResponse = new StringBuilder();
        URL url = new URL(COVID19_DATA_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseStatus = connection.getResponseCode();
        if (responseStatus == 200) {
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                httpResponse.append(scanner.nextLine());
            }
        } else
            throw new RuntimeException("http response code: " + responseStatus);

        JSONObject jsonObject = new JSONObject(httpResponse.toString());


        JSONArray jsonArray = jsonObject.getJSONArray("statewise");
        jsonArray.remove(0).toString();

        Gson gson = new Gson();
        StateWiseStats[] stateWiseStats = gson.fromJson(jsonArray.toString(), StateWiseStats[].class);

        List<StateWiseStats> newStats = new ArrayList<>();
        newStats = Arrays.asList(stateWiseStats);
        this.allStats = newStats;

    }
}
