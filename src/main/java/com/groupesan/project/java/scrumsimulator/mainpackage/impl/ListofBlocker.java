package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

public class ListofBlocker {

    public enum BlockerType {
        TIME_BLOCKER,
        TECHNICAL_DEBT,
        PEOPLE_BLOCKER,
        NEEDS_MORE_INFO,
        REQUIREMENTS_ISSUE
    }

    private BlockerType blockerType;
    private List<String> userStories;

    public ListofBlocker(BlockerType blockerType) {
        this.blockerType = blockerType;
        this.userStories = new ArrayList<>();
    }

    public BlockerType getBlockerType() {
        return blockerType;
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
        return blockerType.toString();
    }
}