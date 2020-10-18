package com.example.ThirdYearProject;
// This file holds the active game object. It requires both teams names, ID's, a date and a venue to be created.
// It is used when adding a active game to the database and nowhere else.

public class ActiveGame {
    private String teamOneName, teamTwoName, teamOneID, teamTwoID, venue, date;

    public ActiveGame(String teamOneName, String teamTwoName, String teamOneID, String teamTwoID, String venue, String date) {
        this.teamOneName = teamOneName;
        this.teamTwoName = teamTwoName;
        this.teamOneID = teamOneID;
        this.teamTwoID = teamTwoID;
        this.venue = venue;
        this.date = date;
    }

    @Override
    public String toString() {
        return "ActiveGame{" +
                "teamOneName='" + teamOneName + '\'' +
                ", teamTwoName='" + teamTwoName + '\'' +
                ", teamOneID='" + teamOneID + '\'' +
                ", teamTwoID='" + teamTwoID + '\'' +
                ", venue='" + venue + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getTeamOneName() {
        return teamOneName;
    }

    public void setTeamOneName(String teamOneName) {
        this.teamOneName = teamOneName;
    }

    public String getTeamTwoName() {
        return teamTwoName;
    }

    public void setTeamTwoName(String teamTwoName) {
        this.teamTwoName = teamTwoName;
    }

    public String getTeamOneID() {
        return teamOneID;
    }

    public void setTeamOneID(String teamOneID) {
        this.teamOneID = teamOneID;
    }

    public String getTeamTwoID() {
        return teamTwoID;
    }

    public void setTeamTwoID(String teamTwoID) {
        this.teamTwoID = teamTwoID;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
