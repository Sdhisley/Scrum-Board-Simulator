package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

public class UserStoryFactory {

    private static UserStoryFactory userStoryFactory;

    public static UserStoryFactory getInstance() {
        if (userStoryFactory == null) {
            userStoryFactory = new UserStoryFactory();
        }
        return userStoryFactory;
    }

    private UserStoryFactory() {}

    public UserStory createNewUserStory(String name, String description, double pointValue, double businessValue, String assignStatus) {
        List<String> blockers = new ArrayList<>();
        UserStory newUS = new UserStory(name, description, pointValue, businessValue, assignStatus, "New", "NA", blockers);
        return newUS;
    }
}
