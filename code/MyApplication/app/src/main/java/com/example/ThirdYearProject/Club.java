package com.example.ThirdYearProject;

import java.util.ArrayList;

public class Club {
    private String club, email,  leagues, phoneNumber;
    private ArrayList<String> pitches;

    public Club(String club, String email, ArrayList<String> pitches, String leagues, String phoneNumber) {
        this.club = club;
        this.email = email;
        this.pitches = pitches;
        this.leagues = leagues;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Club{" +
                "club='" + club + '\'' +
                ", email='" + email + '\'' +
                ", leagues='" + leagues + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", pitches=" + pitches +
                '}';
    }

    public ArrayList<String> getPitches() {
        return pitches;
    }

    public void setPitches(ArrayList<String> pitches) {
        this.pitches = pitches;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLeagues() {
        return leagues;
    }

    public void setLeagues(String leagues) {
        this.leagues = leagues;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
