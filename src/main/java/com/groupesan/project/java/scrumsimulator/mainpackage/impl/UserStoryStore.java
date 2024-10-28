package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserStoryStore {

    private static UserStoryStore userStoryStore;
    private List<UserStory> userStories;

    private UserStoryStore() {
        userStories = new ArrayList<>();
    }

    public static UserStoryStore getInstance() {
        if (userStoryStore == null) {
            userStoryStore = new UserStoryStore();
        }
        return userStoryStore;
    }

    public void addUserStory(UserStory userStory) {
        userStories.add(userStory);
    }

    public void removeUserStory(UserStory story) {
        userStories.remove(story);
    }

    public List<UserStory> getUserStories() {
        return new ArrayList<>(userStories);
    }

    public void updateUserStory(UserStory updatedUserStory) {
        for (int i = 0; i < userStories.size(); i++) {
            UserStory story = userStories.get(i);
            if (story.getId() == updatedUserStory.getId()) {  // Assuming 'getId()' exists in UserStory class
                userStories.set(i, updatedUserStory);
                break;
            }
        }
    }

    // Method to find a UserStory by its name
    public UserStory findUserStoryByName(String name) {
        Optional<UserStory> userStory = userStories.stream()
            .filter(story -> story.getName().equals(name))
            .findFirst();
        return userStory.orElse(null);
    }
}
