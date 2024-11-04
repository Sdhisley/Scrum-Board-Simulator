package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofSolution.SolutionType;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SolutionStore;

public class ListofSolutionsWidget extends JPanel {

    private BlockerStore blockerStore;
    private SolutionStore solutionStore;
    private Map<ListofBlocker, JComboBox<SolutionType>> solutionSelectors;

    public ListofSolutionsWidget() {
        blockerStore = BlockerStore.getInstance();
        solutionStore = SolutionStore.getInstance();
        solutionSelectors = new HashMap<>();

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 5, 5, 0));

        JPanel userStoriesPanel = new JPanel();
        userStoriesPanel.setLayout(new BoxLayout(userStoriesPanel, BoxLayout.Y_AXIS));
        userStoriesPanel.setBorder(BorderFactory.createTitledBorder("User Stories"));

        JPanel blockerTypePanel = new JPanel();
        blockerTypePanel.setLayout(new BoxLayout(blockerTypePanel, BoxLayout.Y_AXIS));
        blockerTypePanel.setBorder(BorderFactory.createTitledBorder("Blocker Type"));

        JPanel blockerProbabilitySelectPanel = new JPanel();
        blockerProbabilitySelectPanel.setLayout(new BoxLayout(blockerProbabilitySelectPanel, BoxLayout.Y_AXIS));
        blockerProbabilitySelectPanel.setBorder(BorderFactory.createTitledBorder("Blocker Probability"));

        JPanel solutionTypePanel = new JPanel();
        solutionTypePanel.setLayout(new BoxLayout(solutionTypePanel, BoxLayout.Y_AXIS));
        solutionTypePanel.setBorder(BorderFactory.createTitledBorder("Solution"));

        JPanel solutionProbabilitySelectPanel = new JPanel();
        solutionProbabilitySelectPanel.setLayout(new BoxLayout(solutionProbabilitySelectPanel, BoxLayout.Y_AXIS));
        solutionProbabilitySelectPanel.setBorder(BorderFactory.createTitledBorder("Solution Probability"));

        String[] percentageValues = {"10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};

        List<ListofBlocker> blockers = blockerStore.getBlockers();
        for (ListofBlocker blocker : blockers) {
            if (!blocker.getUserStories().isEmpty()) {

                JPanel userStoryGroupPanel = new JPanel();
                userStoryGroupPanel.setLayout(new BoxLayout(userStoryGroupPanel, BoxLayout.Y_AXIS));
                for (String userStory : blocker.getUserStories()) {
                    JLabel userStoryLabel = new JLabel("• " + userStory);
                    userStoryGroupPanel.add(userStoryLabel);
                }
                userStoriesPanel.add(userStoryGroupPanel);

                JLabel blockerLabel = new JLabel(blocker.getBlockerType().toString());
                blockerLabel.setFont(new Font("Arial", Font.BOLD, 12));
                blockerTypePanel.add(blockerLabel);

                JComboBox<String> blockerProbabilityDropdown = new JComboBox<>(percentageValues);
                blockerProbabilityDropdown.setPreferredSize(new Dimension(100, 20));
                blockerProbabilityDropdown.setMaximumSize(new Dimension(100, 20));

                String savedBlockerProbability = blockerStore.getBlockerProbability(blocker);
                if (savedBlockerProbability != null) {
                    blockerProbabilityDropdown.setSelectedItem(savedBlockerProbability);
                }

                blockerProbabilityDropdown.addActionListener(e -> {
                    String selectedProbability = (String) blockerProbabilityDropdown.getSelectedItem();
                    blockerStore.setBlockerProbability(blocker, selectedProbability);
                });
                blockerProbabilitySelectPanel.add(blockerProbabilityDropdown);

                JComboBox<SolutionType> solutionDropdown = new JComboBox<>(SolutionType.values());
                solutionDropdown.setPreferredSize(new Dimension(100, 20));
                solutionDropdown.setMaximumSize(new Dimension(100, 20));
                solutionSelectors.put(blocker, solutionDropdown);

                SolutionType savedSolution = solutionStore.getSolutionForBlocker(blocker);
                if (savedSolution != null) {
                    solutionDropdown.setSelectedItem(savedSolution);
                }

                solutionDropdown.addActionListener(e -> {
                    SolutionType selectedSolution = (SolutionType) solutionDropdown.getSelectedItem();
                    solutionStore.addSolutionForBlocker(blocker, selectedSolution);
                });
                solutionTypePanel.add(solutionDropdown);

                JComboBox<String> solutionProbabilityDropdown = new JComboBox<>(percentageValues);
                solutionProbabilityDropdown.setPreferredSize(new Dimension(100, 20));
                solutionProbabilityDropdown.setMaximumSize(new Dimension(100, 20));

                String savedSolutionProbability = solutionStore.getSolutionProbability(blocker);
                if (savedSolutionProbability != null) {
                    solutionProbabilityDropdown.setSelectedItem(savedSolutionProbability);
                }

                solutionProbabilityDropdown.addActionListener(e -> {
                    String selectedProbability = (String) solutionProbabilityDropdown.getSelectedItem();
                    solutionStore.setSolutionProbability(blocker, selectedProbability);
                });
                solutionProbabilitySelectPanel.add(solutionProbabilityDropdown);

                userStoriesPanel.add(Box.createVerticalStrut(10));
                blockerTypePanel.add(Box.createVerticalStrut(10));
                blockerProbabilitySelectPanel.add(Box.createVerticalStrut(10));
                solutionTypePanel.add(Box.createVerticalStrut(10));
                solutionProbabilitySelectPanel.add(Box.createVerticalStrut(10));
            }
        }

        mainPanel.add(userStoriesPanel);
        mainPanel.add(blockerTypePanel);
        mainPanel.add(blockerProbabilitySelectPanel);
        mainPanel.add(solutionTypePanel);
        mainPanel.add(solutionProbabilitySelectPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
}
