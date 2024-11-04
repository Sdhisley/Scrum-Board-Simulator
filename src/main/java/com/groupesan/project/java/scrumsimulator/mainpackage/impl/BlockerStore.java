package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BlockerStore {
    private static BlockerStore instance;
    private List<ListofBlocker> blockers;
    private Map<ListofBlocker, String> blockerProbabilities; // Store for blocker probabilities
    private boolean isSpikeActive;  // New field to track spike state
    private static final String PROPERTIES_FILE = "blockerstore.properties";


    private BlockerStore() {
        blockers = new ArrayList<>();
        blockerProbabilities = new HashMap<>();
        initBlockers();
        loadSpikeState();
    }

    public static BlockerStore getInstance() {
        if (instance == null) {
            instance = new BlockerStore();
        }
        return instance;
    }

    private void initBlockers() {
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.TIME_BLOCKER));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.TECHNICAL_DEBT));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.PEOPLE_BLOCKER));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.NEEDS_MORE_INFO));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.REQUIREMENTS_ISSUE));
    }

    public List<ListofBlocker> getBlockers() {
        return blockers;
    }

    // Methods for saving and retrieving blocker probabilities
    public void setBlockerProbability(ListofBlocker blocker, String probability) {
        blockerProbabilities.put(blocker, probability);
    }

    public String getBlockerProbability(ListofBlocker blocker) {
        return blockerProbabilities.getOrDefault(blocker, "0%");
    }
    public boolean isSpikeActive() {
        return isSpikeActive;
    }
    //button to activate spike
    public void setSpikeActive(boolean isSpikeActive) {
        this.isSpikeActive = isSpikeActive;
        saveSpikeState();
    }
    //Loading the spike state
    private void loadSpikeState() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(input);
            isSpikeActive = Boolean.parseBoolean(properties.getProperty("isSpikeActive", "false"));
        } catch (IOException ex) {
            isSpikeActive = false;
        }
    }
    //Saving the saveSpikeState
    private void saveSpikeState() {
        Properties properties = new Properties();
        properties.setProperty("isSpikeActive", Boolean.toString(isSpikeActive));

        try (OutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(output, null);
        } catch (IOException ex) {
            System.out.println("ex");
        }
    }
    public ListofBlocker getBlockerByName(String name) {
        for (ListofBlocker blocker : blockers) {
            if (blocker.getBlockerType().toString().equals(name)) {
                return blocker;
            }
        }
        return null;  // Return null if no blocker with the specified name is found
    }
}