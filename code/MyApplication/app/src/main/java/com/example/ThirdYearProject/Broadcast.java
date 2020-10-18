package com.example.ThirdYearProject;
// The file holds the game broadcast object. It requires a pitch, division, date, creatorID, challengerID and creator name to be created
// It is used for uploading broadcasts to the database.
public class Broadcast {
    private String venue, division, date, creator, challenger, name;



    public Broadcast(String name, String venue, String division, String date, String creator, String challenger) {
        this.name = name;
        this.venue = venue;
        this.division = division;
        this.date = date;
        this.creator = creator;
        this.challenger = challenger;
    }

    @Override
    public String toString() {
        return "Broadcast{" +
                "venue='" + venue + '\'' +
                ", division='" + division + '\'' +
                ", date='" + date + '\'' +
                ", creator=" + creator +
                ", challenger=" + challenger +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getChallenger() {
        return challenger;
    }

    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }
}
