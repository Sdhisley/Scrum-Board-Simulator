package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofSolution.SolutionType;

public class SolutionStore {
    private static SolutionStore instance;
    private List<ListofSolution> solutions;
    private Map<ListofBlocker, SolutionType> blockerSolutions;
    private Map<ListofBlocker, String> solutionProbabilities; // Store for solution probabilities

    private SolutionStore() {
        solutions = new ArrayList<>();
        blockerSolutions = new HashMap<>();
        solutionProbabilities = new HashMap<>();
        initSolutions();
    }

    public static SolutionStore getInstance() {
        if (instance == null) {
            instance = new SolutionStore();
        }
        return instance;
    }

    private void initSolutions() {
        solutions.add(new ListofSolution(ListofSolution.SolutionType.REFACTOR));
        solutions.add(new ListofSolution(ListofSolution.SolutionType.MORE_PEOPLE));
        solutions.add(new ListofSolution(ListofSolution.SolutionType.PAIR_PROGRAMMING));
        solutions.add(new ListofSolution(ListofSolution.SolutionType.MOB_PROGRAMMING));
        solutions.add(new ListofSolution(ListofSolution.SolutionType.RESEARCH));
        solutions.add(new ListofSolution(ListofSolution.SolutionType.PROTOTYPING));
        solutions.add(new ListofSolution(ListofSolution.SolutionType.TIMEBOX));
    }

    public List<ListofSolution> getSolutions() {
        return solutions;
    }

    public void addSolutionForBlocker(ListofBlocker blocker, SolutionType solutionType) {
        blockerSolutions.put(blocker, solutionType);
    }

    public SolutionType getSolutionForBlocker(ListofBlocker blocker) {
        return blockerSolutions.get(blocker);
    }

    // Method to set the solution probability for a specific blocker
    public void setSolutionProbability(ListofBlocker blocker, String probability) {
        solutionProbabilities.put(blocker, probability);
    }

    // Method to get the solution probability for a specific blocker
    public String getSolutionProbability(ListofBlocker blocker) {
        return solutionProbabilities.getOrDefault(blocker, "0%");
    }

    // Optional: Method to get all blocker-solution mappings
    public Map<ListofBlocker, SolutionType> getAllBlockerSolutions() {
        return blockerSolutions;
    }
}
