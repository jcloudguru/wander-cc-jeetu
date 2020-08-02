package com.wander.codechallenge.models;

public class StateWiseStats {

    private String id;
    private String state;
    private int active;
    private int confirmed;
    private int recovered;
    private int deaths;
    private int latestTotalCases;

    public StateWiseStats() {
    }

    public StateWiseStats(String id, String state, int active, int confirmed, int recovered, int deaths, int latestTotalCases) {
        this.id = id;
        this.state = state;
        this.active = active;
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.deaths = deaths;
        this.latestTotalCases = latestTotalCases;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", active=" + active +
                ", confirmed=" + confirmed +
                ", recovered=" + recovered +
                ", deaths=" + deaths +
                '}';
    }
}
