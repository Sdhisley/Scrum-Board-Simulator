package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.Player;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumIdentifier;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumObject;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.UserStoryDeletedState;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.UserStoryState;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.UserStoryUnselectedState;

public class UserStory extends ScrumObject {
    private UserStoryIdentifier id;
    private String name;
    private String description;
    private double pointValue;
    private double businessValue;
    private String assignStatus;
    private String isAssigned;
    private String status;
    private String solution;
    private UserStoryState state;
    private Player owner;
    private List<String> blockers;  // List to store blockers

    // Constructor with only essential parameters
    public UserStory(String name, double pointValue, double businessValue, String assignStatus) {
        this(name, "", pointValue, businessValue, assignStatus, "New", "NA", new ArrayList<>()); // Default to "New" status and empty blockers list
    }

    // Full constructor with all fields
    public UserStory(String name, String description, double pointValue, double businessValue, 
                     String assignStatus, String status, String solution, List<String> blockers) {
        this.name = name;
        this.description = description;
        this.pointValue = pointValue;
        this.businessValue = businessValue;
        this.assignStatus = assignStatus;
        this.status = status;
        this.solution = solution;
        this.state = new UserStoryUnselectedState(this); // Default state
        this.blockers = blockers;  // Initialize blockers
        register();
    }

    protected void register() {
        this.id = new UserStoryIdentifier(ScrumIdentifierStoreSingleton.get().getNextId());
    }

    public ScrumIdentifier getId() {
        if (!isRegistered()) {
            throw new IllegalStateException("This UserStory has not been registered and does not have an ID yet!");
        }
        return id;
    }

    public boolean isRegistered() {
        return id != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPointValue() {
        return pointValue;
    }

    public void setPointValue(double pointValue) {
        this.pointValue = pointValue;
    }

    public double getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(double businessValue) {
        this.businessValue = businessValue;
    }

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    public String getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(String isAssigned) {
        this.isAssigned = isAssigned;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void changeState(UserStoryState state) {
        this.state = state;
    }

    public void deleteUserStory() {
        this.state = new UserStoryDeletedState(this);
        UserStoryStore.getInstance().removeUserStory(this);
    }

    public UserStoryState getUserStoryState() {
        return state;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public Player getOwner() {
        return this.owner;
    }

    public List<ScrumObject> getChildren() {
        return new ArrayList<>();
    }

    // Blockers management methods
    public List<String> getBlockers() {
        return blockers;
    }

    public void setBlockers(List<String> blockers) {
        this.blockers = blockers;
    }

    public void addBlocker(String blocker) {
        if (!blockers.contains(blocker)) {
            blockers.add(blocker);
        }
    }

    public void removeBlocker(String blocker) {
        blockers.remove(blocker);
    }

    @Override
    public String toString() {
        if (isRegistered()) {
            return this.getId().toString() + " - " + name;
        }
        return "(unregistered) - " + getName();
    }
}
