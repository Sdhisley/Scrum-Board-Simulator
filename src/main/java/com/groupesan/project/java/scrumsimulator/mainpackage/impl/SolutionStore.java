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
    private Map<ListofBlocker, String> solutionProbabilities;

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

    public void setSolutionProbability(ListofBlocker blocker, String probability) {
        solutionProbabilities.put(blocker, probability);
    }

    public String getSolutionProbability(ListofBlocker blocker) {
        return solutionProbabilities.getOrDefault(blocker, "0%");
    }

    public Map<ListofBlocker, SolutionType> getAllBlockerSolutions() {
        return blockerSolutions;
    }

    /**
     * Method to retrieve the solution for a user story's primary blocker.
     * If multiple blockers exist, it returns the solution for the first blocker in the list.
     *
     * @param userStory The user story for which the solution is needed.
     * @return SolutionType The solution type for the first blocker, or null if no solution is found.
     */
    public SolutionType getSolutionForUserStoryBlocker(UserStory userStory) {
        List<String> blockers = userStory.getBlockers();
        if (blockers.isEmpty()) {
            return null;  // No blockers assigned to the user story
        }
        
        for (String blockerName : blockers) {
            ListofBlocker blocker = BlockerStore.getInstance().getBlockerByName(blockerName);
            SolutionType solution = getSolutionForBlocker(blocker);
            if (solution != null) {
                return solution;
            }
        }
        return null;  // No solution found for any of the blockers
    }
}