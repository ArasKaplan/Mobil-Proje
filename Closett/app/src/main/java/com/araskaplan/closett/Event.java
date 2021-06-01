package com.araskaplan.closett;

public class Event {
    String name;
    String type;
    String date;
    String location;
    Combination combination;

    public Event(String name, String type, String date, String location, Combination combination) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.location = location;
        this.combination = combination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Combination getCombination() {
        return combination;
    }

    public void setCombination(Combination combination) {
        this.combination = combination;
    }
}
