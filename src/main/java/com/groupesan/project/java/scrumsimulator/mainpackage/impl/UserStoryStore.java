package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

public class UserStoryStore {

    private static UserStoryStore userStoryStore;

    public static UserStoryStore getInstance() {
        if (userStoryStore == null) {
            userStoryStore = new UserStoryStore();
        }
        return userStoryStore;
    }

    private List<UserStory> userStories;

    private UserStoryStore() {
        userStories = new ArrayList<>();
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
}
