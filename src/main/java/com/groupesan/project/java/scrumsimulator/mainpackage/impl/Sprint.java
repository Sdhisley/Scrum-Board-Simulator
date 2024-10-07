package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

public class Sprint {
    private ArrayList<UserStory> userStories = new ArrayList<>();
    private String name;
    private String description;
    private int length;  // Duration of the sprint
    private int number;
    private int remainingDays;
    private int id;

    public Sprint(String name, String description, int length, int number, int id) {
        this.name = name;
        this.description = description;
        this.length = length;
        this.number = number;
        this.remainingDays = length;
        this.id = id;
    }

    public void addUserStory(UserStory us) {
        userStories.add(us);
    }

    public List<UserStory> getUserStories() {
        return new ArrayList<>(userStories);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;  // Setter for name
    }

    public String getDescription() {
        return description;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;  // Setter for length
    }

    public int getNumber() {
        return number;
    }

    public int getDaysRemaining() {
        return remainingDays;
    }

    public void decrementRemainingDays() {
        if (remainingDays > 0) remainingDays--;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        String header = "Sprint " + this.id + ": " + this.name + "\n";
        StringBuilder USes = new StringBuilder();

        for (UserStory us : userStories) {
            USes.append(us.toString()).append("\n");
        }
        return header + USes;
    }
}
