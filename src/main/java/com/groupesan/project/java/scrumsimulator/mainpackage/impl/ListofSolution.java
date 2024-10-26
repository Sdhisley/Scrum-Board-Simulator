package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

public class ListofSolution {

    public enum SolutionType {
        REFACTOR,
        MORE_PEOPLE,
        PAIR_PROGRAMMING,
        MOB_PROGRAMMING,
        RESEARCH,
        PROTOTYPING,
        TIMEBOX
    }

    private SolutionType solutionType;
    private List<String> userStories;

    public ListofSolution(SolutionType solutionType) {
        this.solutionType = solutionType;
        this.userStories = new ArrayList<>();
    }

    public SolutionType getSolutionType() {
        return solutionType;
    }

    public void addUserStory(String userStory) {
        if (!userStories.contains(userStory)) {
            userStories.add(userStory);
        }
    }

    public List<String> getUserStories() {
        return userStories;
    }

    @Override
    public String toString() {
        return solutionType.toString();
    }
}