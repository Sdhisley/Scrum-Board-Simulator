package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockerStore {
    private static BlockerStore instance;
    private List<ListofBlocker> blockers;
    private Map<ListofBlocker, String> blockerProbabilities; // Store for blocker probabilities

    private BlockerStore() {
        blockers = new ArrayList<>();
        blockerProbabilities = new HashMap<>();
        initBlockers();
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
}
