package com.example.ThirdYearProject;

public class Challenge {
    private String pitch, date, challengedTeam, challengingTeam, challengingClubName, challengedTeamName;



    public Challenge(String pitch, String date, String challengedTeam, String challengingTeam, String challengingClubName, String challengedTeamName) {
        this.pitch = pitch;
        this.date = date;
        this.challengedTeam = challengedTeam;
        this.challengingTeam = challengingTeam;
        this.challengingClubName= challengingClubName;
        this.challengedTeamName = challengedTeamName;

    }

    public String getChallengedTeamName() {
        return challengedTeamName;
    }

    public void setChallengedTeamName(String challengedTeamName) {
        this.challengedTeamName = challengedTeamName;
    }

    public String getChallengingClubName() {
        return challengingClubName;
    }

    public void setChallengingClubName(String challengingClubName) {
        this.challengingClubName = challengingClubName;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChallengedTeam() {
        return challengedTeam;
    }

    public void setChallengedTeam(String challengedTeam) {
        this.challengedTeam = challengedTeam;
    }

    public String getChallengingTeam() {
        return challengingTeam;
    }

    public void setChallengingTeam(String challengingTeam) {
        this.challengingTeam = challengingTeam;
    }
}
